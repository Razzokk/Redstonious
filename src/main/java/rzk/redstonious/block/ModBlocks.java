package rzk.redstonious.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rzk.redstonious.Redstonious;
import rzk.redstonious.item.ModItems;

public class ModBlocks
{
	public static final Block ANALOG_REDSTONE_BLOCK = new AnalogRedstoneBlock();

	public static void registerBlocks()
	{
		registerBlock("analog_redstone_block", ANALOG_REDSTONE_BLOCK);
	}

	public static void registerBlockWithoutItem(String name, Block block)
	{
		Registry.register(Registries.BLOCK, Redstonious.identifier(name), block);
	}

	public static void registerBlock(String name, Block block)
	{
		registerBlockWithoutItem(name, block);
		ModItems.registerItem(name, new BlockItem(block, new FabricItemSettings()));
	}
}
