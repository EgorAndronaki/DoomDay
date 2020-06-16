package startSet

import java.lang.NullPointerException

class StartData(numPlayers: Int) {
    private val weapons = WeaponManager().getWeapons(numPlayers)
    private val loyaltyCards = LoyaltyCardsManager().getLoyaltyCards(numPlayers)
    private val roles = RoleManager().getRoles(numPlayers)
    private val programs = ProgramManager().getPrograms()

    fun getWeapons(): MutableList<Weapon> {
        return weapons
    }

    fun getLoyaltyCards():MutableList<LoyaltyCard> {
        return loyaltyCards
    }

    fun getRoles(): MutableList<Role> {
        return roles
    }

    fun getPrograms(): MutableList<Program> {
        return programs
    }

    private fun factionToString(faction: Faction): String {
        return when (faction) {
            Faction.HUMAN -> "Человек"
            Faction.MACHINE -> "Машина"
            Faction.ROGUE -> "Изгой"
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

    fun weaponToString(weapon: Weapon): String {
        return when (weapon) {
            Weapon.PISTOL -> "Пистолет"
            Weapon.RIFLE -> "Винтовка"
            Weapon.PARTNER -> "Напарник"
            Weapon.MISSILE -> "Ракетница"
            Weapon.LASER -> "Лазер"
        }
    }
}