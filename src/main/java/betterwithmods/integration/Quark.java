package betterwithmods.integration;

import betterwithmods.BWMBlocks;
import betterwithmods.config.BWConfig;
import betterwithmods.util.NetherSpawnWhitelist;
import betterwithmods.util.RecipeUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("unused")
public class Quark implements ICompatModule {

    public static final String MODID = "quark";
    public String[] wood = {"spruce", "birch", "jungle", "acacia", "dark_oak"};

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        NetherSpawnWhitelist.addBlock(Block.REGISTRY.getObject(new ResourceLocation(MODID, "basalt")), 0);

        for (int i = 0; i < 5; i++)
            GameRegistry.addShapedRecipe(new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(MODID, "custom_chest")), 1, i), "SSS", "S S", "SSS", 'S', new ItemStack(BWMBlocks.WOOD_SIDING, 1, i + 1));

    }

    @Override
    public void postInit() {
        if (BWConfig.removeLowTools) {
            RecipeUtils.removeRecipes(Items.STONE_HOE, OreDictionary.WILDCARD_VALUE);
            RecipeUtils.removeRecipes(Items.STONE_SWORD, OreDictionary.WILDCARD_VALUE);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preInitClient() {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initClient() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postInitClient() {
    }
}
