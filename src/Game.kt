import startSet.Weapon
import startSet.prepare

lateinit var players: MutableList<Player>

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

fun calcDamage(weapon: Weapon): Int {
    if (weapon == Weapon.PISTOL || weapon == Weapon.PARTNER || weapon == Weapon.MISSILE) {
        return 1
    }
    return 2
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
            if (players[i].weapon.first == Weapon.NO_WEAPON) {
                println("Выбирай: взять оружие или подсмотреть одну карту верности другого игрока!")
                when(readLine()) {
                    "Оружие" -> {
                        println("Выбирай оружие: ${weapons.joinToString { weaponToString(it) }}!")
                        val weapon = stringToWeapon(readLine() ?: "")
                        players[i].aim(weapon, players)
                        println("${players[i].name} нацеливает ${weaponToString(weapon)} на ${players[i].weapon.second!!.joinToString { it.name }}!")
                        weapons.remove(weapon)
                    }
                    "Карта", "Верность" -> {
                        println("Выбери сначала игрока, а потом карту верности, которую ты хочешь подсмотреть:")
                        println(players[i].getAllHidden(players))
                        val name = readLine()
                        val target = players.find { it.name == name }
                        val side = readLine()
                        println("${players[i].name} подсматривает карту верности ${target!!.name}! И это ${target.loyaltyCardToString(target.loyaltyCards[side])}!")
                    }
                }
            }
            else {
                println("Выбирай: бросить оружие, сменить цель или выстрелить!")
                when(readLine()) {
                    "Бросить" -> {
                        weapons.add(players[i].weapon.first)
                        players[i].weapon = Pair(Weapon.NO_WEAPON, null)
                        println("${players[i].name} бросает оружие!")
                    }
                    "Сменить" -> {
                        players[i].aim(players[i].weapon.first, players)
                        println("${players[i].name} меняет цель! И это ${players[i].weapon.second!!.joinToString { it.name }}!")
                    }
                    "Выстрелить" -> {
                        println("${players[i].name} стреляет в ${players[i].weapon.second!!.joinToString { it.name }}!")
                        val damage = calcDamage(players[i].weapon.first)
                        players[i].attackHandle(damage, players[i].weapon.second!!)
                        weapons.add(players[i].weapon.first)
                        players[i].weapon = Pair(Weapon.NO_WEAPON, null)
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