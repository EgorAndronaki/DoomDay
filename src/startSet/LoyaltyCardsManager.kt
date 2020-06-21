package startSet
import startSet.Faction.*
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

data class LoyaltyCard(val faction: Faction, val x2: Boolean) {
    var hidden = true
}

enum class Faction {
    HUMAN,
    MACHINE,
    ROGUE,
    UNDEFINED
}

class LoyaltyCardsManager {
    private val loyaltyCards = mapOf(4 to createSet(3, 2, 0, 1, 1, 1), 5 to createSet(4, 3, 0, 1, 1, 1), 6 to createSet(3, 4, 2, 2, 1, 0),
        7 to createSet(4, 3, 3, 2, 2, 0), 8 to createSet(6, 5, 0, 2, 2, 1), 9 to createSet(6, 6, 1, 2, 2, 1), 10 to createSet(7, 5, 2, 2, 2, 2),
        11 to createSet(7, 6, 3, 2, 2, 2), 12 to createSet(8, 5, 4, 3, 3, 1), 13 to createSet(8, 6, 5, 2, 3, 2), 14 to createSet(8, 6, 6, 3, 3, 2),
        15 to createSet(9, 8, 6, 3, 2, 2), 16 to createSet(9, 9, 6, 3, 3, 2))

    private fun createSet(human: Int, machine: Int, rogue: Int, x2human: Int, x2machine: Int, x2rogue: Int): List<LoyaltyCard> {
        return List(human) {LoyaltyCard(HUMAN, false)} + List(machine) {LoyaltyCard(MACHINE, false)} +
                List(rogue) {LoyaltyCard(ROGUE, false)} + List(x2human) {LoyaltyCard(HUMAN, true)} +
                List(x2machine) {LoyaltyCard(MACHINE, true)} + List(x2rogue) {LoyaltyCard(ROGUE, true)}
    }

    fun getLoyaltyCards(numPlayers: Int): MutableList<LoyaltyCard> {
        if (!loyaltyCards.containsKey(numPlayers)) {
            throw IllegalArgumentException("Wrong number of players.")
        }
        return loyaltyCards.getOrElse(numPlayers) {throw NullPointerException("No loyalty cards found.")}.shuffled().toMutableList()
    }
}