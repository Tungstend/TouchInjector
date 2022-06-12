import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;

import static com.tungsten.touchinjector.util.Logging.Level.INFO;
import static com.tungsten.touchinjector.util.Logging.log;

public class VanillaRayTracing {

    public static void init(String args) {
        MainArgumentsTransformer.getVersionSeriesListeners().add(version -> {
            if ("1.12".equals(version)) {
                log(INFO, "Enable touchinjector for 1.12");
                rayTrace();
            }
        });
    }

    private static void rayTrace() {
        Thread thread = new Thread("Raytrace thread") {
            public void run() {
                while (true) {
                    bib minecraft = bib.z();
                    if (minecraft != null) {
                        bhc target = minecraft.s;
                        if (target != null) {
                            bhc.a type = target.a;
                            sendType(type);
                        }
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private static void sendType(bhc.a type) {
        log(INFO, type.name());
    }

}
