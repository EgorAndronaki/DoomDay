package listeners

import Player

interface OnWeaponPickedListener {
    fun onWeaponPicked(caster: Player)
}