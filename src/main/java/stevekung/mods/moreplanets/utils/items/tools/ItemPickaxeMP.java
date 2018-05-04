package stevekung.mods.moreplanets.utils.items.tools;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.core.MorePlanetsMod;
import stevekung.mods.moreplanets.integration.jei.MorePlanetsJEIPlugin;
import stevekung.mods.moreplanets.utils.client.renderer.IItemModelRender;
import stevekung.mods.moreplanets.utils.items.EnumSortCategoryItem;
import stevekung.mods.moreplanets.utils.items.ISortableItem;

public class ItemPickaxeMP extends ItemPickaxe implements ISortableItem, IItemModelRender
{
    private Item repairItem;
    private String name;

    public ItemPickaxeMP(String name, ToolMaterial material, Block block)
    {
        this(name, material, Item.getItemFromBlock(block));
    }

    public ItemPickaxeMP(String name, ToolMaterial material, Item item)
    {
        super(material);
        this.repairItem = item;
        this.setUnlocalizedName(name);
        MorePlanetsJEIPlugin.collectAnvilList(name, this, item);
    }

    public ItemPickaxeMP(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        this.name = name;
        return super.setUnlocalizedName(name);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return MorePlanetsMod.ITEM_TAB;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if (repair.getItem() == this.repairItem)
        {
            return true;
        }
        return false;
    }

    @Override
    public EnumSortCategoryItem getItemCategory()
    {
        return EnumSortCategoryItem.PICKAXE;
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}