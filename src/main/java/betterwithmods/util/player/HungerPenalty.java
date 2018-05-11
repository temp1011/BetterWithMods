package betterwithmods.util.player;

/**
 * Penalty linked to hunger (food level).
 *
 * @author Koward
 */
public class HungerPenalty extends BasicPenalty {
    public static HungerPenalty NONE;
    public static HungerPenalty PECKISH;
    public static HungerPenalty HUNGRY;
    public static HungerPenalty FAMISHED;
    public static HungerPenalty STARVING;
    public static HungerPenalty DYING;

    public HungerPenalty(String name, String description, float modifier, boolean canSprint, boolean canJump) {
        super("hardcore.hchunger", name, description, modifier, canSprint, canJump);
    }

    public static void init() {
        NONE = new HungerPenalty("none", "bwm.hunger_penalty.none", 1f, true, true);
        PECKISH = new HungerPenalty("peckish", "bwm.hunger_penalty.peckish", .75F, true, false);
        HUNGRY = new HungerPenalty("hungry", "bwm.hunger_penalty.hungry", 0.75F, true, false);
        FAMISHED = new HungerPenalty("famished", "bwm.hunger_penalty.famished", 0.5F, false, false);
        STARVING = new HungerPenalty("starving", "bwm.hunger_penalty.starving", 0.25F, false, false);
        DYING = new HungerPenalty("dying", "bwm.hunger_penalty.dying", .25F, false, false);
    }
}

