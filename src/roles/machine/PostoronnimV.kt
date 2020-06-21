package roles.machine

import Player
import roles.BaseRole
import startSet.Faction

class PostoronnimV(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                   override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        println("Срабатывает постоянный эффект Посторонним В! ${caster.name} может скрыть или открыть любую роль по своему выбору!")
        println("Выберите, чью роль скрыть или открыть:")
        println("${players.filter { it.role.hidden }.joinToString { it.name }} - со скрытой ролью")
        println("${players.filter { !it.role.hidden }.joinToString { it.name }} - с открытой ролью")
        val name = readLine() ?: ""
        val player = players.find { it.name == name }
        if (player!!.role.hidden) {
            println("${caster.name} открывает роль $name!")
            caster.openRole(player)
            player.role.useEffect(player, players)
        }
        else {
            println("${caster.name} скрывает роль $name!")
            caster.hideRole(player)
        }
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        return
    }
}