package summer.practice.infty.game

private const val increased_damage = 1.5
private const val usual_damage = 1.5
private const val health_damage = 0.5
private const val no_damage = 0.0

fun getRatioFromElemets(attack: Element, def: Element): Double{
    return when(attack){
        Element.HELLISH -> {
            when(def){
                Element.HELLISH ->  usual_damage
                Element.MARINE -> increased_damage
                Element.FROSTY -> increased_damage
                Element.SANDY ->  usual_damage
                Element.UNDERGROUND -> increased_damage
                Element.HEAVENLY ->  usual_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        //------
        Element.MARINE -> {
            when(def){
                Element.HELLISH -> increased_damage
                Element.MARINE ->  usual_damage
                Element.FROSTY ->  usual_damage
                Element.SANDY ->  usual_damage
                Element.UNDERGROUND ->  usual_damage
                Element.HEAVENLY -> increased_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        //------
        Element.FROSTY -> {
            when(def){
                Element.HELLISH -> increased_damage
                Element.MARINE ->  usual_damage
                Element.FROSTY ->  usual_damage
                Element.SANDY ->  usual_damage
                Element.UNDERGROUND ->  usual_damage
                Element.HEAVENLY -> increased_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        //------
        Element.SANDY -> {
            when(def){
                Element.HELLISH -> increased_damage
                Element.MARINE ->  usual_damage
                Element.FROSTY -> increased_damage
                Element.SANDY ->  usual_damage
                Element.UNDERGROUND ->  usual_damage
                Element.HEAVENLY ->  usual_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        //------
        Element.UNDERGROUND -> {
            when(def){
                Element.HELLISH ->  usual_damage
                Element.MARINE -> increased_damage
                Element.FROSTY ->  usual_damage
                Element.SANDY ->  usual_damage
                Element.UNDERGROUND ->  usual_damage
                Element.HEAVENLY -> increased_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        //------
        Element.HEAVENLY -> {
            when(def){
                Element.HELLISH -> increased_damage
                Element.MARINE -> increased_damage
                Element.FROSTY -> increased_damage
                Element.SANDY -> increased_damage
                Element.UNDERGROUND ->  usual_damage
                Element.HEAVENLY ->  usual_damage
                Element.USUAL -> increased_damage
                Element.NONE ->  usual_damage
            }
        }
        else ->  usual_damage
    }
}

fun getHealthDamageOfElemnts(attack: Element, def: Element): Double{
    return when{
        attack == Element.HELLISH && def == Element.MARINE -> health_damage
        attack == Element.MARINE && def == Element.HELLISH -> health_damage
        attack == Element.FROSTY && def == Element.HEAVENLY -> health_damage
        attack == Element.HEAVENLY && def == Element.FROSTY -> health_damage
        else -> no_damage
    }
}