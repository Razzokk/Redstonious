package rzk.redstonious.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class MountedFacingBlock extends Block
{
	public static final DirectionProperty MOUNTED = DirectionProperty.of("mounted");

	private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);
	private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0, 14, 0, 16, 16, 16);
	private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 2);
	private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 0, 14, 16, 16, 16);
	private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 0, 0, 2, 16, 16);
	private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(14, 0, 0, 16, 16, 16);

	protected MountedFacingBlock(Settings settings)
	{
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(MOUNTED, Direction.DOWN).with(HORIZONTAL_FACING, Direction.NORTH));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return switch (state.get(MOUNTED))
				{
					case DOWN -> DOWN_SHAPE;
					case UP -> UP_SHAPE;
					case NORTH -> NORTH_SHAPE;
					case SOUTH -> SOUTH_SHAPE;
					case WEST -> WEST_SHAPE;
					case EAST -> EAST_SHAPE;
				};
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		Direction mounted = ctx.getSide().getOpposite();
		BlockState state = getDefaultState().with(MOUNTED, mounted);

		if (mounted.getHorizontal() == -1)
			state = state.with(HORIZONTAL_FACING, ctx.getPlayerFacing());

		return state;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		Direction facing = state.get(HORIZONTAL_FACING);

		facing = switch (facing)
		{
			case NORTH -> Direction.EAST;
			case SOUTH -> Direction.WEST;
			case EAST -> Direction.SOUTH;
			default -> Direction.NORTH;
		};

		world.setBlockState(pos, state.with(HORIZONTAL_FACING, facing));
		return ActionResult.SUCCESS;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, MOUNTED);
	}
}
