package com.tungsten.touchinjector.transform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.tungsten.touchinjector.util.Logging.Level.INFO;
import static com.tungsten.touchinjector.util.Logging.Level.WARNING;
import static com.tungsten.touchinjector.util.Logging.log;

public class DumpClassListener implements ClassLoadingListener {

	private Path outputPath;

	public DumpClassListener(Path outputPath) {
		this.outputPath = outputPath;
	}

	@Override
	public void onClassLoading(ClassLoader classLoader, String className, byte[] bytecode, List<TransformUnit> appliedTransformers) {
		if (!appliedTransformers.isEmpty()) {
			Path dumpFile = outputPath.resolve(className + "_dump.class");
			try {
				Files.write(dumpFile, bytecode, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
				log(INFO,"Transformed class is dumped to [" + dumpFile + "]");
			} catch (IOException e) {
				log(WARNING, "Failed to dump class [" + className + "]", e);
			}
		}
	}
}