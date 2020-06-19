import programs.BaseProgram
import startSet.LoyaltyCard
import startSet.Weapon
import startSet.prepare
import java.lang.NullPointerException
import java.lang.StringBuilder
import kotlin.random.Random

fun hasWon(players: List<Player>): Boolean {
    if (players.count {it.checkLoyalty() == "Человек"} == 0) {
        return true
    }
    else if (players.count { it.checkLoyalty() == "Машина" } == 0 && players.count { it.checkLoyalty() == "Изгой" } == 0) {
        return true
    }
    else if (players.count {it.checkLoyalty() == "Человек"} == 0 && players.count { it.checkLoyalty() == "Машина"} == 0) {
        return true
    }
    return false

}

fun endGame(players: List<Player>): String {
    if (players.count {it.checkLoyalty() == "Человек"} == 0) {
        return "Машины победили! Все люди уничтожены!"
    }
    else if (players.count { it.checkLoyalty() == "Машина" } == 0 && players.count { it.checkLoyalty() == "Изгой" } == 0) {
        return "Люди победили! Все машины и изгои уничтожены!"
    }
    else if (players.count {it.checkLoyalty() == "Человек"} == 0 && players.count { it.checkLoyalty() == "Машина"} == 0) {
        return "Изгои победили! все люди и машины уничтожены!"
    }
    return ""
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

fun getAllHidden(players: List<Player>): String {
    val sb = StringBuilder()
    for (player in players) {
        sb.append("${player.name}: ")
        for (card in player.getHiddenLoyalty()) {
            sb.append("${card.key} - ${hiddenOrNot(card.value.hidden)}, ")
        }
        sb.append("\n")
    }
    return sb.toString()
}

fun calcDamage(weapon: Weapon): Int {
    if (weapon == Weapon.PISTOL || weapon == Weapon.PARTNER || weapon == Weapon.MISSILE) {
        return 1
    }
    return 2
}

fun aim(weapon: Weapon, players: List<Player>): List<Player> {
    if (weapon == Weapon.MISSILE) {
        println("Выбери две цели. Они должны сидеть рядом:")
        val name1 = readLine()
        val name2 = readLine()
        val player1 = players.find { it.name == name1}
        val player2 = players.find { it.name == name2 }
        return listOf(player1!!, player2!!)
    }
    println("Выбери цель. Это может быть кто угодно, кроме тебя:")
    val name = readLine()
    val player = players.find { it.name == name }
    return listOf(player!!)
}

fun hiddenOrNot(hidden: Boolean): String {
    return if (hidden) "скрыта" else "открыта"
}

fun attackHandle(damage: Int, targets: List<Player>): List<Player> {
    val dead = mutableListOf<Player>()
    for (target in targets) {
        if (damage == 1) {
            if (target.getHiddenLoyalty().isEmpty()) {
                target.lives--
                println("У ${target.name} ${target.lives} жизней!")
            }
            println("${target.name}, выбирай! Открывай карту верности или получай одно повреждение!")
            println("Статус твоих карт верности: левая - ${hiddenOrNot(target.loyaltyCards["Левая"]!!.hidden)}, правая - ${hiddenOrNot(target.loyaltyCards["Правая"]!!.hidden)}")
            when(readLine()) {
                "Левая" -> {
                    target.loyaltyCards["Левая"]!!.hidden = false
                    println(target.loyaltyCardToString(target.loyaltyCards["Левая"]))
                }
                "Правая" -> {
                    target.loyaltyCards["Правая"]!!.hidden = false
                    println(target.loyaltyCardToString(target.loyaltyCards["Правая"]))
                }
                "Урон" -> {
                    target.lives--
                    println("У ${target.name} ${target.lives} жизней!")
                }
            }
        }
        else {
            if (!target.role.hidden) {
                target.lives -= 2
                println("У ${target.name} ${target.lives} жизней!")
            }
            println("${target.name}, выбирай! Открывай роль или умирай!")
            when (readLine()) {
                "Роль" -> {
                    target.role.hidden = false
                    println(target.roleToString(target.role))
                }
                "Урон" -> {
                    target.lives -= 2
                    println("У ${target.name} ${target.lives} жизней!")
                }
            }
        }
        if (target.lives <= 0) {
            println("${target.name} убили!")
        }
    }
    return dead
}

fun main() {
    var startData = prepare()
    var players: MutableList<Player> = startData.first.toMutableList()
    val weapons = startData.third
    while (hasWon(players)) {
        println("Что-то пошло не так, перерассчитываем...")
        startData = prepare()
        players = startData.first.toMutableList()
    }
    while(!hasWon(players)) {
        for (player in players) {
            println("Ход ${player.name}!")
            if (player.weapon.first == Weapon.NO_WEAPON) {
                println("Выбирай: взять оружие или подсмотреть одну карту верности другого игрока!")
                when(readLine()) {
                    "Оружие" -> {
                        println("Выбирай оружие: ${weapons.joinToString { weaponToString(it) }}!")
                        val weapon = stringToWeapon(readLine() ?: "")
                        val targets = aim(weapon, players)
                        player.weapon = Pair(weapon, targets)
                        println("${player.name} нацеливает ${weaponToString(weapon)} на ${targets.joinToString { it.name }}!")
                        weapons.remove(weapon)
                    }
                    "Карта", "Верность" -> {
                        println("Выбери сначала игрока, а потом карту верности, которую ты хочешь подсмотреть:")
                        println(getAllHidden(players))
                        val name = readLine()
                        val target = players.find { it.name == name }
                        val side = readLine()
                        println("${player.name} подсматривает карту верности ${target!!.name}! И это ${target.loyaltyCardToString(target.loyaltyCards[side])}!")
                    }
                }
            }
            else {
                println("Выбирай: бросить оружие, сменить цель или выстрелить!")
                when(readLine()) {
                    "Бросить" -> {
                        weapons.add(player.weapon.first)
                        player.weapon = Pair(Weapon.NO_WEAPON, null)
                        println("${player.name} бросает оружие!")
                    }
                    "Сменить" -> {
                        val targets = aim(player.weapon.first, players)
                        println("${player.name} меняет цель! И это ${targets.joinToString { it.name }}")
                    }
                    "Выстрелить" -> {
                        println("${player.name} стреляет в ${player.weapon.second!!.joinToString { it.name }}!")
                        val damage = calcDamage(player.weapon.first)
                        val killed = attackHandle(damage, player.weapon.second!!)
                        players.removeAll(killed)
                        weapons.add(player.weapon.first)
                        player.weapon = Pair(Weapon.NO_WEAPON, null)
                    }
                }
            }
            if (hasWon(players)) {
                break
            }
        }
    }
    println(endGame(players))
}