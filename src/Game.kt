import roles.human.Invisible
import roles.human.Sniper
import roles.human.UndercoverAgent
import roles.machine.Joker
import roles.rogue.Mole
import startSet.Weapon
import startSet.prepare

lateinit var players: MutableList<Player>

fun hasWon(players: List<Player>): Boolean {
    return if (players.count {it.checkLoyalty() == "Человек"} == 0) {
        true
    }
    else if (players.count { it.checkLoyalty() == "Машина" } == 0 && players.count { it.checkLoyalty() == "Изгой" } == 0) {
        true
    }
    else players.count {it.checkLoyalty() == "Человек"} == 0 && players.count { it.checkLoyalty() == "Машина"} == 0

}

fun endGame(players: List<Player>): String {
    return if (players.count {it.checkLoyalty() == "Человек"} == 0) {
        "Машины победили! Все люди уничтожены!"
    }
    else if (players.count { it.checkLoyalty() == "Машина" } == 0 && players.count { it.checkLoyalty() == "Изгой" } == 0) {
        "Люди победили! Все машины и изгои уничтожены!"
    }
    else if (players.count {it.checkLoyalty() == "Человек"} == 0 && players.count { it.checkLoyalty() == "Машина"} == 0) {
        "Изгои победили! все люди и машины уничтожены!"
    }
    else ""
}

fun weaponAvailable(weapons: List<Weapon>): String {
    return if (weapons.isEmpty()) "Нет доступного оружия" else weapons.joinToString { weaponToString(it) }
}

fun weaponToString(weapon: Weapon): String {
    return when (weapon) {
        Weapon.PISTOL -> "Пистолет"
        Weapon.RIFLE -> "Винтовка"
        Weapon.PARTNER -> "Напарник"
        Weapon.MISSILE -> "Ракетница"
        Weapon.LASER -> "Лазер"
        Weapon.NO_WEAPON -> "Нет оружия"
    }
}

fun stringToWeapon(name: String): Weapon {
    return when(name) {
        "Пистолет" -> Weapon.PISTOL
        "Винтовка" -> Weapon.RIFLE
        "Напарник" -> Weapon.PARTNER
        "Ракетница" -> Weapon.MISSILE
        "Лазер" -> Weapon.LASER
        else -> throw IllegalArgumentException("Wrong name for weapon.")
    }
}

fun main() {
    var startData = prepare()
    players = startData.first.toMutableList()
    val weapons = startData.third
    while (hasWon(players)) {
        println("Что-то пошло не так, перерассчитываем...")
        startData = prepare()
        players = startData.first.toMutableList()
    }
    outer@while(!hasWon(players)) {
        for (i in 0 until players.size) {
            if (i == players.size) {
                break
            }
            println("Доступное оружие: ${weaponAvailable(weapons)}!")
            println("Ход ${players[i].name}!")
            val revealed = players.filter { !it.role.hidden }
            for (player in revealed) {
                if (player.role is UndercoverAgent || player.role is Invisible) {
                    player.role.useConstEffect(player, null, players)
                }
                if (player.role is Mole) {
                    player.role.useConstEffect(player, players[i], players)
                }
            }
            if (players[i].weapon.first == Weapon.NO_WEAPON) {
                println("Выбирай: взять оружие или подсмотреть одну карту верности другого игрока!")
                when(readLine()) {
                    "Оружие" -> {
                        if (players[i] in revealed && players[i].role is Sniper) {
                            players[i].role.useConstEffect(players[i], null, players)
                        }
                        if (players[i] in revealed && players[i].role is Joker) {
                            players[i].role.useConstEffect(players[i], null, players)
                        }
                        else {
                            println("Выбирай оружие: ${weapons.joinToString { weaponToString(it) }}!")
                            if ("Гадалка" in revealed.map { it.role.name }) {
                                players.find { it.role.name == "Гадалка" }!!.role.useConstEffect(players.find { it.role.name == "Гадалка" }!!, players[i], players)
                            }
                            else {
                                val weapon = stringToWeapon(readLine() ?: "")
                                players[i].aim(weapon, players)
                                weapons.remove(weapon)
                            }
                        }
                    }
                    "Карта", "Верность" -> {
                        println("Выбери сначала игрока, а потом карту верности, которую ты хочешь подсмотреть:")
                        println(players[i].getAllHidden(players))
                        val name = readLine()
                        val target = players.find { it.name == name }
                        val side = readLine()
                        println("${players[i].name} подсматривает карту верности ${target!!.name}! И это ${target.loyaltyCardToString(target.loyaltyCards[side])}!")
                        if ("Посторонним В" in revealed.map { it.role.name }) {
                            revealed.find { it.role.name == "Посторонним В" }!!.role.useConstEffect(revealed.find { it.role.name == "Посторонним В" }!!, null, players)
                        }
                    }
                }
            }
            else {
                println("Выбирай: бросить оружие, сменить цель или выстрелить!")
                when(readLine()) {
                    "Бросить" -> {
                        println("${players[i].name} бросает оружие!")
                        weapons.add(players[i].weapon.first)
                        if ("Исполнитель" in revealed.map { it.role.name }) {
                            players.find { it.role.name == "Исполнитель" }!!.role.useConstEffect(players.find { it.role.name == "Исполнитель" }!!, players[i], players)
                        }
                        players[i].weapon = Pair(Weapon.NO_WEAPON, null)
                    }
                    "Сменить" -> {
                        players[i].aim(
                            players[i].weapon.first,
                            players
                        )
                        println("${players[i].name} меняет цель! И это ${players[i].weapon.second!!.joinToString { it.name }}!")
                    }
                    "Выстрелить" -> {
                        println("${players[i].name} стреляет в ${players[i].weapon.second!!.joinToString { it.name }}!")
                        if (players[i].role.name == "Убийца" && players[i].weapon.first == Weapon.PISTOL) {
                            players[i].role.useConstEffect(players[i], players[i].weapon.second!![0], players)
                        }
                        else {
                            players[i].attackHandle(players[i].calcDamage(), players[i].weapon.second!!)
                        }
                        if (players[i].role.name == "Фиксер" && !players[i].role.hidden) {
                            println("Срабатывает постоянный эффект Фиксера! ${players[i].name} может не сбрасывать оружие! Но тогда нужно выбрать новую цель!")
                            println("Будете бросать оружие?")
                            if (readLine() == "Да") {
                                weapons.add(players[i].weapon.first)
                                players[i].weapon = Pair(Weapon.NO_WEAPON, null)
                            }
                            else {
                                players[i].aim(players[i].weapon.first, players)
                            }
                        }
                        else {
                            weapons.add(players[i].weapon.first)
                            players[i].weapon = Pair(Weapon.NO_WEAPON, null)
                        }
                    }
                }
            }
            players.removeAll {it.lives <= 0}
            if (hasWon(players)) {
                break@outer
            }
        }
    }
    println(endGame(players))
}