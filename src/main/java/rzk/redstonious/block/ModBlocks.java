package rzk.redstonious.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rzk.redstonious.Redstonious;
import rzk.redstonious.item.ModItems;

public class ModBlocks
{
	public static final Block REDSTONE_EMITTER = new RedstoneEmitterBlock();
	public static final Block ANALOG_LAMP = new AnalogLamp();
	public static final Block MOUNTED_FACING_BLOCK = new MountedFacingBlock(AbstractBlock.Settings.of(Material.METAL));
	public static final Block TEST_BLOCK = new TestBlock(AbstractBlock.Settings.of(Material.METAL));

	public static void registerBlocks()
	{
		registerBlock("redstone_emitter", REDSTONE_EMITTER);
		registerBlock("analog_lamp", ANALOG_LAMP);
		registerBlock("mounted_facing_block", MOUNTED_FACING_BLOCK);
		registerBlock("test_block", TEST_BLOCK);
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
