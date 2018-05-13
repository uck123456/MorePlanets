package stevekung.mods.moreplanets.utils.items;

public class ItemDungeonKeyMP extends ItemBaseMP implements IDungeonKey
{
    public ItemDungeonKeyMP(String name, int tier)
    {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName(name);
    }

    @Override
    public EnumSortCategoryItem getItemCategory()
    {
        return EnumSortCategoryItem.DUNGEON_KEY;
    }
}