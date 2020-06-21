package roles.machine

import Player
import roles.BaseRole
import startSet.Faction

class Assassin(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
               override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        println("Срабатывает постоянный эффект Убийцы! ${caster.name} открывает роль, а ${target!!.name}" +
                " открывает одну карту верности и роль или получает два повреждения!")
        caster.openRole(caster)
        if (target.getHiddenLoyalty().isEmpty() || !target.role.hidden) {
            target.lives -= 2
            println("${target.name} не может выполнить условие и получает два повреждения!")
            if (target.lives <= 0) {
                println("${target.name} убили!")
            }
        }
        else {
            println("${target.name}, выбирай! Открывай одну карту верности и роль или умирай!")
            if (readLine() == "Открыть") {
                caster.openLoyalty(target)
                caster.openRole(target)
            }
            else {
                println("${target.name} получает два повреждения!")
                target.lives -= 2
                if (target.lives <= 0) {
                    println("${target.name} убили!")
                }
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}