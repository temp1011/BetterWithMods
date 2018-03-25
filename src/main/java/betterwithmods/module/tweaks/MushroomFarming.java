package betterwithmods.module.tweaks;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.BlockBeacon;
import betterwithmods.common.blocks.BlockMushroom;
import betterwithmods.module.Feature;
import betterwithmods.module.hardcore.beacons.CapabilityBeacon;
import betterwithmods.module.hardcore.beacons.EnderchestCap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class MushroomFarming extends Feature {
    public static boolean ALIAS_MUSHROOMS;
    public static int MAX_LIGHT_LEVEL;
    public static HashSet<String> VALID_MUSHROOMS;

    public static final Block RED_MUSHROOM = new BlockMushroom().setRegistryName("minecraft:red_mushroom");
    public static final Block BROWN_MUSHROOM = new BlockMushroom().setRegistryName("minecraft:brown_mushroom");

    @Override
    public void setupConfig() {
        MAX_LIGHT_LEVEL = loadPropInt("Maximum Light Level", "The highest lightlevel at which mushrooms will grow.", 0);
        VALID_MUSHROOMS = loadPropStringHashSet("Valid Other Mushrooms","Registry names of affected mushrooms other than vanilla ones.",new String[]{});
        ALIAS_MUSHROOMS = loadPropBool("Alias Mushrooms","Aliases vanilla mushrooms to truly prevent them from growing. Turn this off if it causes conflicts.",true);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        if(ALIAS_MUSHROOMS) {
            BWMBlocks.registerBlock(RED_MUSHROOM);
            BWMBlocks.registerBlock(BROWN_MUSHROOM);
        }
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

        if(isMushroom(state) && !canGrowMushroom(world,pos))
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
        Block block = state.getBlock();
        return block.getRegistryName() != null && VALID_MUSHROOMS.contains(block.getRegistryName().toString());
    }

    private void popOffMushroom(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        if(isMushroom(state) && !canGrowMushroom(world,pos))
        {
            state.getBlock().dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    private boolean canGrowMushroom(World world,BlockPos pos)
    {
        int light = world.getLight(pos);
        IBlockState soil = world.getBlockState(pos.down());

        return light <= MAX_LIGHT_LEVEL || isMushroomSoil(soil);
    }

    public static boolean isMushroomSoil(IBlockState state)
    {
        if (state.getBlock() == Blocks.MYCELIUM)
            return true;
        else if (state.getBlock() == Blocks.DIRT && state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
            return true;
        return false;
    }
}
