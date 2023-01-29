package rzk.redstonious.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static net.minecraft.state.property.Properties.POWER;

public class AnalogRedstoneBlock extends Block
{
	public AnalogRedstoneBlock()
	{
		super(Settings.copy(Blocks.REDSTONE_BLOCK));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if (!world.isClient)
		{
			int power = state.get(POWER) + 1;
			if (power > 15)
				power = 0;

			world.setBlockState(pos, state.with(POWER, power));
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public boolean emitsRedstonePower(BlockState state)
	{
		return true;
	}

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction)
	{
		return state.get(POWER);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(POWER);
	}
}
