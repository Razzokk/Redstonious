package rzk.redstonious.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import rzk.redstonious.Redstonious;
import rzk.redstonious.block.AnalogLamp;
import rzk.redstonious.block.ModBlocks;
import rzk.redstonious.block.MountedFacingBlock;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider
{
	public ModelProvider(FabricDataOutput output)
	{
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
	{
		redstoneEmitter(blockStateModelGenerator, ModBlocks.REDSTONE_EMITTER);

		blockStateModelGenerator.blockStateCollector.accept(
				VariantsBlockStateSupplier.create(ModBlocks.ANALOG_LAMP)
						.coordinate(BlockStateVariantMap.create(Properties.POWER)
								.register(power -> BlockStateVariant.create()
										.put(VariantSettings.MODEL, power == 0 ?
												blockStateModelGenerator.createSubModel(ModBlocks.ANALOG_LAMP, "_off", Models.CUBE_ALL, TextureMap::all) :
												blockStateModelGenerator.createSubModel(ModBlocks.ANALOG_LAMP, "_on", Models.CUBE_ALL, TextureMap::all)))));

		mountedFacingBlock(blockStateModelGenerator, ModBlocks.MOUNTED_FACING_BLOCK);

		blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.TEST_BLOCK, BlockStateVariant.create()
				.put(VariantSettings.MODEL, Redstonious.identifier("block/test_block"))));
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		itemModelGenerator.register(ModBlocks.REDSTONE_EMITTER.asItem(), new Model(Optional.of(TextureMap.getSubId(ModBlocks.REDSTONE_EMITTER, "_0")), Optional.empty()));
		itemModelGenerator.register(ModBlocks.ANALOG_LAMP.asItem(), new Model(Optional.of(TextureMap.getSubId(ModBlocks.ANALOG_LAMP, "_off")), Optional.empty()));
	}

	private static void redstoneEmitter(BlockStateModelGenerator blockStateModelGenerator, Block block)
	{
		Model model = new Model(Optional.of(Redstonious.identifier("block/template/redstone_emitter")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);

		blockStateModelGenerator.blockStateCollector.accept(
				VariantsBlockStateSupplier.create(block)
						.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
						.coordinate(BlockStateVariantMap.create(Properties.POWER)
								.register(power -> BlockStateVariant.create().put(VariantSettings.MODEL,
										blockStateModelGenerator.createSubModel(block, "_" + power, model, id -> new TextureMap()
												.put(TextureKey.TOP, TextureMap.getSubId(block, "/top_" + power))
												.put(TextureKey.BOTTOM, TextureMap.getSubId(block, "/bottom"))
												.put(TextureKey.SIDE, TextureMap.getSubId(block, "/side")))))));
	}

	private static VariantSettings.Rotation getMountedFacingXRotation(Direction mounted, Direction facing)
	{
		if (mounted.getHorizontal() == -1)
			return mounted == Direction.DOWN ? VariantSettings.Rotation.R0 : VariantSettings.Rotation.R180;

		return switch (facing)
				{
					case SOUTH -> VariantSettings.Rotation.R180;
					case WEST -> VariantSettings.Rotation.R270;
					case EAST -> VariantSettings.Rotation.R90;
					default -> VariantSettings.Rotation.R0;
				};
	}

	private static VariantSettings.Rotation getMountedFacingYRotation(Direction mounted, Direction facing)
	{
		return switch (mounted)
				{
					case DOWN -> switch (facing)
							{
								case SOUTH -> VariantSettings.Rotation.R180;
								case WEST -> VariantSettings.Rotation.R270;
								case EAST -> VariantSettings.Rotation.R90;
								default -> VariantSettings.Rotation.R0;
							};
					case UP -> switch (facing)
							{
								case NORTH -> VariantSettings.Rotation.R180;
								case WEST -> VariantSettings.Rotation.R90;
								case EAST -> VariantSettings.Rotation.R270;
								default -> VariantSettings.Rotation.R0;
							};
					case NORTH -> VariantSettings.Rotation.R90;
					case SOUTH -> VariantSettings.Rotation.R270;
					case WEST -> VariantSettings.Rotation.R0;
					case EAST -> VariantSettings.Rotation.R180;
				};
	}

	private static void mountedFacingBlock(BlockStateModelGenerator blockStateModelGenerator, Block block)
	{
		Identifier model = new Model(Optional.of(Redstonious.identifier("block/template/mounted_facing_block")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE)
				.upload(block, TextureMap.sideTopBottom(block), blockStateModelGenerator.modelCollector);
		Identifier wallModel = new Model(Optional.of(Redstonious.identifier("block/template/mounted_facing_block_wall")), Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE)
				.upload(block, "_wall", TextureMap.sideTopBottom(block), blockStateModelGenerator.modelCollector);

		blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(MountedFacingBlock.MOUNTED, Properties.HORIZONTAL_FACING)
						.register((mounted, facing) -> BlockStateVariant.create()
								.put(VariantSettings.MODEL, mounted.getHorizontal() == -1 ? model : wallModel)
								.put(VariantSettings.X, getMountedFacingXRotation(mounted, facing))
								.put(VariantSettings.Y, getMountedFacingYRotation(mounted, facing)))));
	}
}
