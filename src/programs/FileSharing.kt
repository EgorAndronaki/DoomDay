package programs

import Player
import startSet.Time

class FileSharing(override val name: String, override val timeOfPlay: Time, override val description: String,
                  override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        val tmp = targets!![0].weapon
        targets[0].weapon = Pair(targets[1].weapon.first, null)
        targets[1].weapon = Pair(tmp.first, null)
    }
}