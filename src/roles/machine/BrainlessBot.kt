package roles.machine

import Player
import roles.BaseRole
import startSet.Faction

class BrainlessBot(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                   override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        println("Срабатывает эффект Безмозглого бота! ${caster.name} открывает карту верности одного игрока, рольь другого и может нанести повреждение третьему!")
        var player1: Player? = null
        var player2: Player? = null
        if (players.any { it.getHiddenLoyalty().isNotEmpty() && it != caster }) {
            println("Имя игрока, у которого надо открыть карту верности:")
            println("${players.filter { it.getHiddenLoyalty().isNotEmpty() && it != caster }.joinToString {it.name}}:")
            val name = readLine()
            player1 = players.find { it.name == name}
            caster.openLoyalty(player1!!)
        }
        if (players.any { it.role.hidden && it != player1 }) {
            println("Имя игрока, у которого надо открыть роль:")
            println("${players.filter { it.role.hidden && it != player1 }.joinToString { it.name }}:")
            val name = readLine()
            player2 = players.find { it.name == name }
            caster.openRole(player2!!)
        }
        if (players.any { it != player1 && it != player2 }) {
            println("Будете наносить повреждение?")
            if (readLine() == "Да") {
                println("Имя игрока, которому надо нанести повреждение:")
                println("${players.filter { it != player1 && it != player2 }.joinToString { it.name }}:")
                val name = readLine()
                val player3 = players.find { it.name == name }
                player3!!.lives--
                println("${caster.name} наносит ${player3.name} 1 урон!")
                if (player3.lives <= 0) {
                    println("${player3.name} убили!")
                }
            }
        }
    }
}