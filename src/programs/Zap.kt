package programs

import Player
import startSet.Time

class Zap(override val name: String, override val timeOfPlay: Time, override val description: String,
          override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        target!!.lives++
    }
}