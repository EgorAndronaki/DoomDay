package startSet

import programs.BaseProgram
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

    fun getPrograms(): MutableList<BaseProgram> {
        return programs
    }

    fun weaponToString(weapon: Weapon): String {
        return when (weapon) {
            Weapon.PISTOL -> "Пистолет"
            Weapon.RIFLE -> "Винтовка"
            Weapon.PARTNER -> "Напарник"
            Weapon.MISSILE -> "Ракетница"
            Weapon.LASER -> "Лазер"
            Weapon.NO_WEAPON -> "нет оружия"
        }
    }

    fun sortPrograms(numPlayers: Int) {
        if (numPlayers in 4..8) {
            programs.removeIf { it.name == "Лазер" || it.name == "Ракетница" || it.name == "Небольшой апдейт"}
        }
        else if (numPlayers in 9..10) {
            programs.removeIf {it.name == "Лазер"}
        }
    }
}