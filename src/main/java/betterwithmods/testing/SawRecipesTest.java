package betterwithmods.testing;

import betterwithmods.common.registry.block.managers.SawManagerBlock;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.common.registry.block.recipe.SawRecipe;
import betterwithmods.testing.base.BaseBlockTest;
import betterwithmods.testing.base.Before;
import betterwithmods.testing.base.world.FakeWorld;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SawRecipesTest extends BaseBlockTest<SawRecipe> {

    @Before
    @Override
    public void beforeTest() {
        world = new FakeWorld();

        TEST_MANAGER = new SawManagerBlock();
        world.setState(Blocks.PLANKS.getDefaultState());

        BlockIngredient ingredient = new BlockIngredient(Blocks.PLANKS.getDefaultState());
        BlockIngredient oreIngredient = new BlockIngredient("plankWood");

        List<ItemStack> outputs = Lists.newArrayList(new ItemStack(Items.IRON_INGOT));
        recipe = new SawRecipe(ingredient, outputs);
        oreRecipe = new SawRecipe(oreIngredient, outputs);

    }
}
