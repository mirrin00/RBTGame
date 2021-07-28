package summer.practice.infty.game

import summer.practice.infty.actions.Action
import summer.practice.infty.actions.ActionEvent
import summer.practice.infty.actions.ActionNext
import summer.practice.infty.actions.ActionNextRoom
import summer.practice.infty.controllers.ViewController
import summer.practice.infty.game.creatures.*
import summer.practice.infty.game.events.EmptyEvent
import summer.practice.infty.game.events.RoomEvent
import summer.practice.infty.game.generators.Generator
import summer.practice.infty.game.items.ItemType
import summer.practice.infty.game.rooms.EmptyRoom
import summer.practice.infty.game.rooms.Room
import summer.practice.infty.rbt.RedBlackTreeGame
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class Game(var view: ViewController<Int>? = null) {
    private val tree = RedBlackTreeGame<Int>()
    private var player = Player(this)
    var room: Room = EmptyRoom()
    var creature: Creature = EmptyCreature()
    var event: RoomEvent = EmptyEvent()
    private var game_active = false
    private var cur_stage = Stages.WAY
    private var adds = 0
    private var dels = 0
    private var updates = 0
    private var creature_description = ""
    private var event_description = ""
    private var way_description = ""
    private var description_action_before = ""

    fun next(skip_view_tree_update: Boolean = false){
        cur_stage = when(cur_stage){
            Stages.CREATURE ->{
                description_action_before = creature.win_description
                if(event.isVisible(player.perception)){
                    Stages.EVENT
                }else{
                    changeTree()
                    if(isLastRoom()) win()
                    Stages.WAY
                }
            }
            Stages.EVENT -> {
                description_action_before = ""
                if(isLastRoom()) win()
                Stages.WAY
            }
            Stages.WAY -> {
                creature = room.creature
                event = room.event
                player.in_fight = creature.in_battle
                creature_description = creature.description
                event_description = event.description
                val left_right = tree.getLeftRightKeys(player.cur_room)
                way_description = if(left_right.first == null && left_right.second == null) "You win!"
                                  else "You can go "
                way_description += when{
                    left_right.first == null && left_right.second == null -> ""
                    left_right.first == null -> "right."
                    left_right.second == null -> "left."
                    left_right.first != null && left_right.second != null -> "left or right"
                    else -> ""
                }
                if(!skip_view_tree_update) view?.updateTree(tree, player.cur_room)
                Stages.CREATURE
            }
        }
        view?.update()
    }

    fun buy(index: Int){
        if(cur_stage != Stages.CREATURE || !game_active) return
        if(creature !is Trader || index >= 3 ||
           player.getItemsCountInInventory() >= Player.INVENTORY_CAPACITY) return
        (creature as? Trader)?.buy(index, player)
        adds += 1
        view?.update()
    }

    fun sell(item_index: Int, active: Boolean = false){
        if(!game_active || !isTrade()) return
        if (active && item_index in 0 until player.getActiveCapacity()) player.soldActiveItem(item_index)
        else if (item_index in 0 until Player.INVENTORY_CAPACITY) player.soldInventoryItem(item_index)
        view?.update()
    }

    fun drop(item_index: Int, active: Boolean = false){
        if(!game_active) return
        if (active && item_index in 0 until player.getActiveCapacity()) player.removeActiveItem(item_index)
        else if (item_index in 0 until Player.INVENTORY_CAPACITY) player.removeInventoryItem(item_index)
        view?.update()
    }

    fun pay(){
        if(cur_stage != Stages.CREATURE || !game_active) return
        val b = creature as? Bandit ?: return
        if(player.coins >= b.pay_cost){
            player.coins -= b.pay_cost
            adds += 2
            next()
            description_action_before = "You bought off the Bandit."
        }
        view?.update()
    }

    fun toFight(){
        if(cur_stage != Stages.CREATURE || !game_active) return
        val b = creature as? Bandit ?: return
        b.in_battle = true
        player.in_fight = true
        dels += 2
        view?.update()
    }

    fun fight(by_magic: Boolean){
        if(cur_stage != Stages.CREATURE || !game_active) return
        while(player.health > 0 && creature.health > 0){
            player.Attack(creature, by_magic)
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        adds += if(by_magic) 1 else 2
        dels += if(by_magic) 2 else 1
        player.in_fight = false
        if(player.health <= 0){
            end()
        }else{
            for(i in 1..3){
                if(player.getItemsCountInInventory() >= Player.INVENTORY_CAPACITY) break
                player.addItem(Generator.generateItem(room.deep_level))
            }
            player.coins += (creature as? Bandit)?.coins ?: 0
            next()
        }
        view?.update()
    }

    fun actEvent(){
        if(!game_active) return
        if(event.canAct(player)){
            adds += 1
            changeTree()
            event.actWithPlayer(player)
            next()
        }
        view?.update()
    }

    fun nextRoom(left: Boolean){
        if(!game_active) return
        val left_right = tree.getLeftRightKeys(player.cur_room)
        if(left_right.first == null && left_right.second == null) win()
        if(left){
            // Use of !! is safe because case of two nulls was above
            player.cur_room = if(left_right.first != null) left_right.first!!
                              else left_right.second!!
        }else{
            // Use of !! is safe because case of two nulls was above
            player.cur_room = if(left_right.second != null) left_right.second!!
                              else left_right.first!!
        }
        room = tree.find(player.cur_room) ?: throw RuntimeException("There is no next room by this key")
        if(updates != 0 && !room.element.red &&
           ((height_ratio * tree.height).toInt() <= tree.getKeyDepth(player.cur_room))){
            val old_root = player.cur_room
            addSubTree()
            player.cur_room = tree.getRootKey() ?: throw RuntimeException("There is no elements in tree")
            tree.insert(player.cur_room, room)
            view?.addSubTree(old_root, tree, player.cur_room)
            next(true)
        }else {
            next()
        }
        view?.update()
    }

    fun movePlayerOnDepth(){
        if(!game_active) return
        player.cur_room = tree.getRandomKeyOnDepth(tree.getKeyDepth(player.cur_room))
                ?: throw RuntimeException("Null key in teleport")
        room = tree.find(player.cur_room) ?: throw RuntimeException("Player in room that is not in tree")
        //cur_stage = Stages.WAY
        description_action_before = "You fell out of the portal onto the road."
        adds += 2
        dels += 4
    }

    fun addNextCreatureDescription(){
        if(!game_active) return
        val keys = tree.getLeftRightKeys(player.cur_room)
        var next_creature: Creature?
        if(keys.first != null){
            // Use of !! is safe because it is checked line above
            next_creature = tree.find(keys.first!!)?.creature
            val type = when{
                (next_creature is Trader) -> "Trader"
                (next_creature is Golem) -> "Golem"
                (next_creature is Bandit) -> "Bandit"
                (next_creature is Dragon) -> "Dragon"
                else -> "Unknown"
            }
            way_description += " On the left side is the $type with damage " +
                               "equal to ${next_creature?.damage ?: 0}."
        }
        if(keys.second != null) {
            // Use of !! is safe because it is checked line above
            next_creature = tree.find(keys.second!!)?.creature
            val type = when {
                (next_creature is Trader) -> "Trader"
                (next_creature is Golem) -> "Golem"
                (next_creature is Bandit) -> "Bandit"
                (next_creature is Dragon) -> "Dragon"
                else -> "Unknown"
            }
            way_description += " On the right side is the $type with damage " +
                    "equal to ${next_creature?.damage ?: 0}."
        }
    }

    fun swapPlayerItems(index1: Int, index2: Int){
        if(!game_active) return
        player.swapInventoryItems(index1, index2)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
        view?.update()
    }

    fun useItem(index: Int){
        if(!game_active) return
        player.useItem(index)
        if(player.in_fight){
            creature.attack(player)
            while(player.health <= 0 && player.hasHealthPotion())
                player.useHealthPotion()
        }
        if(player.health <= 0) end()
        view?.update()
    }

    fun start(difficulty: Difficulty){
        GameSettings.difficulty = difficulty
        game_active = true
        player = Player(this)
        player.addItem(Generator.generateItem(1, ItemType.WEAPON))
        player.addItem(Generator.generateItem(1, ItemType.MAGIC))
        player.addItem(Generator.generateItem(1, ItemType.ARMOR))
        player.addItem(Generator.generateItem(7, ItemType.HEALTH_POTION))
        generateTree()
        updates = when(GameSettings.difficulty){
            Difficulty.NORMAL -> Random.nextInt(2,5)
            Difficulty.HARD -> Random.nextInt(2, 4)
        }
        player.cur_room = tree.getRootKey() ?: throw RuntimeException("No root key in tree")
        room = tree.find(player.cur_room) ?: throw RuntimeException("Player in room that is not in tree")
        cur_stage = Stages.WAY
        view?.clearTree()
        view?.updateTree(tree, player.cur_room)
        view?.update()
    }

    private fun end(){
        player.reset()
        game_active = false
        view?.youDied()
    }

    private fun isLastRoom(): Boolean{
        val left_right = tree.getLeftRightKeys(player.cur_room)
        return (left_right.first == null && left_right.second == null)
    }

    private fun win(){
        player.reset()
        game_active = false
        view?.win()
    }

    fun increaseNumberOfRoomAdds(inc: Int){
        if(inc < 0) return
        adds += inc
    }

    fun increaseNumberOfRoomDels(inc: Int){
        if(inc < 0) return
        dels += inc
    }

    private fun changeTree(){
        val key_min = player.cur_room - 100
        val key_max = player.cur_room + 100
        insertKeys(adds * add_mul, key_min, key_max)
        val keys = tree.getKeys()
        keys.remove(player.cur_room)
        val arr = Array<Int>(max(0, min(keys.size - 5, dels * del_mul))){0}
        for (i in arr.indices) {
            var index = Random.nextInt(0, keys.size)
            while (arr.contains(keys[index])) {
                index++
                if(index == keys.size) index = 0
            }
        }
        tree.deleteRooms(*arr)
        adds = 0
        dels = 0
        view?.updateTree(tree, player.cur_room)
    }

    private fun insertKeys(size: Int, key_min: Int = 0, key_max: Int = 10000){
        if(key_min >= key_max) return
        val arr = Array<Int>(size){Random.nextInt(key_min, key_max)}
        tree.insertRooms(*arr)
    }

    private fun generateTree(){
        tree.clear()
        insertKeys(when(GameSettings.difficulty){
            Difficulty.NORMAL -> Random.nextInt(64, 129)
            Difficulty.HARD -> Random.nextInt(128, 255)
        })
    }

    private fun addSubTree(){
        if(updates == 0) return
        updates--
        tree.changeRoot(player.cur_room)
        insertKeys(when(GameSettings.difficulty){
            Difficulty.NORMAL -> Random.nextInt(32, 65)
            Difficulty.HARD -> Random.nextInt(64, 100)
        })
    }

    // <------------------------>
    // Methods for ViewController

    fun getDescription() = when(cur_stage){
            Stages.CREATURE -> creature_description
            Stages.EVENT -> "$description_action_before $event_description"
            Stages.WAY -> "$description_action_before $way_description"
        }

    fun getHealth() = player.health

    fun getCoins() = player.coins

    fun getMagic() = player.magic

    fun getAttributes() = "STRN\n${player.strength}\n\nDXTR\n${player.dexterity}\n\nINTL\n${player.intelligence}\n\nPRCT\n${player.perception}"

    fun getActions() = if(!game_active) ArrayList<Action>() else when(cur_stage){
        Stages.CREATURE -> creature.getActions(player)
        Stages.EVENT -> {
            val actions = ArrayList<Action>()
            if(event.canAct(player)) actions.add(ActionEvent(true, event.getActionDescription(), event.getUseDescription()))
            else actions.add(ActionEvent(false, event.getRequirements()))
            actions.add(ActionNext(true, "Move on"))
            actions
        }
        Stages.WAY -> {
            val actions = ArrayList<Action>()
            val keys = tree.getLeftRightKeys(player.cur_room)
            if(keys.first != null) actions.add(ActionNextRoom(true, true, "Go to the left"))
            if(keys.second != null) actions.add(ActionNextRoom(false, true, "Go to the right"))
            actions
        }
    }

    fun getInventory() = player.getInventory()

    fun getActiveItems() = player.getActiveItems()

    // <------------------------>
    // Methods for InterfaceController

    fun isEmptyItem(index: Int, active: Boolean) = if(active) player.getActiveItems().getOrNull(index)?.type == ItemType.EMPTY
                                                   else player.getInventory().getOrNull(index)?.type == ItemType.EMPTY

    fun isUsableItem(index: Int): Boolean{
        val item = player.getInventory().getOrNull(index)
        return (item?.type != ItemType.EMPTY && item?.type != ItemType.OTHER)
    }

    fun isTrade() = cur_stage == Stages.CREATURE && creature is Trader
}

private enum class Stages{
    CREATURE,
    EVENT,
    WAY
}

private const val add_mul = 5
private const val del_mul = 2
private const val height_ratio = 0.65