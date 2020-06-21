package roles.human

import Player
import roles.BaseRole
import startSet.Faction

class Invisible(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        if (caster.getHiddenLoyalty().isEmpty()) {
            println("Срабатывает постоянный эффект Невидимки! Он может скрыть одну, две или все свои карты!")
            println("Будете скрывать карты?")
            if (readLine() == "Да") {
                println("Сколько карт верности?")
                val answer = readLine()!!.toInt()
                if (answer == 1) {
                    println("Какую сторону?")
                    caster.hideLoyalty(caster, readLine() ?: "")
                }
                else if (answer == 2) {
                    caster.hideLoyalty(caster, "Левая")
                    caster.hideLoyalty(caster, "Правая")
                }
            }
            println("Будете скрывать роль?")
            if (readLine() == "Да") {
                caster.hideRole(caster)
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}