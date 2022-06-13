import com.tungsten.touchinjector.transform.support.MainArgumentsTransformer;
import com.tungsten.touchinjector.util.SocketServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class VanillaRayTracing {

    private static String version;

    private static int isVanilla;
    private static int versionSupport;

    public static void init(String args) {
        MainArgumentsTransformer.getVersionSeriesListeners().add(version -> {
            if (version.startsWith("1.12")) {
                VanillaRayTracing.version = "1.12";
            }
            else if (version.startsWith("1.13")) {
                VanillaRayTracing.version = "1.13";
            }
            else if (version.startsWith("1.14")) {
                VanillaRayTracing.version = "1.14";
            }
            else if (version.startsWith("1.15")) {
                VanillaRayTracing.version = "1.15";
            }
            else if (version.startsWith("1.16")) {
                VanillaRayTracing.version = "1.16";
            }
            else if (version.startsWith("1.17")) {
                VanillaRayTracing.version = "1.17";
            }
            else if (version.startsWith("1.18")) {
                VanillaRayTracing.version = "1.18";
            }
            else if (version.startsWith("1.19")) {
                VanillaRayTracing.version = "1.19";
            }
            else {
                VanillaRayTracing.version = "unknown";
                log(WARNING, "TouchInjector does not support this version !");
            }
            versionSupport = 1;
            start();
        });
        MainArgumentsTransformer.getVersionTypeListeners().add(bool -> {
            if (!bool) {
                isVanilla = 1;
                start();
            }
        });
    }

    private static void start() {
        if (versionSupport == 1 && isVanilla == 1 && version != null && !version.equals("unknown")) {
            startSocket();
            log(INFO, "Enable touchinjector for vanilla " + version);
        }
    }

    private static void startSocket() {
        SocketServer server = new SocketServer("127.0.0.1", 2333, (server1, msg) -> {
            if (msg.equals("refresh")) {
                log(INFO, "Message received ! Start getting raytrace result type.");
                rayTrace();
            }
        });
        server.start();
    }

    private static void rayTrace() {
        Thread thread = new Thread("Raytrace thread") {
            public void run() {
                try {
                    log(INFO, "Refresh raytrace result type and send to launcher !");
                    switch (version) {
                        case "1.12":
                            sendType(getRaytraceResultType112());
                            break;
                        case "1.13":
                            sendType(getRaytraceResultType113());
                            break;
                        case "1.14":
                            sendType(getRaytraceResultType114());
                            break;
                        case "1.15":
                            sendType(getRaytraceResultType115());
                            break;
                        case "1.16":
                            sendType(getRaytraceResultType116());
                            break;
                        case "1.17":
                            sendType(getRaytraceResultType117());
                            break;
                        case "1.18":
                            sendType(getRaytraceResultType118());
                            break;
                        case "1.19":
                            sendType(getRaytraceResultType119());
                            break;
                        default:
                            log(WARNING, "TouchInjector does not support this version ! Return null.");
                            break;
                    }
                } catch (Exception e) {
                    log(ERROR, e.getMessage());
                }
            }
        };
        thread.start();
    }

    public static String getRaytraceResultType112() {
        bib minecraft = bib.z();
        bhc target = minecraft.s;
        if (target != null) {
            bhc.a type = target.a;
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType113() {
        cft minecraft = cft.s();
        ceb target = minecraft.s;
        if (target != null) {
            ceb.a type = target.a;
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType114() {
        cyc minecraft = cyc.u();
        csf target = minecraft.u;
        if (target != null) {
            csf.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType115() {
        dbn minecraft = dbn.x();
        cvi target = minecraft.u;
        if (target != null) {
            cvi.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType116() {
        djz minecraft = djz.C();
        dcl target = minecraft.v;
        if (target != null) {
            dcl.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType117() {
        dvp minecraft = dvp.C();
        dmy target = minecraft.v;
        if (target != null) {
            dmy.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType118() {
        dyr minecraft = dyr.D();
        dpm target = minecraft.v;
        if (target != null) {
            dpm.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType119() {
        eev minecraft = eev.G();
        dvr target = minecraft.v;
        if (target != null) {
            dvr.a type = target.c();
            return type.name();
        }
        else {
            log(WARNING, "RayTraceResult is null !");
        }
        return "UNKNOWN";
    }

    private static void sendType(String type) {
        try {
            log(INFO, "Current raytrace result type : " + type);
            DatagramSocket socket = new DatagramSocket();
            socket.connect(new InetSocketAddress("127.0.0.1", 2332));
            byte[] data = (type).getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            log(WARNING, e.getMessage());
        }
    }

}
