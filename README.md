# Vulture

Vulture runs existing OptiFine/Iris-format shader packs on Minecraft's native Vulkan renderer. It is a client-side mod for Fabric and NeoForge, intended for existing modpacks that can run Minecraft with Vulkan enabled. Shader packs are installed separately; Vulture does not bundle them.

The renderer in Minecraft 26.2 and 26.3 is experimental. On macOS, Minecraft routes Vulkan through MoltenVK to Metal. Vulture includes Mac performance presets for Apple Silicon and Windows, alongside the full-quality controls.

## Platform targets

| Platform | Target | Verification |
| --- | --- | --- |
| macOS | Apple Silicon M1 to M5, macOS 15 Sequoia through macOS 27 Golden Gate, where Apple supports that OS on the device | M4 / macOS 27.0.1 builds and Complementary archive smoke test passed; GPU rendering was not verified |
| Windows | Windows 10/11 with a Vulkan-capable GPU and driver | GitHub Actions builds passed for 26.2 and 26.3; GPU rendering was not verified |

The source does not contain chip-specific shader code; it uses Minecraft's Vulkan renderer and MoltenVK on macOS. M1, M2, M3, M4, and M5 Macs and every OS/device pairing are not individually available for local runtime testing. Apple's supported-device list determines which macOS releases a given Mac can install.

## Requirements

- Minecraft Java Edition 26.2 or 26.3 (build one game version at a time).
- Fabric or NeoForge, plus the matching Sodium version listed in [INSTALL.md](INSTALL.md).
- Minecraft's Vulkan graphics backend enabled. The game's OpenGL backend does not draw shader packs through Vulture.
- An OptiFine/Iris-format shader pack such as [Complementary Reimagined](https://github.com/ComplementaryDevelopment/ComplementaryReimagined), installed in the usual `shaderpacks/` folder.

Vulture carries the upstream shader translation and rendering path, including existing compatibility work for shader packs. Pack compatibility remains pack- and game-version-specific; use the project's [compatibility notes](docs/compatibility.md) and report reproducible problems with the pack name, version, loader, Minecraft version, OS, and GPU.

## Build

The build uses Java 25. From this repository root:

```sh
./gradlew build
./gradlew build -Pminecraft=26.3
```

On Windows, run `gradlew.bat build` and `gradlew.bat build -Pminecraft=26.3`. The loader jars are written under `fabric/build/` and `neoforge/build/` for the selected Minecraft version. The build includes the automated unit tests. GitHub Actions builds both supported Minecraft versions on macOS and Windows.

For installation and Vulkan setup, see [INSTALL.md](INSTALL.md). For the performance presets, see [docs/performance-presets.md](docs/performance-presets.md).

## Origin and license

Vulture is a rebranded derivative of [Vitrail Shaders](https://github.com/avpbynf/Vitrail-Shaders), with its source pinned and local changes described in [UPSTREAM.md](UPSTREAM.md). It is not an official Vitrail release. The inherited LGPL-3.0-only license and third-party notices are retained in [LICENSE](LICENSE), [GPL-3.0.txt](GPL-3.0.txt), and [NOTICE](NOTICE).
