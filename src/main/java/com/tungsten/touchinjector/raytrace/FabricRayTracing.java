package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.util.SocketServer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.Level.WARNING;
import static com.tungsten.touchinjector.util.Logging.log;
import static com.tungsten.touchinjector.TouchKnotClient.classLoader;

public class FabricRayTracing {

    private static String version;

    public static void init(String args) {
        FabricRayTracing.version = args;
        start();
    }

    private static void start() {
        startSocket();
        log(INFO, "Enable touchinjector for fabric " + version);
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
        Thread thread = new Thread("Fabric Raytrace thread") {
            public void run() {
                try {
                    log(INFO, "Refresh raytrace result type and send to launcher !");
                    sendType(getRaytraceResultType());
                } catch (Exception e) {
                    log(ERROR, e.getMessage());
                }
            }
        };
        thread.start();
    }

    public static String getRaytraceResultType() {
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.class_310", true, classLoader);
            Method method = minecraftClass.getMethod("method_1551");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("field_1765");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("method_17783");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException |
                 IllegalAccessException e) {
            log(ERROR, e.getMessage());
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
