package com.tungsten.touchinjector.transform;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public final class CallbackSupport {
	private CallbackSupport() {
	}

	static final String METAFACTORY_NAME = "__touchinjector_metafactory";
	static final String METAFACTORY_SIGNATURE = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;)Ljava/lang/invoke/CallSite;";

	private static Method findCallbackMethod(Class<?> owner, String methodName) {
		for (Method method : owner.getDeclaredMethods()) {
			int modifiers = method.getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) &&
					methodName.equals(method.getName()) &&
					method.getAnnotation(CallbackMethod.class) != null) {
				return method;
			}
		}
		throw new IllegalArgumentException("No such method: " + methodName);
	}

	public static void invoke(TransformContext ctx, MethodVisitor mv, Class<?> owner, String methodName) {
		ctx.requireMinimumClassVersion(50);
		ctx.upgradeClassVersion(51);

		String descriptor = Type.getMethodDescriptor(findCallbackMethod(owner, methodName));
		mv.visitInvokeDynamicInsn(methodName, descriptor, ctx.acquireCallbackMetafactory(), owner.getName());
	}
}