package programs

import Player
import startSet.Time

class Virus(override val name: String, override val timeOfPlay: Time, override val description: String,
            override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (player in targets!!) {
            player.lives--
        }
    }

}