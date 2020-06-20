package listeners

import Player

interface OnLoyaltyCardRevealedListener {
    fun onLoyaltyCardRevealed(caster: Player, target: Player? = null)
}