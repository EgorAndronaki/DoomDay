package programs

import Player
import startSet.Time

class Trust(override val name: String, override val timeOfPlay: Time, override val description: String,
            override val const: Boolean) : BaseProgram {
    override fun execute(caster: Player, target: Player?, targets: List<Player>?) {
        println("${caster.name} показывает ${target!!.name} свои карты роли и верности!")
        println("Роль ${caster.name} - ${caster.role.name} (${caster.factionToString(caster.role.faction)})! \n" +
                "Способности роли: ${caster.role.description} \n" +
                "Карты верности: левая - ${caster.loyaltyCardToString(caster.loyaltyCards["Левая"])}, правая - ${caster.loyaltyCardToString(caster.loyaltyCards["Правая"])} \n")
    }
}