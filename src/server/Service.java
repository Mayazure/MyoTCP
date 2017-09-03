package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable{
	
	private Server server;
	private Socket socket;
	private String ip;
	private Boolean socketIsOn = false;
	private DataOutputStream out;
	private DataInputStream in;
	private String data;
	
	public Service(Socket socket, Server server){
		this.server = server;
		this.socket = socket;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socketIsOn = true; 
		ip = socket.getInetAddress().toString();
		ip = ip.replace("/", "");
	}
	
	public void send(String outData){
		try {
			out.writeUTF(outData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(socketIsOn){
			if(socket.isClosed()){
				break;
			}
			try {
				data = in.readUTF();
				onReceive(ip,data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("Client read timeout.");
//				socketIsOn = false;
			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		try {
			in.close();
			out.close();
			socket.close();
			this.server.close(ip);
//			System.out.println(ip+": Client closed.");
			server.onReceive(ip + ": Client closed.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void onReceive(String ip, String data){
//		System.out.println(ip+" received: "+data+"; "+Thread.currentThread().getId()+" "+Thread.currentThread().getName());
		if(data.equals("PingRes")){
			server.onPing();
		}
		else{
			data = "$T2="+System.currentTimeMillis() + data;
			server.onReceive(ip, data);
		}
	}
	
	public void close(){
		synchronized (socketIsOn) {
			this.socketIsOn = false;
		}
		
		try {
			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}