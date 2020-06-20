package listeners

import Player
import startSet.Weapon

interface OnWeaponDroppedListener {
    fun onWeaponDropped(caster: Player, weapon: Weapon)
}