package dev.vulture.mixin;

import dev.vulture.render.timing.TransferProbe;

import com.mojang.renderpearl.backend.vulkan.VulkanCommandEncoder;
import com.mojang.renderpearl.backend.vulkan.VulkanRenderPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

/**
 * Feeds {@link TransferProbe} from the one Vulkan encoder every {@code CommandEncoder} shares, which
 * is the only place that knows whether a pass is open whoever asked.
 * <p>
 * One injection per call rather than a name matching several overloads: the five-argument
 * {@code copyTextureToBuffer} hands over to the nine-argument one, and counting both would count one
 * copy twice.
 * <p>
 * The 26.3 half: the same probe on the backend encoder of that game, whose region clear names a
 * mip level beside the region.
 */
@Mixin(VulkanCommandEncoder.class)
public abstract class VulkanCommandEncoderTransferMixin {

	@Shadow
	private VulkanRenderPass currentRenderPass;

	@Unique
	private Supplier<String> vulture$open() {
		return this.currentRenderPass == null ? null : this.currentRenderPass.getLabel();
	}

	@Inject(method = "writeToBuffer(Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;Ljava/nio/ByteBuffer;)V",
			at = @At("HEAD"))
	private void vulture$writeToBuffer(CallbackInfo ci) {
		TransferProbe.seen("writeToBuffer", vulture$open());
	}

	@Inject(method = "copyToBuffer(Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;"
			+ "Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;)V", at = @At("HEAD"))
	private void vulture$copyToBuffer(CallbackInfo ci) {
		TransferProbe.seen("copyToBuffer", vulture$open());
	}

	@Inject(method = "writeToTexture(Lcom/mojang/renderpearl/api/textures/GpuTexture;Ljava/nio/ByteBuffer;IIIIII)V",
			at = @At("HEAD"))
	private void vulture$writeToTexture(CallbackInfo ci) {
		TransferProbe.seen("writeToTexture", vulture$open());
	}

	@Inject(method = "copyBufferToTexture(Lcom/mojang/renderpearl/api/buffers/GpuBufferSlice;IIII"
			+ "Lcom/mojang/renderpearl/api/textures/GpuTexture;IIIIII)V", at = @At("HEAD"))
	private void vulture$copyBufferToTexture(CallbackInfo ci) {
		TransferProbe.seen("copyBufferToTexture", vulture$open());
	}

	@Inject(method = "copyTextureToBuffer(Lcom/mojang/renderpearl/api/textures/GpuTexture;"
			+ "Lcom/mojang/renderpearl/api/buffers/GpuBuffer;JLjava/lang/Runnable;IIIII)V", at = @At("HEAD"))
	private void vulture$copyTextureToBuffer(CallbackInfo ci) {
		TransferProbe.seen("copyTextureToBuffer", vulture$open());
	}

	@Inject(method = "copyTextureToTexture(Lcom/mojang/renderpearl/api/textures/GpuTexture;"
			+ "Lcom/mojang/renderpearl/api/textures/GpuTexture;IIIIIII)V", at = @At("HEAD"))
	private void vulture$copyTextureToTexture(CallbackInfo ci) {
		TransferProbe.seen("copyTextureToTexture", vulture$open());
	}

	@Inject(method = "clearColorTexture(Lcom/mojang/renderpearl/api/textures/GpuTexture;Lorg/joml/Vector4fc;)V",
			at = @At("HEAD"))
	private void vulture$clearColorTexture(CallbackInfo ci) {
		TransferProbe.seen("clearColorTexture", vulture$open());
	}

	@Inject(method = "clearDepthTexture(Lcom/mojang/renderpearl/api/textures/GpuTexture;D)V", at = @At("HEAD"))
	private void vulture$clearDepthTexture(CallbackInfo ci) {
		TransferProbe.seen("clearDepthTexture", vulture$open());
	}

	@Inject(method = "clearColorAndDepthTextures(Lcom/mojang/renderpearl/api/textures/GpuTexture;"
			+ "Lorg/joml/Vector4fc;Lcom/mojang/renderpearl/api/textures/GpuTexture;D)V", at = @At("HEAD"))
	private void vulture$clearColorAndDepthTextures(CallbackInfo ci) {
		TransferProbe.seen("clearColorAndDepthTextures", vulture$open());
	}

	@Inject(method = "clearColorAndDepthTextures(Lcom/mojang/renderpearl/api/textures/GpuTexture;"
			+ "Lorg/joml/Vector4fc;Lcom/mojang/renderpearl/api/textures/GpuTexture;DIIIII)V", at = @At("HEAD"))
	private void vulture$clearColorAndDepthRegion(CallbackInfo ci) {
		TransferProbe.seen("clearColorAndDepthTextures (region)", vulture$open());
	}

	@Inject(method = "createRenderPass(Lcom/mojang/renderpearl/api/commands/RenderPassDescriptor;)"
			+ "Lcom/mojang/renderpearl/backend/api/RenderPassBackend;", at = @At("HEAD"))
	private void vulture$createRenderPass(CallbackInfoReturnable<?> cir) {
		TransferProbe.seen("createRenderPass", vulture$open());
	}
}
