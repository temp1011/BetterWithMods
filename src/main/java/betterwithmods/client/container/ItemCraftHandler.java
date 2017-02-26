package betterwithmods.client.container;

import betterwithmods.blocks.tile.TileEntitySteelAnvil;
import betterwithmods.craft.steelanvil.SteelCraftingManager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.items.ItemStackHandler;

public class ItemCraftHandler extends ItemStackHandler {

    public InventoryCrafting crafting;
    private TileEntitySteelAnvil te;

    public ItemCraftHandler(int size, TileEntitySteelAnvil te)
    {
        super(size);
        this.te = te;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if(crafting != null)
            te.setResult(SteelCraftingManager.getInstance().findMatchingRecipe(this.crafting, te.getWorld()));
        super.onContentsChanged(slot);
    }
}