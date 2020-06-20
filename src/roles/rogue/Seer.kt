package roles.rogue

import Player
import roles.BaseRole
import startSet.Faction

class Seer(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
           override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("У кого подсмотреть первую карту верности?")
        println(players.filter { it.getHiddenLoyalty().isNotEmpty() && it != caster }.joinToString { it.name })
        var name = readLine() ?: ""
        val player1 = players.find { it.name == name }
        caster.watchLoyalty(player1!!)
        println("У кого подсмотреть вторую карту верности?")
        println(players.filter { it.getHiddenLoyalty().isNotEmpty() && it != caster }.joinToString { it.name })
        name = readLine() ?: ""
        val player2 = players.find { it.name == name }
        caster.watchLoyalty(player2!!)
        println("У кого подсмотреть роль?")
        println(players.filter { it.role.hidden && it != caster }.joinToString { it.name })
        name = readLine() ?: ""
        val player3 = players.find { it.name == name }
        caster.watchRole(player3!!)
    }
}