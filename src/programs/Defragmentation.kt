package programs

import Player
import startSet.Time

class Defragmentation(override val name: String, override val timeOfPlay: Time, override val description: String,
    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        for (card in caster.loyaltyCards.values) {
            card.hidden = true
        }
    }
}