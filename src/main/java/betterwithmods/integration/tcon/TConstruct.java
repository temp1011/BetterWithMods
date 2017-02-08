package betterwithmods.integration.tcon;

import betterwithmods.BWMBlocks;
import betterwithmods.BWMod;
import betterwithmods.integration.ICompatModule;
import betterwithmods.util.NetherSpawnWhitelist;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.smeltery.OreCastingRecipe;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.List;
import java.util.Set;

import static slimeknights.tconstruct.smeltery.TinkerSmeltery.*;

@SuppressWarnings("unused")
public class TConstruct implements ICompatModule {

    public static final String MODID = "tconstruct";
    public final Material soulforgedSteel = newTinkerMaterial("soulforgedSteel", 5066061);
    public final Material hellfire = newTinkerMaterial("hellfire", 14426647);
    public AbstractTrait mending;
    public FluidMolten soulforgeFluid;
    public FluidMolten hellfireFluid;

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        mending = new TraitMending();
        soulforgeFluid = fluidMetal("soulforged_steel", 5066061);
        soulforgeFluid.setTemperature(681);
        soulforgedSteel.addItem("ingotSoulforgedSteel", 1, Material.VALUE_Ingot);
        soulforgedSteel.addTrait(mending);
        hellfireFluid = fluidMetal("hellfire", 14426647);
        hellfireFluid.setTemperature(850);
        hellfire.addItem("ingotHellfire", 1, Material.VALUE_Ingot);
        hellfire.addTrait(TinkerTraits.autosmelt);

