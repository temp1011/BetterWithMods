package betterwithmods.util.player;

/**
 * Penalty linked to fat (saturation).
 *
 * @author Koward
 */
public enum FatPenalty implements IPlayerPenalty {
    NO_PENALTY(1, "bwm.fat_penalty.none", true),
    PLUMP(0.9f, "bwm.fat_penalty.plump", true),
    CHUBBY(0.75F, "bwm.fat_penalty.chubby", true),
    FAT(0.5F, "bwm.fat_penalty.fat", true),
    OBESE(0.25F, "bwm.fat_penalty.obese", false);

    private final float modifier;
    private final String description;
    private final boolean canJump;

    FatPenalty(float modifier, String description, boolean canJump) {
        this.modifier = modifier;
        this.description = description;
        this.canJump = canJump;
    }

    @Override
    public boolean canJump() {
        return canJump;
    }

    @Override
    public float getModifier() {
        return modifier;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
