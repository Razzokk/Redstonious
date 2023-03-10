package rzk.redstonious.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import rzk.redstonious.block.ModBlocks;
import rzk.redstonious.blockentity.ModBlockEntities;
import rzk.redstonious.client.render.TestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class RedstoniousClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.REDSTONE_EMITTER);
		BlockEntityRendererRegistry.register(ModBlockEntities.TEST_BLOCK_ENTITY, TestBlockEntityRenderer::new);
	}
}
