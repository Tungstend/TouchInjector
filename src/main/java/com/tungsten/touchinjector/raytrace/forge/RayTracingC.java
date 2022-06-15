package com.tungsten.touchinjector.raytrace.forge;

import cpw.mods.modlauncher.Launcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.ERROR;
import static com.tungsten.touchinjector.util.Logging.log;

public class RayTracingC {

    public static String getRaytraceResultType_c() {
        try {
            Field classField = Launcher.class.getDeclaredField("classLoader");
            classField.setAccessible(true);
            ClassLoader classLoader = (ClassLoader) classField.get(Launcher.INSTANCE);
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft", true, classLoader);
            Method method = minecraftClass.getDeclaredMethod("func_71410_x");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("field_71476_x");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("func_216346_c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

}
