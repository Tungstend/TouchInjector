package com.tungsten.touchinjector.util;

import java.io.IOException;
import java.net.*;

import static com.tungsten.touchinjector.util.Logging.Level.ERROR;
import static com.tungsten.touchinjector.util.Logging.Level.WARNING;
import static com.tungsten.touchinjector.util.Logging.log;

public class SocketServer {

	private DatagramPacket packet;
	private DatagramSocket socket;
	private final Listener mListener;
	private final String ip;
	private final int port;

	public interface Listener {
		void onReceive(SocketServer server, String msg);
	}
	
	public SocketServer(String ip, int port, Listener mListener) {
		this.mListener = mListener;
		this.ip = ip;
		this.port = port;
		try {
			byte[] bytes = new byte[1024];
			packet = new DatagramPacket(bytes, bytes.length);
			socket = new DatagramSocket(port, InetAddress.getByName(ip));
		} catch (Exception e) {
			log(ERROR, "Failed to start socket !\n" + e.getMessage());
		}
	}

	public void start() {
		if(packet == null || socket == null)
			return;
		new Thread(() -> {
			try {
				while(true){
					socket.receive(packet);
					String receiveMsg = new String(packet.getData(), 0, packet.getLength());
					mListener.onReceive(this, receiveMsg);
				}
			} catch (IOException e) {
				log(WARNING, "Failed to get message from launcher !");
			}
		}).start();
	}

	public void stop() {
		socket.close();
	}

}