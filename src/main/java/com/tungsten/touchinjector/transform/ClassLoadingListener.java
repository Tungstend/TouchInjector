package com.tungsten.touchinjector.transform;

import java.util.List;

public interface ClassLoadingListener {

	void onClassLoading(ClassLoader classLoader, String className, byte[] bytecode, List<TransformUnit> appliedTransformers);

}