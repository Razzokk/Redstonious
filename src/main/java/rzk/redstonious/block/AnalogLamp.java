package rzk.redstonious.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.POWER;

public class AnalogLamp extends Block
{
	public AnalogLamp()
	{
		super(Settings.of(Material.REDSTONE_LAMP).luminance(state -> state.get(POWER)));
		setDefaultState(stateManager.getDefaultState().with(POWER, 0));
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		World world = ctx.getWorld();
		BlockState state = getDefaultState();
		if (!world.isClient)
			state = state.with(POWER, world.getReceivedRedstonePower(ctx.getBlockPos()));
		return state;
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify)
	{
		if (world.isClient) return;
		int power = world.getReceivedRedstonePower(pos);

		if (state.get(POWER) == power) return;
		world.setBlockState(pos, state.with(POWER, power));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(POWER);
	}
}
