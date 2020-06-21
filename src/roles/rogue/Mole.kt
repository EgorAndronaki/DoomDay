package roles.rogue

import Player
import roles.BaseRole
import startSet.Faction

class Mole(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
           override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        println("Срабатывает постоянный эффект Крота! Он можетп подсмотреть карту верности или роль того, чей сейчас ход!")
        if (target!!.getHiddenLoyalty().isNotEmpty()) {
            println("Будете подсматривать карту верности ${target.name}?")
            if (readLine() == "Да") {
                caster.watchLoyalty(target)
            }
        }
        if (target.role.hidden) {
            println("Будете подсматривать роль ${target.name}?")
            if (readLine() == "Да") {
                caster.watchRole(target)
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}