package betterwithmods.testing;

import betterwithmods.api.tile.IHeated;
import betterwithmods.common.registry.bulk.manager.CookingPotManager;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.common.registry.heat.BWMHeatRegistry;
import betterwithmods.testing.base.BaseTest;
import betterwithmods.testing.base.Before;
import betterwithmods.testing.base.MockInventory;
import betterwithmods.testing.base.Test;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreIngredient;
import org.fest.assertions.Assertions;

import java.util.List;


public class CookingPotTests extends BaseTest {

    private IHeated UNSTOKED_COOKING_POT = new IHeated() {
        private MockInventory inventory = MockInventory.createInventory(0, new ItemStack(Blocks.COBBLESTONE, 64));

        @Override
        public int getHeat(World world, BlockPos pos) {
            return 1;
        }

        @Override
        public World getWorld() {
            return null;
        }

        @Override
        public BlockPos getPos() {
            return null;
        }

        @Override
        public ItemStackHandler getInventory() {
            return inventory;
        }
    };

    private CookingPotManager TEST_MANAGER;

    private List<Ingredient> inputs = Lists.newArrayList(Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)));
    private List<Ingredient> inputs2 = Lists.newArrayList(new OreIngredient("cobblestone"));

    private List<ItemStack> outputs = Lists.newArrayList(new ItemStack(Items.DIAMOND));
    private CookingPotRecipe recipe = new CookingPotRecipe(inputs, outputs, BWMHeatRegistry.UNSTOKED_HEAT);
    private CookingPotRecipe oreRecipe = new CookingPotRecipe(inputs2, outputs, BWMHeatRegistry.UNSTOKED_HEAT);
    private CookingPotRecipe stokedRecipe = new CookingPotRecipe(inputs, outputs, BWMHeatRegistry.STOKED_HEAT);

    @Before
    public void beforeTest() {
        TEST_MANAGER = new CookingPotManager();
    }

    @Test
    public void testRecipeAddition() {
        Assertions.assertThat(recipe.isInvalid()).isFalse();

        Assertions.assertThat(TEST_MANAGER.getRecipes()).isEmpty();
        TEST_MANAGER.addRecipe(recipe);
        Assertions.assertThat(TEST_MANAGER.getRecipes()).hasSize(1);
    }

    @Test
    public void testRecipeRemoval() {
        Assertions.assertThat(recipe.isInvalid()).isFalse();

        Assertions.assertThat(TEST_MANAGER.getRecipes()).isEmpty();
        TEST_MANAGER.addRecipe(recipe);
        Assertions.assertThat(TEST_MANAGER.getRecipes()).isNotEmpty();
        TEST_MANAGER.remove(recipe);
    }

    @Test
    public void testRecipeMatching() {
        testRecipe(recipe);
    }

    @Test
    public void testOredictMatching() {
        testRecipe(oreRecipe);
    }

    @Test
    public void testWrongHeatRecipe() {
        Assertions.assertThat(stokedRecipe.isInvalid()).isFalse();

        Assertions.assertThat(TEST_MANAGER.getRecipes()).isEmpty();
        TEST_MANAGER.addRecipe(stokedRecipe);
        Assertions.assertThat(TEST_MANAGER.getRecipes()).hasSize(1);

        Assertions.assertThat(TEST_MANAGER.canCraft(UNSTOKED_COOKING_POT, UNSTOKED_COOKING_POT.getInventory())).isFalse();
        Assertions.assertThat(TEST_MANAGER.craftItem(null, UNSTOKED_COOKING_POT, UNSTOKED_COOKING_POT.getInventory())).isEmpty();
    }

    private void testRecipe(CookingPotRecipe recipe) {
        Assertions.assertThat(recipe.isInvalid()).isFalse();

        Assertions.assertThat(TEST_MANAGER.getRecipes()).isEmpty();
        TEST_MANAGER.addRecipe(recipe);
        Assertions.assertThat(TEST_MANAGER.getRecipes()).hasSize(1);

        Assertions.assertThat(TEST_MANAGER.canCraft(UNSTOKED_COOKING_POT, UNSTOKED_COOKING_POT.getInventory())).isTrue();
        Assertions.assertThat(TEST_MANAGER.craftItem(null, UNSTOKED_COOKING_POT, UNSTOKED_COOKING_POT.getInventory())).isNotEmpty();
    }
}
