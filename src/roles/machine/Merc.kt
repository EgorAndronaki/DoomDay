package roles.machine

import Player
import roles.BaseRole
import startSet.Faction

class Merc(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
           override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("Выберите игрока, которого хотите убить:")
        println("${players.filter { it != caster }.joinToString { it.name }}:")
        val answer = readLine() ?: ""
        val target = players.find { it.name == answer }
        target!!.lives -= 2
        println("${target!!.name} убили!")
    }
}