package programs

import Player
import startSet.Time
import startSet.Weapon

class AdventurerBot(override val name: String, override val timeOfPlay: Time, override val description: String,
                    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (player in targets!!) {
            if (player.weapon.first != Weapon.NO_WEAPON) {
                println(player.roleToString(player.role))
            }
        }
    }
}