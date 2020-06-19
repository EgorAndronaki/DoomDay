package programs

import Player
import startSet.Time

class SwitchChannel(override val name: String, override val timeOfPlay: Time, override val description: String,
                    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        targets!![0].role.hidden = true
        targets[1].role.hidden = false
    }
}