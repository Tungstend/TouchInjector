package com.tungsten.touchinjector.transform;

import java.util.Optional;
import org.objectweb.asm.ClassVisitor;

public interface TransformUnit {

	Optional<ClassVisitor> transform(ClassLoader classLoader, String className, ClassVisitor writer, TransformContext context);

}