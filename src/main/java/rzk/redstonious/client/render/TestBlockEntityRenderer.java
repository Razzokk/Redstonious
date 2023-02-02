package rzk.redstonious.client.render;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import rzk.redstonious.blockentity.TestBlockEntity;

public class TestBlockEntityRenderer implements BlockEntityRenderer<TestBlockEntity>
{
	public TestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx)
	{
	}

	@Override
	public void render(TestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		matrices.push();

		ItemStack torch = new ItemStack(Items.REDSTONE_TORCH);
		byte connections = entity.getConnections();

		for (int i = 0; i < entity.hitFields.length; i++)
		{
			if ((connections & (1 << i)) == 0) continue;

			double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0 + i * Math.PI / 8) / 8.0;

			matrices.push();
			matrices.translate((entity.hitFields[i].xMin() + entity.hitFields[i].xMax()) / 2, 0.5 + offset, (entity.hitFields[i].zMin() + entity.hitFields[i].zMax()) / 2);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));
			MinecraftClient.getInstance().getItemRenderer().renderItem(torch, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
			matrices.pop();

			matrices.push();
			matrices.translate((entity.hitFields[i].xMin() + entity.hitFields[i].xMax()) / 2, 0.5, (entity.hitFields[i].zMin() + entity.hitFields[i].zMax()) / 2);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 5));
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 9));
			matrices.scale(0.25f, (float) (0.25f + Math.sin((entity.getWorld().getTime() + tickDelta) / 8) * 0.5f), 0.25f);
			MinecraftClient.getInstance().getEntityRenderDispatcher().render(MinecraftClient.getInstance().player, 0,-0.5,0,0, tickDelta, matrices, vertexConsumers, light);
			matrices.pop();
		}

		matrices.pop();
	}
}
