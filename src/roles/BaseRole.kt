package roles

import Player
import startSet.Faction

interface BaseRole {
    val name: String
    val faction: Faction
    val always: Boolean
    val effect: String?
    val constEffect: String?
    var hidden: Boolean

    fun useEffect(caster: Player, players: List<Player>)
    fun useConstEffect(caster: Player, target: Player? = null, players: List<Player>)
}