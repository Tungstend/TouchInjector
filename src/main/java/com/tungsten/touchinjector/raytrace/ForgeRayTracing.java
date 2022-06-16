package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.raytrace.forge.RayTracingA;
import com.tungsten.touchinjector.raytrace.forge.RayTracingB;
import com.tungsten.touchinjector.raytrace.forge.RayTracingC;
import com.tungsten.touchinjector.raytrace.forge.RayTracingD;
import com.tungsten.touchinjector.util.SocketServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class ForgeRayTracing {

    private static String version;

    public static void init(String args) {
        ForgeRayTracing.version = args;
        start();
    }

    private static void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft", true, Thread.currentThread().getContextClassLoader());
                    log(INFO, minecraftClass.getName());
                } catch (ClassNotFoundException e) {
                    log(ERROR, e.getMessage());
                }
            }
        }, 1000);
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
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread thread = new Thread("Forge Raytrace thread") {
            public void run() {
                Thread.currentThread().setContextClassLoader(classLoader);
                try {
                    log(INFO, "Refresh raytrace result type and send to launcher !");
                    switch (version) {
                        case "1.7":
                        case "1.8":
                        case "1.9":
                        case "1.10":
                        case "1.11":
                        case "1.12":
                            sendType(RayTracingA.getRaytraceResultType());
                            break;
                        case "1.13":
                            sendType(RayTracingB.getRaytraceResultType());
                            break;
                        case "1.14":
                        case "1.15":
                        case "1.16":
                            sendType(RayTracingC.getRaytraceResultType());
                            break;
                        case "1.17":
                        case "1.18":
                        case "1.19":
                            sendType(RayTracingD.getRaytraceResultType());
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
