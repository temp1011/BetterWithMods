package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.BWMod;
import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.common.BWMBlocks;
import betterwithmods.module.Feature;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMoulding;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockSiding;
import betterwithmods.module.gameplay.miniblocks.client.MiniModel;
import betterwithmods.module.gameplay.miniblocks.tiles.TileCorner;
import betterwithmods.module.gameplay.miniblocks.tiles.TileMoulding;
import betterwithmods.module.gameplay.miniblocks.tiles.TileSiding;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class MiniBlocks extends Feature {

    public static final Set<ItemStack> MATERIALS = Sets.newHashSet();
    public static Block SIDING = new BlockSiding().setRegistryName("siding");
    public static Block MOULDING = new BlockMoulding().setRegistryName("moulding");
    public static Block CORNER = new BlockMoulding().setRegistryName("corner");


    public MiniBlocks() {
        enabledByDefault = false;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BWMBlocks.registerBlock(SIDING, new ItemMini(SIDING));
        BWMBlocks.registerBlock(MOULDING, new ItemMini(MOULDING));
        BWMBlocks.registerBlock(CORNER, new ItemMini(CORNER));
        GameRegistry.registerTileEntity(TileSiding.class, "bwm.siding");
        GameRegistry.registerTileEntity(TileMoulding.class, "bwm.moulding");
        GameRegistry.registerTileEntity(TileCorner.class, "bwm.corner");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        MATERIALS.addAll(Lists.newArrayList(new ItemStack(Blocks.PLANKS), new ItemStack(Blocks.PLANKS, 1, 1), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 3)));
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
        registerModel(event.getModelRegistry(), "siding", MiniModel.SIDING);
        registerModel(event.getModelRegistry(), "moulding", MiniModel.MOULDING);
        registerModel(event.getModelRegistry(), "corner", MiniModel.CORNER);
    }
}
