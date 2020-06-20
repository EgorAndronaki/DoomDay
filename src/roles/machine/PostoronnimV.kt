package roles.machine

import Player
import roles.BaseRole
import startSet.Faction

class PostoronnimV(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                   override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {

    }

    override fun useEffect(caster: Player, players: List<Player>) {

    }
}