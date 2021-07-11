package summer.practice.infty.game

private const val damage = 1.0
private const val heath_damage = 0.5
private const val no_damage = 0.0

fun getRatioFromElemets(attack: Element, def: Element): Double{
    return when(attack){
        Element.HELLISH -> {
            when(def){
                Element.HELLISH -> no_damage
                Element.MARINE -> damage
                Element.FROSTY -> damage
                Element.SANDY -> no_damage
                Element.UNDERGROUND -> damage
                Element.HEAVENLY -> no_damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        //------
        Element.MARINE -> {
            when(def){
                Element.HELLISH -> damage
                Element.MARINE -> no_damage
                Element.FROSTY -> no_damage
                Element.SANDY -> no_damage
                Element.UNDERGROUND -> no_damage
                Element.HEAVENLY -> damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        //------
        Element.FROSTY -> {
            when(def){
                Element.HELLISH -> damage
                Element.MARINE -> no_damage
                Element.FROSTY -> no_damage
                Element.SANDY -> no_damage
                Element.UNDERGROUND -> no_damage
                Element.HEAVENLY -> damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        //------
        Element.SANDY -> {
            when(def){
                Element.HELLISH -> damage
                Element.MARINE -> no_damage
                Element.FROSTY -> damage
                Element.SANDY -> no_damage
                Element.UNDERGROUND -> no_damage
                Element.HEAVENLY -> no_damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        //------
        Element.UNDERGROUND -> {
            when(def){
                Element.HELLISH -> no_damage
                Element.MARINE -> damage
                Element.FROSTY -> no_damage
                Element.SANDY -> no_damage
                Element.UNDERGROUND -> no_damage
                Element.HEAVENLY -> damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        //------
        Element.HEAVENLY -> {
            when(def){
                Element.HELLISH -> damage
                Element.MARINE -> damage
                Element.FROSTY -> damage
                Element.SANDY -> damage
                Element.UNDERGROUND -> no_damage
                Element.HEAVENLY -> no_damage
                Element.USUAL -> damage
                Element.NONE -> no_damage
            }
        }
        else -> no_damage
    }
}

fun getHealthDamageOfElemnts(attack: Element, def: Element): Double{
    return when{
        attack == Element.HELLISH && def == Element.MARINE -> heath_damage
        attack == Element.MARINE && def == Element.HELLISH -> heath_damage
        attack == Element.FROSTY && def == Element.HEAVENLY -> heath_damage
        attack == Element.HEAVENLY && def == Element.FROSTY -> heath_damage
        else -> no_damage
    }
}