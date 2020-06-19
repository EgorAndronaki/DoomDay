package programs

import Player
import startSet.Time

class SpyProgram(override val name: String, override val timeOfPlay: Time, override val description: String,
                 override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (player in targets!!) {
            if (player.getHiddenLoyalty().size == 1) {
                player.getHiddenLoyalty().values.toList()[0].hidden = false
            }
            else if (player.getHiddenLoyalty().size == 2) {
                println("Какую карту открываем?")
                when (readLine()) {
                    "Левая" -> player.loyaltyCards["Левая"]!!.hidden = false
                    "Правая" -> player.loyaltyCards["Правая"]!!.hidden = false
                    else -> throw IllegalArgumentException("Wrong answer.")
                }
            }
        }
    }
}