package rzk.redstonious;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rzk.redstonious.block.ModBlocks;
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
	}
}
