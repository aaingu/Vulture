package dev.vulture.neoforge;

import dev.vulture.platform.VulturePlatform;
import dev.vulture.Vulture;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

final class NeoForgePlatform implements VulturePlatform {

	@Override
	public String loaderName() {
		return "NeoForge";
	}

	@Override
	public String loaderVersion() {
		return versionOf("neoforge");
	}

	@Override
	public String modVersion() {
		return versionOf(Vulture.MOD_ID);
	}

	@Override
	public String minecraftVersion() {
		return versionOf("minecraft");
	}

	@Override
	public Path gameDirectory() {
		return FMLPaths.GAMEDIR.get();
	}

	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	private static String versionOf(String modId) {
		return ModList.get()
				.getModContainerById(modId)
				.map(container -> container.getModInfo().getVersion().toString())
				.orElse("unknown");
	}
}
