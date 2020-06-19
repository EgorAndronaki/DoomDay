package programs

import Player
import startSet.LoyaltyCard
import startSet.Time

class OrellanoEffect(override val name: String, override val timeOfPlay: Time, override val description: String,
    override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        val cards = mutableListOf<LoyaltyCard>()
        for (player in targets!!) {
            for (card in player.loyaltyCards.values) {
                card.hidden = true
            }
            cards += player.loyaltyCards.values
        }
        cards.shuffle()
        for (player in targets) {
            player.loyaltyCards["Левая"] = cards[0]
            player.loyaltyCards["Правая"] = cards[1]
            cards.removeAt(0)
            cards.removeAt(0)
        }
    }
}