package dev.vulture.pack.load;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Optional check against a player-supplied shader pack. It verifies ZIP reading, program discovery,
 * and include expansion; it does not start Minecraft or compile shaders on a GPU.
 */
class ShaderPackSmokeTest {

	@Test
	void opensPackAndExpandsItsProgramsWhenProvided() throws IOException {
		String configuredPath = System.getProperty("vulture.test.shaderPack", "").trim();
		assumeTrue(!configuredPath.isEmpty(), "Set -Dvulture.test.shaderPack to a shader-pack ZIP");

		Path shaderPack = Path.of(configuredPath);
		assertTrue(Files.isRegularFile(shaderPack), "Shader-pack file does not exist: " + shaderPack);

		LoadedPack loaded = PackLoader.load(shaderPack);
		assertTrue(loaded.fromZip(), "Expected the supplied shader pack to be a ZIP archive");
		assertTrue(loaded.programs().count() > 0, "No shader program entry points were discovered");
		assertTrue(loaded.expandedUnits() > 0, "No shader program includes were expanded");
	}
}
