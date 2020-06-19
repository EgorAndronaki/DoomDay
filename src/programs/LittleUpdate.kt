package programs

import Player
import startSet.Time
import startSet.Weapon

class LittleUpdate(override val name: String, override val timeOfPlay: Time, override val description: String,
                   override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        println("Лазер или ракетница?")
        when (readLine()) {
            "Лазер" -> {
                caster.weapon = Pair(Weapon.LASER, null)
            }
            "Ракетница" -> {
                caster.weapon = Pair(Weapon.MISSILE, null)
            }
            else -> throw IllegalArgumentException("Wrong answer.")
        }
    }
}