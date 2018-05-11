package betterwithmods.util.player;

import betterwithmods.module.ConfigHelper;

public class BasicPenalty implements IPlayerPenalty {

    private float modifier;
    private String description;
    private boolean canJump, canSprint;


    public BasicPenalty(String category, String name, String description, float modifier, boolean canSprint, boolean canJump) {
        this.modifier = ((float) ConfigHelper.loadPropDouble("Modifier", category + "." + name, "number", modifier));
        this.description = description;
        this.canJump = ConfigHelper.loadPropBool("Jump", category + "." + name, "can jump", canJump);
        this.canSprint = ConfigHelper.loadPropBool("Sprint", category + "." + name, "can sprint", canSprint);

    }

    @Override
    public float getModifier() {
        return modifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean canJump() {
        return canJump;
    }

    @Override
    public boolean canSprint() {
        return canSprint;
    }


}
