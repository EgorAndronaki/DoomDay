package roles.human

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class Sniper(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
             override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        if (caster.weapon.first == Weapon.NO_WEAPON) {
            println("Срабатывает постоянный эффект Снайпера! Он может вооружиться лазером!")
            println("Будете вооружаться лазером?")
            if (readLine() == "Да") {
                println("${caster.name} вооружается лазером!")
                caster.weapon = Pair(Weapon.LASER, null)
            }
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("Срабатывает эффект Снайпера! ${caster.name} вооружается лазером!")
        caster.weapon = Pair(Weapon.LASER, null)
    }
}