package dev.vulture.mixin;

import dev.vulture.render.EntityIdentifiers;
import dev.vulture.render.SubmittedIdentifiers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * The same for an item's own quads, which are the geometry the third identifier is about.
 * <p>
 * <strong>A submission of its own and not a model one</strong>, which is what makes this file
 * necessary rather than tidy: an item is submitted through {@code ItemFeatureRenderer}, whose
 * {@code Submit} is a record of its own and whose group is built by a method of its own. A window
 * opened round the model submissions alone would leave every item in a frame, on the ground and in a
 * hand carrying nought for the very name that says which item it is. Iris marks the same submission
 * for the same reason ({@code mixin/entity_render_context/MixinItemSubmit.java}).
 */
@Mixin(ItemFeatureRenderer.Submit.class)
public abstract class ItemSubmitMixin implements SubmittedIdentifiers {

	@Unique
	private long vulture$identifiers;

	@Inject(method = "<init>", at = @At("RETURN"), require = 1)
	private void vulture$capture(PoseStack.Pose pose, ItemDisplayContext displayContext,
			int lightCoords, int overlayCoords, int outlineColor, int[] tintLayers,
			List<BakedQuad> quads, ItemStackRenderState.FoilType foilType, CallbackInfo callback) {
		this.vulture$identifiers = EntityIdentifiers.packed();
	}

	@Override
	public long vulture$identifiers() {
		return this.vulture$identifiers;
	}
}
