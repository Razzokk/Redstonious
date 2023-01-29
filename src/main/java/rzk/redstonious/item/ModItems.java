package rzk.redstonious.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rzk.redstonious.Redstonious;

public class ModItems
{
	public static void registerItems()
	{

	}

	public static void registerItem(String name, Item item)
	{
		Registry.register(Registries.ITEM, Redstonious.identifier(name), item);
	}
}
