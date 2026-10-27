# Performance presets

Vulture draws OptiFine/Iris-format shader packs through Minecraft's Vulkan backend. On macOS,
Minecraft provides Vulkan through MoltenVK, which maps Vulkan work to Metal. Vulkan is experimental
in Minecraft 26.2 and later, and the available features and performance depend on the Mac, macOS
version, driver, and shader pack.

Open **Options → Video Settings → Vulture → Settings** while the game is using Vulkan. The new
**Performance Preset** selector applies a starting point to four existing controls. It does not
change a pack automatically at launch. The selected values are saved in the instance, and each
slider remains editable afterwards.

| Preset | Render scale | Shadow map scale | Shadow distance | Shadow reuse |
| --- | ---: | ---: | ---: | ---: |
| Full Quality | 100% | 100% | 32 chunks | Off |
| macOS Balanced | 85% | 80% | 24 chunks | 1 frame |
| macOS Battery Saver | 70% | 60% | 16 chunks | 2 frames |
| Windows Balanced | 90% | 85% | 24 chunks | 1 frame |
| Custom | Keeps current values | Keeps current values | Keeps current values | Keeps current values |

The macOS profiles reduce the number of world and shadow-map pixels and reuse a shadow map for a
short interval. The battery profile trades more image detail and shadow reach for lower work. Shadow
reuse can leave ground shadows briefly out of date after blocks change; moving casters are rendered
again each frame. The presets do not change the shader pack's own quality settings or enable temporal
accumulation.

The scales are per edge: 85% render scale draws about 72% as many world pixels, while 80% shadow-map
scale uses 64% as many shadow texels. The battery preset's 70% and 60% settings draw about 49% as
many world pixels and 36% as many shadow texels. These are pixel-count comparisons, not promised
frame-rate gains. If a pack fixes its own shadow distance, the preset's distance value remains saved
but the pack's value takes effect.

These are starting points, not guaranteed frame-rate targets. If a pack remains CPU or submission
bound, lowering render scale may not help much. Try one control at a time after applying a preset.
Use the engine's pass census/timing tools described in `docs/developing.md` to identify whether the
pack is spending time on shadow geometry, full-screen passes, or driver submission.

The Windows preset uses the same game-native Vulkan renderer and the same pack translation path.
It keeps a higher render scale by default. On either platform, the mod requires a Minecraft build
supported by this source tree, Fabric or NeoForge, and the compatible Sodium version listed in
`INSTALL.md`. A device that cannot start Minecraft's Vulkan backend will not draw packs through this
mod; use the game's OpenGL backend with Iris instead.
