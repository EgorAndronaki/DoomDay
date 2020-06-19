package programs

import Player
import startSet.Time
import startSet.Weapon

class Laser(override val name: String, override val timeOfPlay: Time, override val description: String,
            override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        caster.weapon = Pair(Weapon.LASER, null)
    }
}