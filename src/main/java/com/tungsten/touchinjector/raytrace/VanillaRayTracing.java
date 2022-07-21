package com.tungsten.touchinjector.raytrace;

import com.tungsten.touchinjector.util.SocketServer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import static com.tungsten.touchinjector.util.Logging.Level.*;
import static com.tungsten.touchinjector.util.Logging.log;

public class VanillaRayTracing {

    private static String version;

    public static void init(String args) {
        VanillaRayTracing.version = args;
        start();
    }

    private static void start() {
        startSocket();
        log(INFO, "Enable touchinjector for vanilla " + version);
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
        Thread thread = new Thread("Vanilla Raytrace thread") {
            public void run() {
                try {
                    log(INFO, "Refresh raytrace result type and send to launcher !");
                    switch (version) {
                        case "1.7":
                            sendType(getRaytraceResultType17());
                            break;
                        case "1.8":
                            sendType(getRaytraceResultType18());
                            break;
                        case "1.9":
                            sendType(getRaytraceResultType19());
                            break;
                        case "1.10":
                            sendType(getRaytraceResultType110());
                            break;
                        case "1.11":
                            sendType(getRaytraceResultType111());
                            break;
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

    public static String getRaytraceResultType17() {
        try {
            Class<?> minecraftClass = Class.forName("bao");
            Method method = minecraftClass.getDeclaredMethod("B");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("t");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType18() {
        try {
            Class<?> minecraftClass = Class.forName("ave");
            Method method = minecraftClass.getDeclaredMethod("A");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("s");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType19() {
        try {
            Class<?> minecraftClass = Class.forName("bcd");
            Method method = minecraftClass.getDeclaredMethod("z");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("t");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType110() {
        try {
            Class<?> minecraftClass = Class.forName("bcx");
            Method method = minecraftClass.getDeclaredMethod("z");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("t");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType111() {
        try {
            Class<?> minecraftClass = Class.forName("bes");
            Method method = minecraftClass.getDeclaredMethod("z");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("t");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType112() {
        try {
            Class<?> minecraftClass = Class.forName("bib");
            Method method = minecraftClass.getDeclaredMethod("z");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("s");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType113() {
        try {
            Class<?> minecraftClass = Class.forName("cft");
            Method method = minecraftClass.getDeclaredMethod("s");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("s");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Field typeField = target.getClass().getDeclaredField("a");
            typeField.setAccessible(true);
            Object type = typeField.get(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType114() {
        try {
            Class<?> minecraftClass = Class.forName("cyc");
            Method method = minecraftClass.getDeclaredMethod("u");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("u");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType115() {
        try {
            Class<?> minecraftClass = Class.forName("dbn");
            Method method = minecraftClass.getDeclaredMethod("x");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("u");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType116() {
        try {
            Class<?> minecraftClass = Class.forName("djz");
            Method method = minecraftClass.getDeclaredMethod("C");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("v");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType117() {
        try {
            Class<?> minecraftClass = Class.forName("dvp");
            Method method = minecraftClass.getDeclaredMethod("C");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("v");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType118() {
        try {
            Class<?> minecraftClass = Class.forName("dyr");
            Method method = minecraftClass.getDeclaredMethod("D");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("v");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
            log(ERROR, e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getRaytraceResultType119() {
        try {
            Class<?> minecraftClass = Class.forName("eev");
            Method method = minecraftClass.getDeclaredMethod("G");
            Object minecraft = method.invoke(null);
            Field targetField = minecraftClass.getDeclaredField("v");
            targetField.setAccessible(true);
            Object target = targetField.get(minecraft);
            Method typeMethod = target.getClass().getDeclaredMethod("c");
            typeMethod.setAccessible(true);
            Object type = typeMethod.invoke(target);
            return type.toString();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 NoSuchFieldException e) {
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