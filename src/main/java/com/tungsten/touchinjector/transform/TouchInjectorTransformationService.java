package com.tungsten.touchinjector.transform;

import com.tungsten.touchinjector.raytrace.ForgeRayTracing;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.tungsten.touchinjector.util.Logging.Level.INFO;
import static com.tungsten.touchinjector.util.Logging.log;

public class TouchInjectorTransformationService implements ITransformationService {

    @Override
    public String name() {
        return "Touch-Injector";
    }

    @Override
    public void initialize(IEnvironment iEnvironment) {

    }

    @Override
    public void onLoad(IEnvironment iEnvironment, Set<String> set) throws IncompatibleEnvironmentException {
        log(INFO, "Start Touch-Injector as a forge plugin.");
        String version = System.getProperty("touchinjector.version");
        if (version != null && !version.isEmpty() && !version.equals("unknown")) {
            ForgeRayTracing.init(version);
        }
    }

    @Override
    public List<ITransformer> transformers() {
        return new ArrayList<>();
    }
}