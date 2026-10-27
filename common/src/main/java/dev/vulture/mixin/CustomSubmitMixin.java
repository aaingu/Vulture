package dev.vulture.mixin;

import dev.vulture.render.BlockEntityGeometry;
import dev.vulture.render.BlockEntityOrigin;
import dev.vulture.render.EntityIdentifiers;
import dev.vulture.render.SubmittedIdentifiers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The same for geometry a block entity draws by hand rather than through a model.
 * <p>
 * Three block entity renderers of the game take this road. Most of what they draw blends, so it is
 * no row of the entity table and never reaches the door; the mark is taken all the same, because
 * whether a given renderer blends is the game's to change and this file would not notice.
 */
@Mixin(CustomFeatureRenderer.Submit.class)
public abstract class CustomSubmitMixin implements BlockEntityOrigin, SubmittedIdentifiers {

	@Unique
	private boolean vulture$blockEntity;

	@Unique
	private long vulture$identifiers;

	@Inject(method = "<init>", at = @At("RETURN"), require = 1)
	private void vulture$capture(PoseStack.Pose pose, RenderType renderType,
			SubmitNodeCollector.CustomGeometryRenderer renderer, CallbackInfo callback) {
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
