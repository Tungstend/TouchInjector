# TouchInjector
 BE touch for HMCL-PE

## Introduction
Touch-Injector calls the function of minecraft to obtain the type of the mouse pointer, and sends it to the launcher through the socket to judge the touch event, so as to realize a touch mechanism similar to that of Bedrock Edition.

## Support Versions
* Vanilla Minecraft 1.7.x-1.19.x
* Forge/Forge+OptiFine 1.7.x-1.19.x
* OptiFine 1.7.x-1.18.x
* LiteLoader 1.7.x-1.12.x
* Fabric 1.14.x-1.19.x

## Dependency
[Touch-Injector-Dependency](https://github.com/Tungstend/TouchInjectorDependency): compileOnly library.

## Usage

### Compilation
Compiler: Java 1.8.

### Launch
#### Vanilla Minecraft/OptiFine/LiteLoader
Simply add jvm argument:
```bash
-javaagent:[Touch-Injector path]=vanilla/optifine/optifine
```
#### Forge
1.7.x-1.16.x: Simply add jvm argument:
```bash
-javaagent:[Touch-Injector path]=forge
```
1.17+: Switch to [Forge](https://github.com/Tungstend/TouchInjector/tree/Forge) branch, Touch-Injector is used as a forge plugin on forge1.17+,just add [Touch-Injector path] to classpath. Then add a jvm argument:
```bash
-Dtouchinjector.version=1.17/1.18/1.19
```
#### Fabric
Touch-Injector is used as mainClass when launch Fabric, add [Touch-Injector path] to classpath, then change the mainClass to:
```bash
com.tungsten.touchinjector.launch.TouchKnotClient
```

### Options
Enable debug log output:
```bash
-Dtouchinjector.debug
```

### How to get mouse pointer type
Send message "refresh" to game and Touch-Injector will return current type to launcher. There are four types you may get:
* MISS (pointer doesn't point to anything)
* BLOCK (pointer points to block)
* ENTITY (pointer points to entity)
* UNKNOWN (this shouldn't happen!)

First start a socket to receive message from game, port is 2332, so you will receive msg from game from this socket. When you need to get pointer type, you need to send a msg to another socket(port is 2333), like this:
```bash
new Thread(() -> {
    try {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("127.0.0.1", 2333));
        byte[] data = ("refresh").getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.send(packet);
        socket.close();
    }catch (Exception e){
        e.printStackTrace();
    }
}).start();
```
Then the first socket will receive type msg at about 15-35ms later.