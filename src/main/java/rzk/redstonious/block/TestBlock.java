package rzk.redstonious.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rzk.redstonious.blockentity.TestBlockEntity;

public class TestBlock extends Block implements BlockEntityProvider
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

	private static final VoxelShape HIT_FIELD_TOP = Block.createCuboidShape(16.0 / 3 + 0.2, 2.0, 0.2, 16.0 * 2 / 3 - 0.2, 2.05, 16.0 / 3 - 0.2);
	private static final VoxelShape HIT_FIELD_LEFT = Block.createCuboidShape(0.2, 2.0, 16.0 / 3 + 0.2, 16.0 / 3 - 0.2, 2.05, 16.0 * 2 / 3 - 0.2);
	private static final VoxelShape HIT_FIELD_RIGHT = Block.createCuboidShape(16.0 * 2 / 3 + 0.2, 2.0, 16.0 / 3 + 0.2, 16.0 - 0.2, 2.05, 16.0 * 2 / 3 - 0.2);
	private static final VoxelShape HIT_FIELD_BOTTOM = Block.createCuboidShape(16.0 / 3 + 0.2, 2.0, 16.0 * 2 / 3 + 0.2, 16.0 * 2 / 3 - 0.2, 2.05, 16.0 - 0.2);

	public TestBlock(Settings settings)
	{
		super(settings);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return VoxelShapes.union(SHAPE, HIT_FIELD_TOP, HIT_FIELD_LEFT, HIT_FIELD_RIGHT, HIT_FIELD_BOTTOM);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if (world.getBlockEntity(pos) instanceof TestBlockEntity blockEntity)
			return blockEntity.onBlockClicked(player, hand, hit.getPos().x - pos.getX(), hit.getPos().y - pos.getY(), hit.getPos().z - pos.getZ());

		return ActionResult.PASS;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new TestBlockEntity(pos, state);
	}
}
