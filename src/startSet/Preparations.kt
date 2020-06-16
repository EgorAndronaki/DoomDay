package startSet

import Player
import java.lang.NullPointerException

fun prepare() {
    println("Введите количетсво игроков:")
    val numPlayers = readLine()?.toInt() ?: 0
    val data = StartData(numPlayers)
    val weapons = data.getWeapons()
    val loyaltyCards = data.getLoyaltyCards()
    val roles = data.getRoles()
    val programs = data.getPrograms()

    val players = mutableListOf<Player>()

    if (numPlayers < 9) {
        for (i in 0 until numPlayers) {
            val num = i + 1
            println("Введите имя игрока:")
            val name = readLine() ?: "Игрок $num"
            val player = Player(name, roles[0], mutableMapOf("Левая" to loyaltyCards[0], "Правая" to loyaltyCards[1]), programs[0])
            players.add(player)
            roles.removeAt(0)
            loyaltyCards.removeAt(0)
            loyaltyCards.removeAt(0)
            programs.removeAt(0)
        }
    }
    else {
        for (i in 0 until numPlayers) {
            val num = i + 1
            println("Введите имя игрока:")
            val name = readLine() ?: "Игрок $num"
            val player: Player
            player = if (i == 0) {
                Player(name, roles[0], mutableMapOf("Левая" to loyaltyCards[0], "Правая" to null), programs[0])
            } else {
                Player(name, roles[0],
                    mutableMapOf("Левая" to loyaltyCards[0], "Правая" to players[i - 1].loyaltyCards["Левая"]), programs[0])
            }
            players.add(player)
            roles.removeAt(0)
            loyaltyCards.removeAt(0)
            programs.removeAt(0)
        }
        players[0].loyaltyCards["Правая"] = players[numPlayers - 1].loyaltyCards["Левая"]
    }
    println("Оружия в этой игре: " + weapons.joinToString(", ", "", "!") { data.weaponToString(it) })
    println("Первый ход у " + players[0].name)
    println("Порядок хода: " + players.map { it.name })
    for (player in players) {
        println(player.name + ", ты " + player.checkLoyalty() + "! " + data.roleToString(player.role) + "\n" + "Твои карты верности: левая - " +
        data.loyaltyCardToString(player.loyaltyCards["Левая"]) + ", правая - " + data.loyaltyCardToString(player.loyaltyCards["Правая"]) + ".\n" +
        "Твоя стартовая программа: " + player.startProgram.name + ". Вот что она дает: " + player.startProgram.description + "\n")
    }
}