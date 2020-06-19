package programs

import Player
import startSet.Time
import startSet.Weapon

class Malfunction(override val name: String, override val timeOfPlay: Time, override val description: String,
                  override val const: Boolean) : BaseProgram {

    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        target!!.weapon = Pair(Weapon.NO_WEAPON, null)
        if (target.getHiddenLoyalty().size == 1) {
            target.getHiddenLoyalty().values.toList()[0].hidden = false
        }
        else if (target.getHiddenLoyalty().size == 2) {
            println("Какую карту открываем?")
            when (readLine()) {
                "Левая" -> target.loyaltyCards["Левая"]!!.hidden = false
                "Правая" -> target.loyaltyCards["Правая"]!!.hidden = false
                else -> throw IllegalArgumentException("Wrong answer.")
            }
        }
    }
}