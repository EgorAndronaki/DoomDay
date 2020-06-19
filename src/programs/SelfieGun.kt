package programs

import Player
import startSet.Time
import startSet.Weapon
import java.lang.IllegalArgumentException

class SelfieGun(override val name: String, override val timeOfPlay: Time, override val description: String,
                override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (player in targets!!) {
            if (player.weapon.first != Weapon.NO_WEAPON) {
                if (player.getHiddenLoyalty().size == 1) {
                    println("Карта верности: ${player.loyaltyCardToString(player.getHiddenLoyalty().values.toList()[0])}")
                }
                else if (player.getHiddenLoyalty().size == 2) {
                    println("Какую будем подсматривать?")
                    when (readLine()) {
                        "Левая" -> println("Левая карта верности: ${player.loyaltyCardToString(player.loyaltyCards["Левая"])}")
                        "Правая" -> println("Правая карта верности: ${player.loyaltyCardToString(player.loyaltyCards["Правая"])}")
                        else -> throw IllegalArgumentException("Wrong answer.")
                    }
                }
            }
        }
    }
}