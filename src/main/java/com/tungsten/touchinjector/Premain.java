package com.tungsten.touchinjector;

import java.lang.instrument.Instrumentation;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class Premain {

    private Premain() {}

    public static void premain(String arg, Instrumentation instrumentation) {
        try {
            initInjector(arg, instrumentation, false);
        } catch (InitializationException e) {
            log(DEBUG, "A known exception has occurred", e);
            System.exit(1);
        } catch (Throwable e) {
            log(ERROR, "An exception has occurred, exiting", e);
            System.exit(1);
        }
    }

    public static void agentmain(String arg, Instrumentation instrumentation) {
        try {
            log(INFO, "Launched from agentmain");
            initInjector(arg, instrumentation, true);
        } catch (InitializationException e) {
            log(DEBUG, "A known exception has occurred", e);
        } catch (Throwable e) {
            log(ERROR, "An exception has occurred", e);
        }
    }

    private static void initInjector(String arg, Instrumentation instrumentation, boolean reTransform) {
        TouchInjector.bootstrap(instrumentation, arg);

        if (reTransform) {
            TouchInjector.reTransformAllClasses();
        }
    }

}
