# Installing Vulture

Vulture is a client-only mod. It runs OptiFine/Iris-format shader packs through Minecraft's native Vulkan renderer. It does not run through OpenGL.

## Requirements

| Component | Version |
| --- | --- |
| Minecraft | 26.2 or 26.3 (use the matching jar) |
| Loader | Fabric or NeoForge for the selected Minecraft version |
| Sodium | 0.9.x build matching both the game and loader |
| Fabric API | Required on Fabric; use a matching game build |
| Java | 25 to build; the launcher supplies the game runtime |

See the release page for published jars. Until a Vulture release is available, build the project from this repository using [the build instructions](README.md#build). The Fabric and NeoForge jars are written under their respective `build/` directories.

## Install in a modpack

Add the jar for the correct Minecraft version and loader to the instance's `mods/` folder alongside Sodium. On Fabric, install Fabric API as well. Do not add both loader jars to one instance.

Add an OptiFine/Iris-format pack such as [Complementary Reimagined](https://github.com/ComplementaryDevelopment/ComplementaryReimagined) to the instance's existing `shaderpacks/` folder. Vulture does not include or redistribute shader packs. Select the pack in the Vulture page under Options → Video Settings.

## Enable Vulkan

With the game closed, select the Vulkan graphics backend in Video Settings and restart the game. Minecraft 26.2 and 26.3 mark Vulkan as experimental. If Minecraft cannot start Vulkan on the device or its driver, Vulture cannot draw the shader pack. OpenGL and Iris remain available as another renderer choice.

On macOS, Minecraft uses MoltenVK to map Vulkan commands to Metal. Vulture includes macOS Balanced and Battery Saver presets; they are starting points and do not guarantee a specific frame rate. Details are in [Performance presets](docs/performance-presets.md).

## Compatibility notes

A modpack must be able to start Minecraft's native Vulkan backend. Mods that replace the renderer or rely on unsupported OpenGL-only hooks may conflict. Shader pack compatibility depends on both Minecraft and the pack; the [compatibility guide](docs/compatibility.md) describes known limitations. Include the pack and game versions, loader, OS, GPU, and relevant log excerpt when reporting a problem.
