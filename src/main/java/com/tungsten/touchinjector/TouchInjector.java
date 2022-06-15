package com.tungsten.touchinjector;

import com.tungsten.touchinjector.raytrace.StartRayTracing;
import com.tungsten.touchinjector.transform.ClassTransformer;
import com.tungsten.touchinjector.transform.DumpClassListener;
import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;

import java.lang.instrument.Instrumentation;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;
import static java.util.Objects.requireNonNull;

public class TouchInjector {

    private TouchInjector() {}

    private static boolean booted = false;
    private static Instrumentation instrumentation;
    private static boolean retransformSupported;
    private static ClassTransformer classTransformer;

    public static String versionType;
    public static final String VERSION_TYPE_VANILLA = "vanilla";
    public static final String VERSION_TYPE_FORGE = "forge";
    public static final String VERSION_TYPE_OPTIFINE = "optifine";

    public static synchronized void bootstrap(Instrumentation instrumentation, String arg) throws InitializationException {
        if (booted) {
            log(INFO, "Already started, skipping");
            return;
        }
        booted = true;
        TouchInjector.instrumentation = requireNonNull(instrumentation);
        Config.init();

        retransformSupported = instrumentation.isRetransformClassesSupported();
        if (!retransformSupported) {
            log(WARNING, "Retransform is not supported");
        }

        log(INFO, "Version: " + TouchInjector.class.getPackage().getImplementationVersion());

        classTransformer = createTransformer();
        instrumentation.addTransformer(classTransformer, retransformSupported);

        versionType = arg;
        StartRayTracing.init();
    }

    public static void printAllLoadedClass(ClassLoader classLoader) {
        Class<?>[] classes = instrumentation.getInitiatedClasses(classLoader);
        for (Class<?> c : classes) {
            log(INFO, c.getName());
        }
        if (classes.length == 0) {
            log(WARNING, "No class loaded !");
        }
    }

    public static void reTransformAllClasses() {
        if (!retransformSupported) {
            return;
        }
        log(INFO, "Attempt to retransform all classes");
        long t0 = System.currentTimeMillis();

        Class<?>[] classes = Stream.of(instrumentation.getAllLoadedClasses())
                .filter(TouchInjector::canRetransformClass)
                .toArray(Class[]::new);
        if (classes.length > 0) {
            try {
                instrumentation.retransformClasses(classes);
            } catch (Throwable e) {
                log(WARNING, "Failed to retransform", e);
                return;
            }
        }
        else {
            log(WARNING, "No class to retransform");
        }

        long t1 = System.currentTimeMillis();
        log(INFO, "Retransformed " + classes.length + " classes in " + (t1 - t0) + "ms");
    }

    private static boolean canRetransformClass(Class<?> clazz) {
        return instrumentation.isModifiableClass(clazz);
    }

    private static ClassTransformer createTransformer() {

        ClassTransformer transformer = new ClassTransformer();

        if (Config.dumpClass) {
            transformer.listeners.add(new DumpClassListener(Paths.get("").toAbsolutePath()));
        }
        transformer.units.add(new MainArgumentsTransformer());

        return transformer;
    }

    public static ClassTransformer getClassTransformer() {
        return classTransformer;
    }
}
