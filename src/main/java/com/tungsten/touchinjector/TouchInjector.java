package com.tungsten.touchinjector;

import com.tungsten.touchinjector.transform.ClassTransformer;
import com.tungsten.touchinjector.transform.DumpClassListener;
import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

        try {
            Class<?> clazz = Class.forName("VanillaRayTracing");
            Method method= clazz.getMethod("init", String.class);
            method.invoke(null, "");
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

    public static void retransformClasses(String... classNames) {
        if (!retransformSupported) {
            return;
        }
        Set<String> classNamesSet = new HashSet<>(Arrays.asList(classNames));
        Class<?>[] classes = Stream.of(instrumentation.getAllLoadedClasses())
                .filter(clazz -> classNamesSet.contains(clazz.getName()))
                .filter(TouchInjector::canRetransformClass)
                .toArray(Class[]::new);
        if (classes.length > 0) {
            log(INFO, "Attempt to retransform classes: " + Arrays.toString(classes));
            try {
                instrumentation.retransformClasses(classes);
            } catch (Throwable e) {
                log(WARNING, "Failed to retransform", e);
            }
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
