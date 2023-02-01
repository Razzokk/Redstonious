package rzk.redstonious.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import rzk.redstonious.Redstonious;
import rzk.redstonious.block.ModBlocks;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider
{
	public ModelProvider(FabricDataOutput output)
	{
		super(output);
	}

	private void redstoneEmitter(BlockStateModelGenerator blockStateModelGenerator, Block block)
	{
		Model model = new Model(Optional.of(Redstonious.identifier("block/redstone_emitter_template")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);

		blockStateModelGenerator.blockStateCollector.accept(
				VariantsBlockStateSupplier.create(block)
						.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
						.coordinate(BlockStateVariantMap.create(Properties.POWER).register(power ->
								BlockStateVariant.create().put(VariantSettings.MODEL,
										blockStateModelGenerator.createSubModel(block, "_" + power, model, id -> new TextureMap()
												.put(TextureKey.TOP, TextureMap.getSubId(block, "/top_" + power))
												.put(TextureKey.BOTTOM, TextureMap.getSubId(block, "/bottom"))
												.put(TextureKey.SIDE, TextureMap.getSubId(block, "/side")))))));
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
	{
		redstoneEmitter(blockStateModelGenerator, ModBlocks.REDSTONE_EMITTER);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ANALOG_LAMP);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		itemModelGenerator.register(ModBlocks.REDSTONE_EMITTER.asItem(), new Model(Optional.of(TextureMap.getSubId(ModBlocks.REDSTONE_EMITTER, "_0")), Optional.empty()));
	}
}
