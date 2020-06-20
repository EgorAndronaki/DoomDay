package startSet

import Player
import programs.BaseProgram
import java.lang.NullPointerException

fun prepare(): Triple<MutableList<Player>, MutableList<BaseProgram>, MutableList<Weapon>> {
    println("Введите количество игроков:")
    val numPlayers = readLine()?.toInt() ?: 0
    val data = StartData(numPlayers)
    val weapons = data.getWeapons()
    val loyaltyCards = data.getLoyaltyCards()
    val roles = data.getRoles()
    val programs = data.getPrograms()

    val players = mutableListOf<Player>()
    data.sortPrograms(numPlayers)

    if (numPlayers < 9) {
        for (i in 0 until numPlayers) {
            println("Введите имя игрока:")
            val name = readLine() ?: "Игрок ${i + 1}"
            val player = Player(name, roles[0], mutableMapOf("Левая" to loyaltyCards[0], "Правая" to loyaltyCards[1]))
            players.add(player)
            roles.removeAt(0)
            loyaltyCards.removeAt(0)
            loyaltyCards.removeAt(0)
            programs.removeAt(0)
        }
    }
    else {
        for (i in 0 until numPlayers) {
            println("Введите имя игрока:")
            val name = readLine() ?: "Игрок ${i + 1}"
            val player = if (i == 0) {
                Player(name, roles[0], mutableMapOf("Левая" to loyaltyCards[0], "Правая" to LoyaltyCard(Faction.UNDEFINED, false)))
            } else {
                Player(name, roles[0],
                    mutableMapOf("Левая" to loyaltyCards[0], "Правая" to players[i-1].loyaltyCards["Левая"]!!))
            }
            players.add(player)
            roles.removeAt(0)
            loyaltyCards.removeAt(0)
            programs.removeAt(0)
        }
        players[0].loyaltyCards["Правая"] = players[numPlayers - 1].loyaltyCards.getOrDefault("Левая", LoyaltyCard(Faction.UNDEFINED, false))
    }
    println("Оружия в этой игре: " + weapons.joinToString(", ", "", "!") { data.weaponToString(it) })
    println("Первый ход у " + players[0].name)
    println("Порядок хода: " + players.map { it.name })
    for (player in players) {
        println("${player.name}, ты ${player.checkLoyalty()}! ${player.roleToString(player, player.role)} \n" +
        "Твои карты верности: левая - ${player.loyaltyCardToString(player.loyaltyCards["Левая"])}, " +
                "правая - ${player.loyaltyCardToString(player.loyaltyCards["Правая"])}! \n")
    }
    return Triple(players, programs, weapons)
}