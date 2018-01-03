package betterwithmods.module.gameplay.breeding_harness;

import betterwithmods.BWMod;
import betterwithmods.common.BWMItems;
import betterwithmods.common.items.ItemBreedingHarness;
import betterwithmods.module.Feature;
import betterwithmods.network.MessageHarnessSync;
import betterwithmods.network.NetworkHandler;
import betterwithmods.util.InvUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class BreedingHarness extends Feature {

    public BreedingHarness() {
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BWMItems.registerItem(BWMItems.BREEDING_HARNESS);
        CapabilityManager.INSTANCE.register(CapabilityHarness.class, new CapabilityHarness.Storage(), CapabilityHarness::new);
    }

    private static final ResourceLocation CAPABILITY = new ResourceLocation(BWMod.MODID, "harness");

    @SubscribeEvent
    public void onAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (harnessEntity(entity)) {
            event.addCapability(CAPABILITY, new CapabilityHarness());
        }
    }

    @SubscribeEvent
    public void onTrack(PlayerEvent.StartTracking event) {
        if(event.getEntityPlayer().world.isRemote)
            return;
        Entity entity = event.getTarget();
        CapabilityHarness cap = getCapability(entity);
        if(cap != null) {
            NetworkHandler.sendToAllAround(new MessageHarnessSync(entity.getEntityId(), cap.getHarness()), entity.getEntityWorld(), entity.getPosition());
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getWorld().isRemote)
            return;
        Entity entity = event.getTarget();

        CapabilityHarness cap = getCapability(entity);
        if (cap != null) {
            ItemStack hand = event.getItemStack();
            ItemStack harness = cap.getHarness();
            if (harness.isEmpty() && !event.getEntityPlayer().isSneaking()) {
                if (hand.getItem() instanceof ItemBreedingHarness) {
                    cap.setHarness(InvUtils.setCount(hand.copy(), 1));
                    hand.shrink(1);
                    event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5f, 1.3f);
                    event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.NEUTRAL, 0.5f, 1.3f);
                    event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
                }
            } else if (!harness.isEmpty() && event.getEntityPlayer().isSneaking() && hand.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), harness.copy());
                harness.shrink(1);
                event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 1, 1);
                event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.NEUTRAL, 1, 1f);
                event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
            }
            NetworkHandler.sendToAllAround(new MessageHarnessSync(entity.getEntityId(), cap.getHarness()), event.getWorld(), event.getPos());
        }

    }


    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase entity = e.getEntityLiving();

        CapabilityHarness cap = getCapability(entity);
        if (cap != null && cap.getHarness().getItem() instanceof ItemBreedingHarness) {
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(-1);
        } else {
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        }
    }


    public static CapabilityHarness getCapability(Entity entity) {
        if (entity.hasCapability(CapabilityHarness.HARNESS_CAPABILITY, null)) {
            return entity.getCapability(CapabilityHarness.HARNESS_CAPABILITY, null);
        }
        return null;
    }

    public static boolean hasHarness(Entity entity) {
        CapabilityHarness cap = getCapability(entity);
        return cap != null && cap.getHarness().getItem() instanceof ItemBreedingHarness;
    }

    public static boolean harnessEntity(Entity entity) {
        return entity instanceof EntityCow;
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }


    public class HarnessListener implements IWorldEventListener {

        @Override
        public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {

        }

        @Override
        public void notifyLightSet(BlockPos pos) {

        }

        @Override
        public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {

        }

        @Override
        public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {

        }

        @Override
        public void playRecord(SoundEvent soundIn, BlockPos pos) {

        }

        @Override
        public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {

        }

        @Override
        public void spawnParticle(int id, boolean ignoreRange, boolean p_190570_3_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {

        }

        @Override
        public void onEntityAdded(Entity entity) {
            if (entity.world == null || entity.world.isRemote)
                return;
            CapabilityHarness cap = getCapability(entity);
            if (cap != null) {
                NetworkHandler.sendToAllAround(new MessageHarnessSync(entity.getEntityId(), cap.getHarness()), entity.world, entity.getPosition());
            }
        }

        @Override
        public void onEntityRemoved(Entity entityIn) {

        }

        @Override
        public void broadcastSound(int soundID, BlockPos pos, int data) {

        }

        @Override
        public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {

        }

        @Override
        public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {

        }
    }
}
