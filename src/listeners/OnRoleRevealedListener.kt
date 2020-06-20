package listeners

import Player

interface OnRoleRevealedListener {
    fun ownRoleRevealed(caster: Player, targets: List<Player>? = null)
    fun otherRoleRevealed(caster: Player)
}