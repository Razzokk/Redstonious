package rzk.redstonious.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rzk.redstonious.Redstonious;

public class DataGeneration implements DataGeneratorEntrypoint
{
	private static Logger LOGGER = LoggerFactory.getLogger(Redstonious.MODID);

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
	}
}
