package dev.vulture.render;

import dev.vulture.settings.PackFile;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Explicit starting points for the shader engine's machine-wide performance controls.
 *
 * <p>The Apple profiles trade some image detail and shadow reach for lower per-frame GPU work.
 * They are opt-in: shader packs and Apple GPUs vary too much for a safe one-size-fits-all automatic
 * setting. The Windows preset keeps a higher render scale while reducing the expensive shadow
 * walk. Selecting a preset writes the same settings used by the sliders, so every value remains
 * editable afterwards.</p>
 */
public enum PerformancePreset {
	QUALITY(PackFile.MAX_RENDER_SCALE, PackFile.MAX_SHADOW_MAP_SCALE,
			PackFile.MAX_SHADOW_DISTANCE, ShadowAmortisation.MIN_FRAMES),
	MACOS_BALANCED(85, 80, 24, 1),
	MACOS_BATTERY(70, 60, 16, 2),
	WINDOWS_BALANCED(90, 85, 24, 1),
	CUSTOM(-1, -1, -1, -1);

	private final int renderScale;
	private final int shadowMapScale;
	private final int shadowDistance;
	private final int shadowReuse;

	PerformancePreset(int renderScale, int shadowMapScale, int shadowDistance, int shadowReuse) {
		this.renderScale = renderScale;
		this.shadowMapScale = shadowMapScale;
		this.shadowDistance = shadowDistance;
		this.shadowReuse = shadowReuse;
	}

	/** Applies the preset through the existing settings paths, preserving pack names and storage. */
	public void apply(Path gameDirectory) {
		if (this == CUSTOM) {
			return;
		}

		PackChoice.shadowDistance(gameDirectory, this.shadowDistance);
		PackChoice.renderScale(gameDirectory, this.renderScale);
		// This setting reloads the pack because it changes translated shadow-map declarations.
		PackChoice.shadowMapScale(gameDirectory, this.shadowMapScale);
		ShadowAmortisation.setFrames(this.shadowReuse);
	}

	/** Returns the named preset matching the live values, or {@link #CUSTOM}. */
	public static PerformancePreset current() {
		int renderScale = PackChoice.renderScale();
		int shadowMapScale = PackChoice.shadowMapScale();
		int shadowDistance = PackChoice.shadowDistance();
		int shadowReuse = ShadowAmortisation.frames();

		return Arrays.stream(values())
				.filter(preset -> preset != CUSTOM
						&& preset.renderScale == renderScale
						&& preset.shadowMapScale == shadowMapScale
						&& preset.shadowDistance == shadowDistance
						&& preset.shadowReuse == shadowReuse)
				.findFirst()
				.orElse(CUSTOM);
	}
}
