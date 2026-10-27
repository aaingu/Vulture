package dev.vulture.mixin;

import dev.vulture.render.BlockEntityGeometry;
import dev.vulture.render.BlockEntityOrigin;
import dev.vulture.render.EntityIdentifiers;
import dev.vulture.render.SubmittedIdentifiers;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * The same for hand written geometry, whose renderer has no per submission method to hang this on.
 * <p>
 * So the mark goes up on the call that picks the draw and comes back down when the group is
 * finished, rather than around each turn of the loop. Nothing between the two reads it: the only
 * reader is the grouping itself, inside that very call.
 * <p>
 * The invoked method is named without its owner on purpose. {@code getVertexBuilder} is declared on
 * the superclass and called on {@code this}, and which of the two names the compiler writes into the
 * call site is not something this file should depend on.
 */
@Mixin(CustomFeatureRenderer.class)
public abstract class CustomFeatureRendererMixin {

	@Inject(method = "buildGroup", require = 1,
			at = @At(value = "INVOKE",
					target = "getVertexBuilder(Lnet/minecraft/client/renderer/rendertype/RenderType;)"
							+ "Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
	private void vulture$begin(FeatureFrameContext context, List<CustomFeatureRenderer.Submit> submits,
			CallbackInfo callback, @Local CustomFeatureRenderer.Submit submit) {
		BlockEntityGeometry.building(((BlockEntityOrigin) (Object) submit).vulture$fromBlockEntity());
		EntityIdentifiers.restore(((SubmittedIdentifiers) (Object) submit).vulture$identifiers());
	}

	@Inject(method = "buildGroup", at = @At("RETURN"), require = 1)
	private void vulture$end(FeatureFrameContext context, List<CustomFeatureRenderer.Submit> submits,
			CallbackInfo callback) {
		BlockEntityGeometry.building(false);
		EntityIdentifiers.clear();
	}
}
