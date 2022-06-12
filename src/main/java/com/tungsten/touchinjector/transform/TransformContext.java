package com.tungsten.touchinjector.transform;

import java.util.List;
import org.objectweb.asm.Handle;

public interface TransformContext {

	void markModified();

	void requireMinimumClassVersion(int version);

	void upgradeClassVersion(int version);

	Handle acquireCallbackMetafactory();

	List<String> getStringConstants();
}