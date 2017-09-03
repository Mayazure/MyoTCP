package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import client.Client;

public class TestClient {
	
	public static void main(String[] args){
		Client client = new Client();
		client.connect("127.0.0.1", 9020);
//		client.connect("192.168.1.17", 9020);
		Thread thread = new Thread(client);
		thread.start();
		
		Scanner scanner = new Scanner(System.in);
		
		while(client.isOn()){
			String data = scanner.nextLine();
			if(data.equals("end")){
				client.close();
				break;
			}
			else if(data.equals("loop")){
				for(int i=0;i<50;i++){
					client.send("loop"+i);
				}
			}
			else{
				client.send(data);
			}
		}
		scanner.close();
	}
	
	public static void plainTest(){
		Socket client;
		DataInputStream inputStream = null;
		DataOutputStream outputStream = null;
		try{
			client = new Socket("127.0.0.1", 9020);
			inputStream = new DataInputStream(client.getInputStream());
			outputStream = new DataOutputStream(client.getOutputStream());
			outputStream.writeUTF("Client");
			while(true){
				String a = inputStream.readUTF();
				System.out.println(a);
				Thread.sleep(1000);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
