package programs

import Player
import startSet.Time

class GilbertEffect(override val name: String, override val timeOfPlay: Time, override val description: String,
                    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (player in targets!!) {
            player.role.hidden = !player.role.hidden
        }
    }
}