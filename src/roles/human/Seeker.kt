package roles.human

import Player
import roles.BaseRole
import startSet.Faction

class Seeker(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
             override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        outer@for (target in players) {
            if (target == caster) {
                continue@outer
            }
            for (card in target.getHiddenLoyalty()) {
                card.value.hidden = false
                println("${card.key} карта верности ${target.name} - ${target.loyaltyCardToString(card.value)}")
                if (card.value.faction == Faction.MACHINE) {
                    break@outer
                }
            }
        }
    }
}