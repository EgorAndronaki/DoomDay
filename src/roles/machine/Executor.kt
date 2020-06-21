package roles.machine

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class Executor(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
               override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        if (caster.weapon.first == Weapon.NO_WEAPON) {
            println("Срабатывает постоянный эффект Исполнителя! ${caster.name} может подобрать оружие!")
            println("Будете подбирать?")
            if (readLine() == "Да") {
                caster.aim(target!!.weapon.first, players)
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}