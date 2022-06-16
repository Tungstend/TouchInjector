package com.tungsten.touchinjector.launch;

import com.tungsten.touchinjector.raytrace.ForgeRayTracing;
import cpw.mods.bootstraplauncher.BootstrapLauncher;

import static com.tungsten.touchinjector.util.Logging.Level.INFO;
import static com.tungsten.touchinjector.util.Logging.Level.WARNING;
import static com.tungsten.touchinjector.util.Logging.log;

public class TouchBootstrapLauncher {

    public static void main(String[] args) {
        log(INFO, "Launch Forge from touchinjector !");

        startRayTracing(args);
        BootstrapLauncher.main(args);
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
                    if (arg.startsWith("1.17")) {
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
            ForgeRayTracing.init(version);
        }
    }

}
