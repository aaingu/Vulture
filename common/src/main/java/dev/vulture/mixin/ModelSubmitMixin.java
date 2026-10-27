package dev.vulture.mixin;

import dev.vulture.render.BlockEntityGeometry;
import dev.vulture.render.BlockEntityOrigin;
import dev.vulture.render.EntityIdentifiers;
import dev.vulture.render.SubmittedIdentifiers;

import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Remembers, on a model submission, whether it was handed in by a block entity renderer.
 * <p>
 * <strong>At construction and not at storage</strong>, which is what makes one hook enough. A model
 * submission is built inside the submit call in three places at once, the ordinary one, the outline
 * and the crumbling overlay, and all three are made while the dispatcher's mark is up; hooking the
 * lists they are filed into would be three hooks that have to stay in step with a method this engine
 * does not own.
 * <p>
 * This is the submission every block entity in the game produces: {@code submitModelPart} is a
 * default method of the collector that delegates to {@code submitModel}, so a chest, a sign's board,
 * a banner and a skull all arrive here, and there is no second kind to mark.
 */
@Mixin(ModelFeatureRenderer.Submit.class)
public abstract class ModelSubmitMixin implements BlockEntityOrigin, SubmittedIdentifiers {

	@Unique
	private boolean vulture$blockEntity;

	@Unique
	private long vulture$identifiers;

	@Inject(method = "<init>", at = @At("RETURN"), require = 1)
	private void vulture$capture(CallbackInfo callback) {
		this.vulture$blockEntity = BlockEntityGeometry.submitting();
		this.vulture$identifiers = EntityIdentifiers.packed();
	}

	@Override
	public boolean vulture$fromBlockEntity() {
		return this.vulture$blockEntity;
	}

	@Override
	public long vulture$identifiers() {
		return this.vulture$identifiers;
	}
}
