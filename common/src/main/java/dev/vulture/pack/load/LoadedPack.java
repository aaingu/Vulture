package dev.vulture.pack.load;

import dev.vulture.pack.option.OptionIndex;
import dev.vulture.pack.program.ProgramResolver;
import dev.vulture.pack.program.ProgramSet;
import dev.vulture.pack.source.DimensionSet;
import dev.vulture.pack.source.ExpansionStats;
import dev.vulture.pack.source.ShaderProperties;

import java.util.List;
import java.util.Set;

/**
 * What is known about a pack once it has been read.
 * <p>
 * Everything here is immutable and holds no {@code Path}. That is not tidiness: the pack may
 * have been a zip, and the archive is closed as soon as reading is done, which would turn any
 * surviving path into a {@code ClosedFileSystemException} on first use, far from the cause.
 */
public record LoadedPack(String packName, boolean fromZip, DimensionSet dimensions,
		ShaderProperties properties, OptionIndex options, ProgramSet programs,
		ProgramResolver resolved, PackStats stats, ExpansionStats expansion, int expandedUnits,
		List<String> looseDirectives, Set<String> disabledPrograms, int caseInsensitiveHits,
		long loadMillis) {
}
