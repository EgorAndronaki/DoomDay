package roles.human

import Player
import roles.BaseRole
import startSet.Faction

class UndercoverAgent(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                      override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        if (caster.getHiddenLoyalty().isEmpty()) {
            println("Срабатывает постоянный эффект Агента под прикрытием! Он скрывает свою роль!")
            caster.hideRole(caster)
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("Срабатывает эффект Агента под прикрытием! ${caster.name} скрывает свои карты верности!")
        for (card in caster.loyaltyCards) {
            card.value.hidden = true
        }
    }
}