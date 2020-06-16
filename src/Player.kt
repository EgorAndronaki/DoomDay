import startSet.Faction
import startSet.LoyaltyCard
import startSet.Program
import startSet.Role
import startSet.Faction.*
import java.lang.IllegalArgumentException

class Player(val name: String, var role: Role, var loyaltyCards: MutableMap<String, LoyaltyCard?>, program: Program) {
    var programList: List<Program>? = listOf(program)
    val startProgram = program

    fun checkLoyalty(): String {
        var human = 0
        var machine = 0
        var rogue = 0
        for (card in loyaltyCards) {
            when (card.value?.faction) {
                HUMAN -> human++
                MACHINE -> machine++
                ROGUE -> rogue++
                null -> throw IllegalArgumentException("Unknown role or role without faction.")
            }
        }
        when (role.faction) {
            HUMAN -> human++
            MACHINE -> machine++
            ROGUE -> rogue++
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

    fun swapLoyalty(player: Player, hisSide: String, yourSide: String) {
        val tmp = this.loyaltyCards[yourSide]
        this.loyaltyCards[yourSide] = player.loyaltyCards[hisSide]
        player.loyaltyCards[hisSide] = tmp
    }

    fun swapRoles(player: Player) {
        val tmp = role
        role = player.role
        player.role = tmp
    }
}