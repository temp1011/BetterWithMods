package betterwithmods.entity.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

/**
 * @author Koward
 *         Based on {@link EntityFallingBlock} build 2185
 */
public class EntityFallingSlab extends EntityFallingBlock {

    @SuppressWarnings("unused")
    public EntityFallingSlab(World worldIn) {
        super(worldIn);
    }

    public EntityFallingSlab(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
        super(worldIn, x, y, z, fallingBlockState);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //TODO slab merging
    }
}