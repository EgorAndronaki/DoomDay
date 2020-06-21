package roles.machine

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class Joker(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
            override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        if (caster.weapon.first == Weapon.NO_WEAPON) {
            println("Срабатывает постоянный эффект Шут.Ника! Он может вооружиться ракетницей!")
            println("Будете вооружаться ракетницей?")
            if (readLine() == "Да") {
                println("${caster.name} вооружается ракетницей!")
                caster.aim(Weapon.MISSILE, players)
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}