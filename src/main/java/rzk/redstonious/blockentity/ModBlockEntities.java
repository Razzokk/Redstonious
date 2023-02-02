package rzk.redstonious.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rzk.redstonious.Redstonious;
import rzk.redstonious.block.ModBlocks;

public class ModBlockEntities
{
	public static final BlockEntityType<TestBlockEntity> TEST_BLOCK_ENTITY =
			FabricBlockEntityTypeBuilder.create(TestBlockEntity::new, ModBlocks.TEST_BLOCK).build();

	public static void registerBlockEntities()
	{
		registerBlockEntity("test_block_entity", TEST_BLOCK_ENTITY);
	}

	private static <T extends BlockEntity> void registerBlockEntity(String name, BlockEntityType<T> blockEntityType)
	{
		Registry.register(Registries.BLOCK_ENTITY_TYPE, Redstonious.identifier(name), blockEntityType);
	}
}
