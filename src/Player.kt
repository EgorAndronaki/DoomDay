import programs.BaseProgram
import startSet.*
import startSet.Faction.*
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

class Player(val name: String, var role: Role, var loyaltyCards: MutableMap<String, LoyaltyCard>, program: BaseProgram) {
    var programList: MutableList<BaseProgram> = mutableListOf(program)
    val startProgram = program
    var weapon: Pair<Weapon, List<Player>?> = Pair(Weapon.NO_WEAPON, null)
    var lives = 2

    private fun hiddenLoyalty(): Int {
        return loyaltyCards.count { it.value.hidden}
    }

    fun getHiddenLoyalty(): Map<String, LoyaltyCard> {
        if (hiddenLoyalty() != 0) {
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

    fun executeProgram(name: String, target: Player? = null, targets: List<Player>? = null) {
        val program = programList.find { it.name == name }
        program?.execute(this, target, targets)
        programList.remove(program)

    }

    fun factionToString(faction: Faction): String {
        return when (faction) {
            HUMAN -> "Человек"
            MACHINE -> "Машина"
            ROGUE -> "Изгой"
            else -> throw IllegalArgumentException("Wrong faction.")
        }
    }

    fun loyaltyCardToString(loyaltyCard: LoyaltyCard?): String {
        if (loyaltyCard != null) {
            return if (loyaltyCard.x2) { factionToString(loyaltyCard.faction) + " X2" } else { factionToString(loyaltyCard.faction) }
        }
        throw NullPointerException("Something went wrong with converting a loyalty card to string.")
    }

    fun roleToString(role: Role): String {
        return "Твоя роль: " + role.name + " (" + factionToString(role.faction) + ")!" + "\n" + "Твои способности: " + role.description
    }

    fun timeToString(time: Time): String {
        return when (time) {
            Time.ANYTIME -> "в любой момент."
            Time.TURN -> "в свой ход."
        }
    }

    fun swapLoyalty(target: Player, hisSide: String, yourSide: String) {
        val tmp = loyaltyCards[yourSide]
        loyaltyCards[yourSide] = target.loyaltyCards.getOrDefault(hisSide, LoyaltyCard(UNDEFINED, false))
    }

    fun swapRoles(target: Player) {
        val tmp = role
        role = target.role
        target.role = tmp
    }
}