package com.tungsten.touchinjector.launch;

import com.tungsten.touchinjector.raytrace.FabricRayTracing;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.launch.knot.Knot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class TouchKnotClient {

    public static ClassLoader classLoader;

    public static void main(String[] args) {
        log(INFO, "Launch FabricMC from touchinjector !");

        try {
            Class<?> knotClass = Class.forName("net.fabricmc.loader.impl.launch.knot.Knot", true, Thread.currentThread().getContextClassLoader());
            Method init = knotClass.getDeclaredMethod("init", String[].class);
            init.setAccessible(true);
            Knot knot = new Knot(EnvType.CLIENT);
            Object classLoader = init.invoke(knot, (Object) args);
            Field field = knotClass.getDeclaredField("provider");
            field.setAccessible(true);
            Object provider = field.get(knot);
            GameProvider gameProvider = (GameProvider) provider;
            log(INFO, classLoader.toString());
            TouchKnotClient.classLoader = (ClassLoader) classLoader;
            startRayTracing(args);
            gameProvider.launch(TouchKnotClient.classLoader);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
    }

    private static void startRayTracing(String[] args) {
        boolean hit = false;
        String version = null;
        for (String arg : args) {
            if (hit) {
                if (arg.startsWith("--")) {
                    // arg doesn't seem to be a value
                    // maybe the previous argument is a value, but we wrongly recognized it as an option
                    hit = false;
                }
                else {
                    if (arg.startsWith("1.14")) {
                        version = "1.14";
                    }
                    else if (arg.startsWith("1.15")) {
                        version = "1.15";
                    }
                    else if (arg.startsWith("1.16")) {
                        version = "1.16";
                    }
                    else if (arg.startsWith("1.17")) {
                        version = "1.17";
                    }
                    else if (arg.startsWith("1.18")) {
                        version = "1.18";
                    }
                    else if (arg.startsWith("1.19")) {
                        version = "1.19";
                    }
                    else {
                        version = "unknown";
                        log(WARNING, "TouchInjector does not support this version !");
                    }
                    log(INFO, "version = " + version);
                    break;
                }
            }

            if ("--assetIndex".equals(arg)) {
                hit = true;
            }
        }
        if (version != null && !version.equals("unknown")) {
            FabricRayTracing.init(version);
        }
    }

}
