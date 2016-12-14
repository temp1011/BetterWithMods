package betterwithmods.blocks;

import betterwithmods.BWMBlocks;
import betterwithmods.api.block.IMultiVariants;
import betterwithmods.client.BWCreativeTabs;
import betterwithmods.util.HardcoreFunctions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.BlockPlanks.VARIANT;

/**
 * @author Koward
 */
public class BlockStump extends Block implements IMultiVariants {
    //TODO drops, stump remover, hardness
    public BlockStump() {
        super(Material.WOOD);
        this.setCreativeTab(BWCreativeTabs.BWTAB);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK));
    }

    /**
     * Hardcore Stumping
     * Whether a stump could be placed or not.
     *
     * @param worldIn  The world where stump will be placed.
     * @param position The position at the base of the tree.
     * @param log      The log of the trunk that must be checked.
     * @return true when the trunk is high enough.
     */
    public static boolean canPlaceStump(World worldIn, BlockPos position, IBlockState log) {
        for (int i = 0; i < 2; i++) {
            IBlockState state = worldIn.getBlockState(position.up(i));
            if (!(state.getBlock() == log.getBlock())) return false;
            if (!(HardcoreFunctions.getWoodType(state) == HardcoreFunctions.getWoodType(log))) return false;
        }
        return true;
    }

    @Nullable
    public static IBlockState getStump(IBlockState log) {
        BlockPlanks.EnumType variant = HardcoreFunctions.getWoodType(log);
        if (variant == null) return null;
        return BWMBlocks.STUMP.getDefaultState().withProperty(BlockPlanks.VARIANT, variant);
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        BlockPlanks.EnumType type = state.getValue(VARIANT);
        return type.getMapColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values()) {
            list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public String[] getVariants() {
        ArrayList<String> variants = new ArrayList<>();
        for (BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values()) {
            variants.add("variant=" + blockplanks$enumtype.getName());
        }
        return variants.toArray(new String[BlockPlanks.EnumType.values().length]);
    }
}
