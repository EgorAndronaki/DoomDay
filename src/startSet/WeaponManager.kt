package startSet
import startSet.Weapon.*
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

enum class Weapon {
    PISTOL,
    RIFLE,
    PARTNER,
    MISSILE,
    LASER
}

class WeaponManager {
    private val weapons = mapOf(4 to listOf(RIFLE, PISTOL), 5 to listOf(RIFLE, RIFLE, PISTOL),
        6 to listOf(PISTOL, RIFLE, PARTNER), 7 to listOf(RIFLE, RIFLE, PISTOL, PARTNER), 8 to listOf(RIFLE, RIFLE, PISTOL, PARTNER),
        9 to listOf(RIFLE, RIFLE, PISTOL, PARTNER, MISSILE), 10 to listOf(RIFLE, RIFLE, PISTOL, PARTNER, MISSILE),
        11 to listOf(RIFLE, RIFLE, PISTOL, PARTNER, MISSILE, LASER), 12 to listOf(RIFLE, RIFLE, PISTOL, PARTNER, MISSILE, LASER),
        13 to listOf(RIFLE, RIFLE, PISTOL, PISTOL, PARTNER, MISSILE, LASER), 14 to listOf(RIFLE, RIFLE, PISTOL, PISTOL, PARTNER, MISSILE, LASER),
        15 to listOf(RIFLE, RIFLE, PISTOL, PISTOL, PARTNER, MISSILE, LASER), 16 to listOf(RIFLE, RIFLE, PISTOL, PISTOL, PARTNER, MISSILE, LASER))

    fun getWeapons(numPlayers: Int): MutableList<Weapon> {
        if (!weapons.containsKey(numPlayers))
            throw IllegalArgumentException("Wrong number of players.")
        return weapons.getOrElse(numPlayers) { throw  NullPointerException("No weapons found.")}.toMutableList()
    }
}