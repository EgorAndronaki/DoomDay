import programs.BaseProgram
import roles.BaseRole
import startSet.*
import startSet.Faction.*
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.lang.StringBuilder

class Player(val name: String, var role: BaseRole, var loyaltyCards: MutableMap<String, LoyaltyCard>) {
    var weapon: Pair<Weapon, List<Player>?> = Pair(Weapon.NO_WEAPON, null)
    var lives = 2

    fun attackHandle(damage: Int, targets: List<Player>) {
        for (target in targets) {
            if (damage == 1) {
                if (target.getHiddenLoyalty().isEmpty()) {
                    target.lives--
                    println("У ${target.name} ${target.lives} жизней!")
                }
                else {
                    println("${target.name}, выбирай! Открывай карту верности или получай одно повреждение!")
                    println(
                        "Статус твоих карт верности: левая - ${hiddenOrNot(target.loyaltyCards["Левая"]!!.hidden)}, правая - ${hiddenOrNot(
                            target.loyaltyCards["Правая"]!!.hidden
                        )}"
                    )
                    when (readLine()) {
                        "Левая" -> {
                            target.loyaltyCards["Левая"]!!.hidden = false
                            println("Левая карта ${target.name} - ${target.loyaltyCardToString(target.loyaltyCards["Левая"])}!")
                        }
                        "Правая" -> {
                            target.loyaltyCards["Правая"]!!.hidden = false
                            println("Правая карта ${target.name} - ${target.loyaltyCardToString(target.loyaltyCards["Правая"])}!")
                        }
                        "Урон" -> {
                            target.lives--
                            println("У ${target.name} ${target.lives} жизней!")
                        }
                    }
                }
            }
            else {
                if (!target.role.hidden) {
                    if (target.role.name == "Информатор") {
                        println("Сработал эффект Информатора! ${target.name} наносится 1 урон!")
                        target.role.useConstEffect(target, players)
                    }
                    else {
                        target.lives -= 2
                    }
                    println("У ${target.name} ${target.lives} жизней!")
                }
                else {
                    println("${target.name}, выбирай! Открывай роль или умирай!")
                    when (readLine()) {
                        "Роль" -> {
                            target.role.hidden = false
                            println(target.roleToString(target, target.role))
                            if (target.role.name != "Наемник") {
                                target.role.useEffect(target, players)
                            }
                        }
                        "Урон" -> {
                            target.lives -= 2
                            println("У ${target.name} ${target.lives} жизней!")
                        }
                    }
                }
            }
            if (target.lives <= 0) {
                println("${target.name} убили!")
                println(target.roleToString(target, target.role))
                println("Левая карта верности - ${target.loyaltyCardToString(target.loyaltyCards["Левая"])}," +
                        " правая - ${target.loyaltyCardToString(target.loyaltyCards["Правая"])}")
                if (target.role.name == "Наемник") {
                    println("Срабатывает способность Наемника! Он может убить выбранного игрока!")
                    target.role.useEffect(target, players)
                }
            }
        }
    }

    private fun hiddenOrNot(hidden: Boolean): String {
        return if (hidden) {
            "скрыта"
        } else {
            "открыта"
        }
    }

    fun getAllHidden(players: List<Player>): String {
        val sb = StringBuilder()
        for (player in players) {
            if (player == this) {
                continue
            }
            sb.append("${player.name}: ")
            for (card in player.getHiddenLoyalty()) {
                sb.append("${card.key} - ${hiddenOrNot(card.value.hidden)}, ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    fun getHiddenLoyalty(): Map<String, LoyaltyCard> {
        if (loyaltyCards.any { it.value.hidden}) {
            return loyaltyCards.filter { it.value.hidden }
        }
        return mapOf()
    }

    fun checkLoyalty(): String {
        var human = 0
        var machine = 0
        var rogue = 0
        if (role.always) {
            return factionToString(role.faction)
        }
        for (card in loyaltyCards) {
            when (card.value.faction) {
                HUMAN -> human += if (card.value.x2) 2 else 1
                MACHINE -> machine += if (card.value.x2) 2 else 1
                ROGUE -> rogue += if (card.value.x2) 2 else 1
                else -> throw IllegalArgumentException("Wrong faction.")
            }
        }
        when (role.faction) {
            HUMAN -> human++
            MACHINE -> machine++
            ROGUE -> rogue++
            else -> throw IllegalArgumentException("Wrong faction.")
        }
        return if (human > machine && human > rogue) {
            "Человек"
        }
        else if (machine > human && machine > rogue) {
            "Машина"
        } else {
            "Изгой"
        }
    }

    private fun factionToString(faction: Faction): String {
        return when (faction) {
            HUMAN -> "Человек"
            MACHINE -> "Машина"
            ROGUE -> "Изгой"
            else -> throw IllegalArgumentException("Wrong faction.")
        }
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

    fun loyaltyCardToString(loyaltyCard: LoyaltyCard?): String {
        if (loyaltyCard != null) {
            return if (loyaltyCard.x2) { factionToString(loyaltyCard.faction) + " X2" } else { factionToString(loyaltyCard.faction) }
        }
        throw NullPointerException("Something went wrong with converting a loyalty card to string.")
    }

    fun roleToString(target: Player, role: BaseRole): String {
        val sb = StringBuilder("Роль ${target.name}: ${role.name} (${factionToString(role.faction)})!\nСпособности роли: ")
        if (role.effect != null) {
            sb.append(role.effect + "\n")
        }
        if (role.constEffect != null) {
            sb.append("Постоянный эффект: ${role.constEffect}")
        }
        return sb.toString()
    }

    fun aim(weapon: Weapon, players: List<Player>) {
        this.weapon = if (weapon == Weapon.MISSILE) {
            println("Выбери две цели. Они должны сидеть рядом:")
            val name1 = readLine()
            val name2 = readLine()
            val player1 = players.find { it.name == name1}
            val player2 = players.find { it.name == name2 }
            Pair(weapon, listOf(player1!!, player2!!))
        } else {
            println("Выбери цель. Это может быть кто угодно, кроме тебя:")
            val name = readLine()
            val player = players.find { it.name == name }
            Pair(weapon, listOf(player!!))
        }
    }

    fun watchLoyalty(target: Player) {
        if (target.getHiddenLoyalty().size == 1) {
            println("${target.getHiddenLoyalty().keys.toList()[0]} карта верности ${target.name} - ${target.getHiddenLoyalty().values.toList()[0]}!")
        }
        else if (target.getHiddenLoyalty().size == 2) {
            println("Какую карту выбираем?")
            val side = readLine() ?: ""
            println("$side карта верности ${target.name} - ${target.loyaltyCardToString(target.loyaltyCards[side])}")
        }
    }

    fun watchRole(target: Player) {
        println(roleToString(target, target.role))
    }

    fun openLoyalty(target: Player, side: String? = null) {
        when {
            side != null -> {
                println("$side карта верности - ${target.loyaltyCardToString(target.loyaltyCards[side])}")
                target.loyaltyCards[side]!!.hidden = false
            }
            target.getHiddenLoyalty().size == 1 -> {
                println("${target.getHiddenLoyalty().keys.toList()[0]} карта верности ${target.name} - ${target.getHiddenLoyalty().values.toList()[0]}!")
                target.getHiddenLoyalty().getValue(target.getHiddenLoyalty().keys.toList()[0]).hidden = false
            }
            target.getHiddenLoyalty().size == 2 -> {
                println("Какую карту выбираем?")
                val answer = readLine() ?: ""
                println("$answer карта верности ${target.name} - ${target.loyaltyCardToString(target.loyaltyCards[answer])}")
                target.loyaltyCards[answer]!!.hidden = false
            }
        }
    }

    fun openRole(target: Player) {
        println(roleToString(target, target.role))
        target.role.hidden = false
    }
}