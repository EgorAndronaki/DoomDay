package roles.rogue

import Player
import roles.BaseRole
import startSet.Faction

class SloyWatson(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
                 override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, players: List<Player>) {

    }

    override fun useEffect(caster: Player, players: List<Player>) {
        val i1 = if (players.indexOf(caster) - 1 < 0) players.size - 1 else players.indexOf(caster) - 1
        val i2 = if (players.indexOf(caster) + 1 == players.size) 0 else players.indexOf(caster) + 1
        val left = players[i1]
        val right = players[i2]
        println("${left.name} должен открыть правую карту верности, а ${right.name} - левую, иначе они потеряют по жизни!")
        if (!left.loyaltyCards["Правая"]!!.hidden) {
            left.lives--
            println("${left.name} теряет жизнь, так как правая карта уже открыта!")
            if (left.lives <= 0) {
                println("${left.name} убили!")
            }
        }
        else {
            caster.openLoyalty(left, "Правая")
        }
        if (!right.loyaltyCards["Левая"]!!.hidden) {
            right.lives--
            println("${right.name} теряет жизнь, так как левая карта уже открыта!")
            if (right.lives <= 0) {
                println("${right.name} убили!")
            }
        }
        else {
            caster.openLoyalty(right, "Левая")
        }
    }
}