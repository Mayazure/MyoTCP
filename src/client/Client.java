package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{
	
	private Socket client;
	private boolean socketIsOn = false;
	
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;
	
	public Client(){
		
	}
	
	public boolean connect(String IP, int port){
		socketIsOn = true;
		try{
			client = new Socket(IP, port);
			client.setSoTimeout(5000);
			inputStream = new DataInputStream(client.getInputStream());
			outputStream = new DataOutputStream(client.getOutputStream());
		}catch (Exception e){
//			e.printStackTrace();
			System.out.println("Fatal: cannot connect to server.");
			socketIsOn = false;
		}
		return socketIsOn;
	}
	
	public void send(String data){
		try {
			if(socketIsOn){
				outputStream.writeUTF(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(socketIsOn){
			String data = null;
			try {
				data = inputStream.readUTF();
				onRead(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Read timeout.");
//				close();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void onRead(String data){
		System.out.println(data);
		if(data.equals("&close")){
			close();
		}
	}
	
	public boolean isOn(){
		return socketIsOn;
	}
	
	public void close(){
		socketIsOn = false;
		try {
			inputStream.close();
			outputStream.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
