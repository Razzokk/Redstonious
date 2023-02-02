package rzk.redstonious;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rzk.redstonious.block.ModBlocks;
import rzk.redstonious.blockentity.ModBlockEntities;
import rzk.redstonious.item.ModItems;

public class Redstonious implements ModInitializer
{
	public static final String MODID = "redstonious";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier identifier(String path)
	{
		return new Identifier(MODID, path);
	}

	@Override
	public void onInitialize()
	{
		LOGGER.info("Registering blocks...");
		ModBlocks.registerBlocks();
		LOGGER.info("Registered blocks");

		LOGGER.info("Registering items...");
		ModItems.registerItems();
		LOGGER.info("Registered items");

		LOGGER.info("Registering block entities...");
		ModBlockEntities.registerBlockEntities();
		LOGGER.info("Registered block entities");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries ->
		{
			entries.add(ModBlocks.REDSTONE_EMITTER);
			entries.add(ModBlocks.ANALOG_LAMP);
			entries.add(ModBlocks.MOUNTED_FACING_BLOCK);
			entries.add(ModBlocks.TEST_BLOCK);
		});
	}
}
