package com.tungsten.touchinjector.raytrace.forge;

import net.minecraft.launchwrapper.Launch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.ERROR;
import static com.tungsten.touchinjector.util.Logging.log;

public class RayTracingA {

    public static String getRaytraceResultType_a() {
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft", true, Launch.classLoader);
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
