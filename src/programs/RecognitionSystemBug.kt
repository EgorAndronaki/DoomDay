package programs

import Player
import startSet.Time
import startSet.Weapon

class RecognitionSystemBug(override val name: String, override val timeOfPlay: Time, override val description: String,
                           override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        target!!.weapon = Pair(Weapon.NO_WEAPON, null)
        caster.weapon = Pair(Weapon.RIFLE, null)
    }
}