package roles.rogue

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class FortuneTeller(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                    override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        println("Срабатывает постоянный эффект Гадалки! ${caster.name} сбрасывает оружие, которое берет ${target!!.name}")
        target.weapon = Pair(Weapon.NO_WEAPON, null)
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}