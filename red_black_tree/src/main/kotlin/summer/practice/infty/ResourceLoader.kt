package summer.practice.infty

import javafx.scene.image.Image
import summer.practice.infty.rbt.RedBlackTree
import java.lang.IllegalArgumentException

object ResourceLoader {
    private val map = RedBlackTree<String, Image>()

    init{
        loadImage("/cave2.jpg", "UNDERGROUND")
        loadImage("/cave.jpg", "UNDERGROUND2")
        loadImage("/forest.jpg", "USUAL")
        loadImage("/lava.jpg", "HELLISH")
        loadImage("/sand.jpg", "SANDY")
        loadImage("/sea.jpg", "MARINE")
        loadImage("/sea2.jpg", "MARINE2")
        loadImage("/sky.jpg", "HEAVENLY")
        loadImage("/snow.jpg", "FROSTY")
        // Item icons
        loadImage("/sword.png", "WEAPON")
        loadImage("/armor.png", "ARMOR")
        loadImage("/magic_scroll.png", "MAGIC")
        loadImage("/amulet.png", "AMULET")
        loadImage("/health_potion.png", "HEALTH_POTION")
        loadImage("/magic_potion.png", "MAGIC_POTION")
        loadImage("/relic.png", "OTHER")
        loadImage("/empty.png", "EMPTY")
        loadImage("/player.jpg", "PLAYER")
    }

    private fun loadImage(filename: String, key: String){
        try {
            map.insert(key, Image(filename))
        }catch(e: IllegalArgumentException){
            println("Error: ResourceLoader: Can't find file $filename")
        }catch(e: Exception){
            throw RuntimeException("ResourceLoader: Error while loading file $filename")
        }
    }

    fun getImage(key: String): Image? = map.find(key)
}