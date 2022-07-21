package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.TouchInjector;
import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class StartRayTracing {

    private static String version;

    public static void init() {
        MainArgumentsTransformer.getVersionSeriesListeners().add(version -> {
            if (version.startsWith("1.7")) {
                StartRayTracing.version = "1.7";
            }
            else if (version.startsWith("1.8")) {
                StartRayTracing.version = "1.8";
            }
            else if (version.startsWith("1.9")) {
                StartRayTracing.version = "1.9";
            }
            else if (version.startsWith("1.10")) {
                StartRayTracing.version = "1.10";
            }
            else if (version.startsWith("1.11")) {
                StartRayTracing.version = "1.11";
            }
            else if (version.startsWith("1.12")) {
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
                    initializeOptiFineInjector();
                    break;
            }
        }
    }

    private static void initializeVanillaInjector() {
        VanillaRayTracing.init(version);
    }

    private static void initializeForgeInjector() {
        ForgeRayTracing.init(version);
    }

    private static void initializeOptiFineInjector() {
        OptiFineRayTracing.init(version);
    }

}
