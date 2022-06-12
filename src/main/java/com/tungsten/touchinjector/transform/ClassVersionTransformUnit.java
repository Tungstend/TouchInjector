package com.tungsten.touchinjector.transform;

import static com.tungsten.touchinjector.util.Logging.Level.DEBUG;
import static com.tungsten.touchinjector.util.Logging.log;
import static org.objectweb.asm.Opcodes.ASM9;
import java.util.Optional;
import org.objectweb.asm.ClassVisitor;

class ClassVersionTransformUnit implements TransformUnit {

	private final int minVersion;
	private final int upgradedVersion;

	public ClassVersionTransformUnit(int minVersion, int upgradedVersion) {
		this.minVersion = minVersion;
		this.upgradedVersion = upgradedVersion;
	}

	@Override
	public Optional<ClassVisitor> transform(ClassLoader classLoader, String className, ClassVisitor writer, TransformContext context) {
		return Optional.of(new ClassVisitor(ASM9, writer) {
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				int major = version & 0xffff;

				if (minVersion != -1 && major < minVersion) {
					throw new ClassVersionException("class version (" + major + ") is lower than required(" + minVersion + ")");
				}

				if (upgradedVersion != -1 && major < upgradedVersion) {
					log(DEBUG,"Upgrading class version from " + major + " to " + upgradedVersion);
					version = upgradedVersion;
					context.markModified();
				}
				super.visit(version, access, name, signature, superName, interfaces);
			}
		});
	}

	@Override
	public String toString() {
		return "Class File Version Transformer";
	}
}