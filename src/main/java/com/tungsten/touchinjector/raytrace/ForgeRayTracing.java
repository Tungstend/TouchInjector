package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.raytrace.forge.RayTracingA;
import com.tungsten.touchinjector.raytrace.forge.RayTracingB;
import com.tungsten.touchinjector.raytrace.forge.RayTracingC;
import com.tungsten.touchinjector.util.SocketServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class ForgeRayTracing {

    private static String version;

    public static void init(String args) {
        ForgeRayTracing.version = args;
        start();
    }

    private static void start() {
        startSocket();
        log(INFO, "Enable touchinjector for forge " + version);
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
        Thread thread = new Thread("Forge Raytrace thread") {
            public void run() {
                try {
                    log(INFO, "Refresh raytrace result type and send to launcher !");
                    switch (version) {
                        case "1.12":
                            sendType(RayTracingA.getRaytraceResultType_a());
                            break;
                        case "1.13":
                            sendType(RayTracingB.getRaytraceResultType_b());
                            break;
                        case "1.14":
                        case "1.15":
                        case "1.16":
                            sendType(RayTracingC.getRaytraceResultType_c());
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

    public static String getRaytraceResultType117() {

        return "UNKNOWN";
    }

    public static String getRaytraceResultType118() {

        return "UNKNOWN";
    }

    public static String getRaytraceResultType119() {

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
