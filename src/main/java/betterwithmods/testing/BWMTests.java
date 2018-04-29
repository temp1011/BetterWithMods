package betterwithmods.testing;

import betterwithmods.testing.base.BaseTest;
import com.google.common.collect.Lists;

import java.util.List;

public class BWMTests {

    public static void runTests() {
        List<BaseTest> TESTS = Lists.newArrayList(new BulkRecipeTests(), new CookingPotTests());
        TESTS.forEach(BaseTest::run);
    }

}
