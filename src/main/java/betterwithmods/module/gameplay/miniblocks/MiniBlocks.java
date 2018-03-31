package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.BWMod;
import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.module.Feature;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockCorner;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMoulding;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockSiding;
import betterwithmods.module.gameplay.miniblocks.client.MiniModel;
import betterwithmods.module.gameplay.miniblocks.tiles.TileCorner;
import betterwithmods.module.gameplay.miniblocks.tiles.TileMoulding;
import betterwithmods.module.gameplay.miniblocks.tiles.TileSiding;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class MiniBlocks extends Feature {

    public static HashMap<Material, Block> SIDINGS = Maps.newHashMap();
    public static HashMap<Material, Block> MOULDINGS = Maps.newHashMap();
    public static HashMap<Material, Block> CORNERS = Maps.newHashMap();
    public static Multimap<Material, IBlockState> MATERIALS = HashMultimap.create();

    private static Map<Material, String> names = Maps.newHashMap();

    static {
        names.put(Material.WOOD, "wood");
        names.put(Material.ROCK, "rock");
        names.put(Material.IRON, "iron");
    }

    static {
        for (Material material : names.keySet()) {
            String name = names.get(material);
            SIDINGS.put(material, new BlockSiding(material).setRegistryName(String.format("%s_%s", "siding", name)));
            MOULDINGS.put(material, new BlockMoulding(material).setRegistryName(String.format("%s_%s", "moulding", name)));
            CORNERS.put(material, new BlockCorner(material).setRegistryName(String.format("%s_%s", "corner", name)));
        }
    }


    public MiniBlocks() {
        enabledByDefault = false;
    }

    public static boolean isValidMini(IBlockState state) {
        return state.isFullBlock() && state.isOpaqueCube() && !state.getBlock().hasTileEntity(state) && !state.getBlock().getTickRandomly();
    }

    public static boolean isValidMini(ItemStack stack) {
        return !BWOreDictionary.hasPrefix(stack, "ore");
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        SIDINGS.values().forEach(b -> BWMBlocks.registerBlock(b, new ItemMini(b)));
        MOULDINGS.values().forEach(b -> BWMBlocks.registerBlock(b, new ItemMini(b)));
        CORNERS.values().forEach(b -> BWMBlocks.registerBlock(b, new ItemMini(b)));
        GameRegistry.registerTileEntity(TileSiding.class, "bwm.siding");
        GameRegistry.registerTileEntity(TileMoulding.class, "bwm.moulding");
        GameRegistry.registerTileEntity(TileCorner.class, "bwm.corner");


    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

        for (Block block : ForgeRegistries.BLOCKS) {
            NonNullList<ItemStack> stacks = NonNullList.create();
            block.getSubBlocks(null, stacks);

            for (ItemStack stack : stacks) {
                IBlockState state = BWMRecipes.getStateFromStack(stack);
                if (!isValidMini(stack) || !isValidMini(state))
                    continue;
                Material material = state.getMaterial();
                if (names.containsKey(material)) {
                    MATERIALS.put(material, state);
                }
            }
        }

        for (Material material : names.keySet()) {
            Block siding = SIDINGS.get(material);
            Block moulding = MOULDINGS.get(material);
            Block corner = CORNERS.get(material);
            addHardcoreRecipe(new MiniRecipe(siding, null));
            addHardcoreRecipe(new MiniRecipe(moulding, siding));
            addHardcoreRecipe(new MiniRecipe(corner, moulding));
        }
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    private void registerModel(IRegistry<ModelResourceLocation, IBakedModel> registry, String name, IBakedModel model) {
        registry.putObject(new ModelResourceLocation(BWMod.MODID + ":" + name, "normal"), model);
        registry.putObject(new ModelResourceLocation(BWMod.MODID + ":" + name, "inventory"), model);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPostBake(ModelBakeEvent event) {
        MiniModel.SIDING = new MiniModel(RenderUtils.getModel(new ResourceLocation(BWMod.MODID, "block/mini/siding")));
        MiniModel.MOULDING = new MiniModel(RenderUtils.getModel(new ResourceLocation(BWMod.MODID, "block/mini/moulding")));
        MiniModel.CORNER = new MiniModel(RenderUtils.getModel(new ResourceLocation(BWMod.MODID, "block/mini/corner")));
        for (Material material : names.keySet()) {
            String name = names.get(material);
            registerModel(event.getModelRegistry(), String.format("%s_%s", "siding", name), MiniModel.SIDING);
            registerModel(event.getModelRegistry(), String.format("%s_%s", "moulding", name), MiniModel.MOULDING);
            registerModel(event.getModelRegistry(), String.format("%s_%s", "corner", name), MiniModel.CORNER);
        }
    }
}
