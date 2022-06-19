package com.tungsten.touchinjector.raytrace.forge;

import cpw.mods.modlauncher.Launcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.ERROR;
import static com.tungsten.touchinjector.util.Logging.log;

public class RayTracingD {

    public static String getRaytraceResultType() {
        try {
            Field classField = Launcher.class.getDeclaredField("classLoader");
            classField.setAccessible(true);
            ClassLoader classLoader = (ClassLoader) classField.get(Launcher.INSTANCE);
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft", true, classLoader);
            Method method = minecraftClass.getDeclaredMethod("m_91087_");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("f_91077_");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("m_6662_");
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
