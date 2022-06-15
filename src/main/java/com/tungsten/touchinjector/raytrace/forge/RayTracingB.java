package com.tungsten.touchinjector.raytrace.forge;

import cpw.mods.modlauncher.Launcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.ERROR;
import static com.tungsten.touchinjector.util.Logging.log;

public class RayTracingB {

    public static String getRaytraceResultType_b() {
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
            Field typeField = target.getClass().getDeclaredField("field_72313_a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

}
