# Upstream source and local changes

Vulture is based on the public [Vitrail Shaders](https://github.com/avpbynf/Vitrail-Shaders) source at commit [`7a3cdcbe1dfd7e04fb2b3021ee8352d341e7647e`](https://github.com/avpbynf/Vitrail-Shaders/commit/7a3cdcbe1dfd7e04fb2b3021ee8352d341e7647e). The upstream `LICENSE`, `GPL-3.0.txt`, and `NOTICE` are kept intact. This project is an independent, unofficial derivative under the inherited LGPL-3.0-only terms.

## Vulture changes

- Renames the mod identity, metadata, resources, loader mixins, Java namespace, and build artifacts to Vulture.
- Keeps the upstream OptiFine/Iris-format shader-pack translation and Vulkan rendering path.
- Adds opt-in macOS Balanced, macOS Battery Saver, Windows Balanced, Full Quality, and Custom performance presets.
- Documents Apple Silicon and Windows setup, compatibility targets, build steps, and platform verification limits.
- Adds no shader pack binaries; packs such as Complementary are installed by the player.

The source tree retains upstream historical changelog and artwork material, together with their attribution. It does not imply that Vulture is published or supported by the upstream project.