        TinkerRegistry.addMaterialStats(soulforgedSteel, new HeadMaterialStats(875, 12.0F, 6.0F, HarvestLevels.OBSIDIAN), new HandleMaterialStats(1.0F, 225), new ExtraMaterialStats(50));
        TinkerRegistry.addMaterialStats(hellfire, new HeadMaterialStats(325, 8.0F, 4.0F, HarvestLevels.DIAMOND), new HandleMaterialStats(0.75F, 75), new ExtraMaterialStats(25));
        registerMaterial(soulforgedSteel, soulforgeFluid, "SoulforgedSteel", 16);
        registerMaterial(hellfire, hellfireFluid, "Hellfire", 9, 8);
        //fixHellfireDust();
        netherWhitelist();
    }

    @Override
    public void postInit() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preInitClient() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initClient() {
        registerRenderInfo(soulforgedSteel, 5066061, 0.1F, 0.3F, 0.1F);
        registerRenderInfo(hellfire, 14426647, 0.0F, 0.2F, 0.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postInitClient() {

    }

    @SideOnly(Side.CLIENT)
    private void registerRenderInfo(Material material, int color, float shininess, float brightness, float hueshift) {
        material.setRenderInfo(new MaterialRenderInfo.Metal(color, shininess, brightness, hueshift));
    }

    private void netherWhitelist() {
        Block ore = Block.REGISTRY.getObject(new ResourceLocation(MODID, "ore"));
        NetherSpawnWhitelist.addBlock(ore, 0);
        NetherSpawnWhitelist.addBlock(ore, 1);
        NetherSpawnWhitelist.addBlock(Block.REGISTRY.getObject(new ResourceLocation(MODID, "slime_congealed")), 3);
        NetherSpawnWhitelist.addBlock(Block.REGISTRY.getObject(new ResourceLocation(MODID, "slime_congealed")), 4);
        NetherSpawnWhitelist.addBlock(Block.REGISTRY.getObject(new ResourceLocation(MODID, "slime_dirt")), 3);
        Block slimeGrass = Block.REGISTRY.getObject(new ResourceLocation(MODID, "slime_grass"));
        NetherSpawnWhitelist.addBlock(slimeGrass, 4);
        NetherSpawnWhitelist.addBlock(slimeGrass, 9);
        NetherSpawnWhitelist.addBlock(slimeGrass, 14);
    }

    private void registerMaterial(Material material, Fluid fluid, String oreSuffix, int blockValue) {
        registerMaterial(material, fluid, oreSuffix, blockValue, 1);
    }

    private void registerMaterial(Material material, Fluid fluid, String oreSuffix, int blockValue, int dustDivision) {
        MaterialIntegration mat = new MaterialIntegration(material, fluid, oreSuffix).setRepresentativeItem("ingot" + oreSuffix);
        mat.integrate();
        //mat.integrateRecipes();
        registerMeltingStuff(oreSuffix, fluid, blockValue, dustDivision);
        TinkerSmeltery.registerToolpartMeltingCasting(material);
        mat.registerRepresentativeItem();
    }

    private Material newTinkerMaterial(String name, int color) {
        Material mat = new Material(name, color);
        TinkerMaterials.materials.add(mat);
        return mat;
    }

    private FluidMolten fluidMetal(String name, int color) {
        FluidMolten fluid = new FluidMolten(name, color);
        return registerFluid(fluid);
    }

    private <T extends Fluid> T registerFluid(T fluid) {
        fluid.setUnlocalizedName(BWMod.MODID + ":" + fluid.getName());
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }


    private void fixHellfireDust() {
        Pair<List<ItemStack>, Integer> dustOre = Pair.of(OreDictionary.getOres("dustHellfire"), Material.VALUE_Ingot / 8);
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(dustOre.getLeft(), dustOre.getRight()), hellfireFluid));
    }

    /*Abridged version of TinkerSmeltery.registerOredictMeltingCasting
    Since both Hellfire and Soulforged Steel are technically alloys, there's no need to look for ore variants.
     */
    private void registerMeltingStuff(String ore, Fluid fluid, int blockValue, int dustDivision) {
        ImmutableSet.Builder<Pair<List<ItemStack>, Integer>> builder = ImmutableSet.builder();
        Pair<List<ItemStack>, Integer> nuggetOre = Pair.of(OreDictionary.getOres("nugget" + ore), Material.VALUE_Nugget);
        Pair<List<ItemStack>, Integer> ingotOre = Pair.of(OreDictionary.getOres("ingot" + ore), Material.VALUE_Ingot);
        Pair<List<ItemStack>, Integer> plateOre = Pair.of(OreDictionary.getOres("plate" + ore), Material.VALUE_Ingot);
        Pair<List<ItemStack>, Integer> gearOre = Pair.of(OreDictionary.getOres("gear" + ore), Material.VALUE_Ingot * 4);
        Pair<List<ItemStack>, Integer> blockOre = Pair.of(OreDictionary.getOres("block" + ore), Material.VALUE_Ingot * blockValue);
        Pair<List<ItemStack>, Integer> dustOre = Pair.of(OreDictionary.getOres("dust" + ore), Material.VALUE_Ingot / dustDivision);

        builder.add(nuggetOre, ingotOre, plateOre, gearOre, blockOre, dustOre);
        Set<Pair<List<ItemStack>, Integer>> knownOres = builder.build();


        // register oredicts
        for(Pair<List<ItemStack>, Integer> pair : knownOres) {
            TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(pair.getLeft(), pair.getRight()), fluid));
        }

        // register oredict castings!
        // ingot casting
        TinkerRegistry.registerTableCasting(new OreCastingRecipe(ingotOre.getLeft(),
                RecipeMatch.ofNBT(castIngot),
                fluid,
                ingotOre.getRight()));
        // nugget casting
        TinkerRegistry.registerTableCasting(new OreCastingRecipe(nuggetOre.getLeft(),
                RecipeMatch.ofNBT(castNugget),
                fluid,
                nuggetOre.getRight()));
        // block casting
        TinkerRegistry.registerBasinCasting(new OreCastingRecipe(blockOre.getLeft(),
                null, // no cast
                fluid,
                blockOre.getRight()));
        // plate casting
        TinkerRegistry.registerTableCasting(new OreCastingRecipe(plateOre.getLeft(),
                RecipeMatch.ofNBT(castPlate),
                fluid,
                plateOre.getRight()));
        // gear casting
        TinkerRegistry.registerTableCasting(new OreCastingRecipe(gearOre.getLeft(),
                RecipeMatch.ofNBT(castGear),
                fluid,
                gearOre.getRight()));

        // and also cast creation!
        for(FluidStack fs : castCreationFluids) {
            TinkerRegistry.registerTableCasting(new CastingRecipe(castIngot, RecipeMatch.of(ingotOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(castNugget, RecipeMatch.of(nuggetOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(castPlate, RecipeMatch.of(plateOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(castGear, RecipeMatch.of(gearOre.getLeft()), fs, true, true));
        }
    }
}
