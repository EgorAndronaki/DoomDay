package roles.human

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class Sniper(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
             override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("${caster.name} вооружается лазером!")
        caster.weapon = Pair(Weapon.LASER, null)
    }
}