package betterwithmods.module.tweaks;

import betterwithmods.common.blocks.BlockBDispenser;
import betterwithmods.common.blocks.BlockUnfiredPottery;
import betterwithmods.common.blocks.tile.TileKiln;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.Feature;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlaceClay extends Feature {


    @Override
    public String getFeatureDescription() {
        return "Clay and Nether Sludge are placeable blocks.";
    }

    @Override
    public void init(FMLInitializationEvent event) {
        //TODO
//        KilnManager.INSTANCE.addRecipe(new KilnRecipe(BWMBlocks.UNFIRED_POTTERY, BlockUnfiredPottery.EnumType.BRICK.getMeta(), Lists.newArrayList(new ItemStack(Items.BRICK))) {
//            @Override
//            public ItemStack getStack() {
//                return new ItemStack(Items.CLAY_BALL);
//            }
//        });
//        KilnManager.INSTANCE.addRecipe(new KilnRecipe(BWMBlocks.UNFIRED_POTTERY, BlockUnfiredPottery.EnumType.NETHER_BRICK.getMeta(), Lists.newArrayList(new ItemStack(Items.NETHERBRICK))) {
//            @Override
//            public ItemStack getStack() {
//                return ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.NETHER_SLUDGE);
//            }
//        });

        BlockBDispenser.BLOCK_DISPENSER_REGISTRY.putObject(Items.CLAY_BALL, (source, stack) -> dispenseBlock(source,stack,BlockUnfiredPottery.BLOCKS.get(BlockUnfiredPottery.EnumType.BRICK).getDefaultState()));
        BlockBDispenser.BLOCK_DISPENSER_REGISTRY.putObject(ItemMaterial.getItem(ItemMaterial.EnumMaterial.NETHER_SLUDGE), (source, stack) -> dispenseBlock(source,stack, BlockUnfiredPottery.BLOCKS.get(BlockUnfiredPottery.EnumType.NETHER_BRICK).getDefaultState()));
    }

    private ItemStack dispenseBlock(IBlockSource source, ItemStack stack, IBlockState stateToPlace) {
        IPosition pos = BlockBDispenser.getDispensePosition(source);
        BlockPos check = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        World world = source.getWorld();
        if ((world.isAirBlock(check) || world.getBlockState(check).getBlock().isReplaceable(world, check)) && stateToPlace.getBlock().canPlaceBlockAt(world, check)) {
            world.setBlockState(check, stateToPlace);
            stack.shrink(1);
            return stack.isEmpty() ? ItemStack.EMPTY : stack;
        }
        return stack;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlaceClay(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled())
            return;
        //TODO

//        if (event.getItemStack().isItemEqual(new ItemStack(Items.CLAY_BALL))) {
//            if (canPlaceAt(event.getEntityPlayer(), event.getWorld(), event.getPos())) {
//                PlayerContainer container = new PlayerContainer(event.getEntityPlayer(), BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.BRICK));
//                ItemBlock item = (ItemBlock) Item.getItemFromBlock(BWMBlocks.UNFIRED_POTTERY);
//                EnumActionResult result = item.onItemUse(container, event.getWorld(), event.getPos(), event.getHand(), event.getFace(), (float) event.getHitVec().x, (float) event.getHitVec().y, (float) event.getHitVec().z);
//                if (result == EnumActionResult.SUCCESS) {
//                    if (!event.getEntityPlayer().capabilities.isCreativeMode)
//                        event.getItemStack().shrink(1);
//                    event.getEntityPlayer().swingArm(event.getHand());
//                }
//            }
//        }
//
//        if (event.getItemStack().isItemEqual(ItemMaterial.getStack(ItemMaterial.EnumMaterial.NETHER_SLUDGE))) {
//            if (canPlaceAt(event.getEntityPlayer(), event.getWorld(), event.getPos())) {
//                PlayerContainer container = new PlayerContainer(event.getEntityPlayer(), BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.NETHER_BRICK));
//                ItemBlock item = (ItemBlock) Item.getItemFromBlock(BWMBlocks.UNFIRED_POTTERY);
//                EnumActionResult result = item.onItemUse(container, event.getWorld(), event.getPos(), event.getHand(), event.getFace(), (float) event.getHitVec().x, (float) event.getHitVec().y, (float) event.getHitVec().z);
//                if (result == EnumActionResult.SUCCESS) {
//                    if (!event.getEntityPlayer().capabilities.isCreativeMode)
//                        event.getItemStack().shrink(1);
//                    event.getEntityPlayer().swingArm(event.getHand());
//                }
//            }
//        }
//
    }

    private boolean canPlaceAt(EntityPlayer player, World world, BlockPos pos) {
        if (player.isSneaking())
            return true;
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && !(tile instanceof TileKiln))
            return false;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        return !(block instanceof BlockWorkbench || block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate);
    }


    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SuppressWarnings("EntityConstructor")
    private class PlayerContainer extends EntityPlayer {
        private EntityPlayer player;
        private ItemStack held;

        public PlayerContainer(World worldIn, GameProfile gameProfileIn) {
            super(worldIn, gameProfileIn);
        }

        public PlayerContainer(EntityPlayer player, ItemStack held) {
            super(player.getEntityWorld(), player.getGameProfile());
            this.player = player;
            this.held = held;
        }

        @Override
        public boolean isSpectator() {
            return player.isSpectator();
        }

        @Override
        public boolean isCreative() {
            return player.isCreative();
        }

        @Override
        public ItemStack getHeldItem(EnumHand hand) {
            return held;
        }

        @Override
        public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack) {
            return player.canPlayerEdit(pos, facing, stack);
        }
    }
}
