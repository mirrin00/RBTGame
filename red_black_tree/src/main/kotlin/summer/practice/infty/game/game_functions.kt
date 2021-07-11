package summer.practice.infty.game

private const val standard = 1.0
private const val increased = 1.5
private const val heath_damage = 0.5
private const val no_damage = 0.0

fun getRatioFromElemets(attack: Element, def: Element): Double{
    return when(attack){
        Element.HELLISH -> {
            when(def){
                Element.HELLISH -> standard
                Element.MARINE -> increased
                Element.FROSTY -> increased
                Element.SANDY -> standard
                Element.UNDERGROUND -> increased
                Element.HEAVENLY -> standard
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        //------
        Element.MARINE -> {
            when(def){
                Element.HELLISH -> increased
                Element.MARINE -> standard
                Element.FROSTY -> standard
                Element.SANDY -> standard
                Element.UNDERGROUND -> standard
                Element.HEAVENLY -> increased
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        //------
        Element.FROSTY -> {
            when(def){
                Element.HELLISH -> increased
                Element.MARINE -> standard
                Element.FROSTY -> standard
                Element.SANDY -> standard
                Element.UNDERGROUND -> standard
                Element.HEAVENLY -> increased
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        //------
        Element.SANDY -> {
            when(def){
                Element.HELLISH -> increased
                Element.MARINE -> standard
                Element.FROSTY -> increased
                Element.SANDY -> standard
                Element.UNDERGROUND -> standard
                Element.HEAVENLY -> standard
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        //------
        Element.UNDERGROUND -> {
            when(def){
                Element.HELLISH -> standard
                Element.MARINE -> increased
                Element.FROSTY -> standard
                Element.SANDY -> standard
                Element.UNDERGROUND -> standard
                Element.HEAVENLY -> increased
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        //------
        Element.HEAVENLY -> {
            when(def){
                Element.HELLISH -> increased
                Element.MARINE -> increased
                Element.FROSTY -> increased
                Element.SANDY -> increased
                Element.UNDERGROUND -> standard
                Element.HEAVENLY -> standard
                Element.USUAL -> increased
                Element.NONE -> standard
            }
        }
        else -> standard
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