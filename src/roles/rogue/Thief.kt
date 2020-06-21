package roles.rogue

import Player
import roles.BaseRole
import startSet.Faction
import startSet.Weapon

class Thief(override val name: String, override val faction: Faction, override val always: Boolean, override val effect: String?,
            override val constEffect: String?, override var hidden: Boolean) : BaseRole {

    override fun useConstEffect(caster: Player, target: Player?, players: List<Player>) {
        return
    }

    override fun useEffect(caster: Player, players: List<Player>) {
        if (players.any {it.weapon.first != Weapon.NO_WEAPON && it != caster}) {
            println("Срабатывает эффект Вора! ${caster.name} крадет оружие у выбранного игрока!")
            println("Выберите вооруженного игрока:")
            println(players.filter { it.weapon.first != Weapon.NO_WEAPON && !it.weapon.second!!.contains(caster) }.joinToString { it.name })
            val name = readLine() ?: ""
            val target = players.find { it.name == name }
            caster.weapon = Pair(target!!.weapon.first, null)
            caster.aim(caster.weapon.first, players)
            target.weapon = Pair(Weapon.NO_WEAPON, null)
            println("${caster.name} крадет ${caster.weaponToString(caster.weapon.first)} у ${target.name}" +
                    " и целится в ${caster.weapon.second!!.joinToString {it.name}}!")
        }
    }
}