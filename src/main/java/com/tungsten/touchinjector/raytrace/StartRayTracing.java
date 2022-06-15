package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.TouchInjector;
import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class StartRayTracing {

    private static String version;

    public static void init() {
        MainArgumentsTransformer.getVersionSeriesListeners().add(version -> {
            if (version.startsWith("1.12")) {
                StartRayTracing.version = "1.12";
            }
            else if (version.startsWith("1.13")) {
                StartRayTracing.version = "1.13";
            }
            else if (version.startsWith("1.14")) {
                StartRayTracing.version = "1.14";
            }
            else if (version.startsWith("1.15")) {
                StartRayTracing.version = "1.15";
            }
            else if (version.startsWith("1.16")) {
                StartRayTracing.version = "1.16";
            }
            else if (version.startsWith("1.17")) {
                StartRayTracing.version = "1.17";
            }
            else if (version.startsWith("1.18")) {
                StartRayTracing.version = "1.18";
            }
            else if (version.startsWith("1.19")) {
                StartRayTracing.version = "1.19";
            }
            else {
                StartRayTracing.version = "unknown";
                log(WARNING, "TouchInjector does not support this version !");
            }
            log(INFO, "version = " + StartRayTracing.version);
            start();
        });
    }

    private static void start() {
        if (version != null && !version.equals("unknown")) {
            switch (TouchInjector.versionType) {
                case TouchInjector.VERSION_TYPE_VANILLA:
                    initializeVanillaInjector();
                    break;
                case TouchInjector.VERSION_TYPE_FORGE:
                    initializeForgeInjector();
                    break;
                case TouchInjector.VERSION_TYPE_OPTIFINE:
                    //TouchInjector.printAllLoadedClass(Launch.classLoader);
                    break;
            }
        }
    }

    private static void initializeVanillaInjector() {
        try {
            Class<?> clazz = Class.forName("VanillaRayTracing");
            Method method= clazz.getMethod("init", String.class);
            method.invoke(null, version);
        } catch (ClassNotFoundException e) {
            log(ERROR, "Failed to load VanillaRayTracing.class !");
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            log(ERROR, "Failed to find method VanillaRayTracing.init() !");
            throw new RuntimeException(e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log(ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void initializeForgeInjector() {
        ForgeRayTracing.init(version);
    }

}
