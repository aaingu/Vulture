package dev.vulture.fabric;

import dev.vulture.Vulture;

import net.fabricmc.api.ClientModInitializer;

/**
 * Where Fabric hands this mod the game.
 * <p>
 * Only the platform is set here. Fabric calls a client entry point from near the top of the
 * {@code Minecraft} constructor, long before the graphics device is up and before the options are
 * read, so nothing that looks at the game belongs here; what does is in
 * {@code dev.vulture.fabric.mixin.ClientSetupMixin}, once the boot's resource reload is behind.
 */
public final class VultureFabric implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Vulture.initClient(new FabricPlatform());
		FabricKey.register();
	}
}
