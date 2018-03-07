package betterwithmods.module.tweaks;

import betterwithmods.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class MushroomFarming extends Feature {
    private static int maxLightLevel;
    private static boolean deleteShrooms;
    private static HashSet<String> validMushrooms;

    @Override
    public void setupConfig() {
        maxLightLevel = loadPropInt("Maximum Light Level", "The highest lightlevel at which mushrooms will grow.", 0);
        deleteShrooms = loadPropBool("Delete Mushrooms", "Whether mushrooms should be deleted instead of popping off.", true);
        validMushrooms = loadPropStringHashSet("Valid Mushrooms","Registry names of affected mushrooms",new String[]{"minecraft:brown_mushroom","minecraft:red_mushroom"});
    }

    @Override
    public void init(FMLInitializationEvent event) {
        Blocks.BROWN_MUSHROOM.setLightLevel(0);
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @Override
    public String getFeatureDescription() {
        return "Mushrooms can only be farmed in complete darkness.";
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void placeMushroom(BlockEvent.PlaceEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getPlacedBlock();

        if(!world.isRemote && isMushroom(state) && !canGrowMushroom(world,pos))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void neighborUpdate(BlockEvent.NeighborNotifyEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();

        if(!world.isRemote)
        {
            popOffMushroom(world,pos);
            popOffMushroom(world,pos.up());
        }
    }

    private boolean isMushroom(IBlockState state)
    {
        return validMushrooms.contains(state.getBlock().getRegistryName().toString());
    }

    private void popOffMushroom(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        if(isMushroom(state) && !canGrowMushroom(world,pos))
        {
            if(!deleteShrooms)
                state.getBlock().dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    private boolean canGrowMushroom(World world,BlockPos pos)
    {
        int light = world.getLight(pos);

        return light <= maxLightLevel;
    }
}
