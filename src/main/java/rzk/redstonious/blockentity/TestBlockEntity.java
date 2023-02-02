package rzk.redstonious.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TestBlockEntity extends BlockEntity
{
	private byte connections = 0;
	public final HitField[] hitFields =
			{
					// Top field
					new HitField(0, 1.0 / 3, 0, 2.0 / 3, 1.0 / 3),
					// Left field
					new HitField(1, 0, 1.0 / 3, 1.0 / 3, 2.0 / 3),
					// Right field
					new HitField(2, 2.0 / 3, 1.0 / 3, 1, 2.0 / 3),
					// Bottom field
					new HitField(3, 1.0 / 3, 2.0 / 3, 2.0 / 3, 1),
			};

	public TestBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModBlockEntities.TEST_BLOCK_ENTITY, pos, state);
	}

	public byte getConnections()
	{
		return connections;
	}

	private boolean handleField(int index, PlayerEntity player, Hand hand)
	{
		byte mask = (byte) (1 << index);

		if ((connections & mask) != 0)
		{
			if (!player.isCreative())
				player.giveItemStack(new ItemStack(Items.REDSTONE_TORCH));

			connections ^= mask;
			markDirty();
			return true;
		}

		ItemStack heldStack = player.getStackInHand(hand);

		if (!heldStack.isOf(Items.REDSTONE_TORCH))
			return false;

		if (!player.isCreative())
			heldStack.decrement(1);

		connections ^= mask;
		markDirty();
		return true;
	}

	public ActionResult onBlockClicked(PlayerEntity player, Hand hand, double x, double y, double z)
	{
//		if (y != 0.125) return ActionResult.PASS;

		int index = -1;

		for (int i = 0; i < hitFields.length && index == -1; i++)
			if (hitFields[i].isIn(x, z))
				index = hitFields[i].index;

		if (index != -1 && handleField(index, player, hand))
			return ActionResult.SUCCESS;

		return ActionResult.PASS;
	}

	@Override
	public void readNbt(NbtCompound nbt)
	{
		super.readNbt(nbt);
		connections = nbt.getByte("connections");
	}

	@Override
	protected void writeNbt(NbtCompound nbt)
	{
		nbt.putByte("connections", connections);
		super.writeNbt(nbt);
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket()
	{
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt()
	{
		return createNbt();
	}

	public record HitField(int index, double xMin, double zMin, double xMax, double zMax)
	{
		public boolean isIn(double x, double z)
		{
			return x >= xMin && x <= xMax && z >= zMin && z <= zMax;
		}
	}
}
