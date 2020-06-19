package programs

import Player
import startSet.Time
import startSet.Weapon

class ArmoredBot(override val name: String, override val timeOfPlay: Time, override val description: String,
                 override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        val weapon = target!!.weapon
        target.weapon = Pair(Weapon.NO_WEAPON, null)
        if (caster.weapon.first == Weapon.NO_WEAPON) {
            caster.weapon = Pair(weapon.first, null)
        }
    }
}