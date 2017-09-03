package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import controller.MainWindow;
import util.TimeParser;

public class Server implements Runnable{
	private final int PORT = 9020;
	private String localIP;
	private String localName;
	
	private ServerSocket server;
	private MainWindow mainWindow;
	
	private boolean serverIsOn = false;
	
//	private ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	private HashMap<String,Service> services = new HashMap<String, Service>();
	private HashMap<String,Thread> threads = new HashMap<String, Thread>();
	
	public Server(MainWindow mainWindow){
		this();
		this.mainWindow = mainWindow;
	}
	
	public Server(){
		
		try {
			this.server = new ServerSocket(PORT);
			serverIsOn = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InetAddress intAddress;
		try {
			intAddress = InetAddress.getLocalHost();
			this.localIP = intAddress.getHostAddress();
			this.localName = intAddress.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getIP(){
		return this.localIP;
	}
	
	public String getName(){
		return this.localName;
	}
	
	@Override
	public void run(){
		if(serverIsOn){
			mainWindow.updateConsole("Server successfully started.");
		}
		while (serverIsOn){
			try {
				Socket socket = this.server.accept();
//				clientSockets.add(socket);
				socket.setSoTimeout(2000);
				String ip = socket.getInetAddress().toString();
				ip = ip.replace("/", "");
				Service service = new Service(socket, this);
				Thread thread = new Thread(service,ip);
				thread.start();
				services.put(ip,service);
				threads.put(ip, thread);
//				System.out.println(ip+" connected.");
				mainWindow.updateConsole(ip+" connected.");
				mainWindow.updateIP(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String send(String ip, String data){
		Service service = services.get(ip);
		if(service == null){
			return "Client disconneted.";
		}
		else{
			//debug
			//TimeParser.getCurrentHourTime();
			
			service.send(data);
			
			//debug
			//TimeParser.getCurrentHourTime();
			
			return "Data sent.";
		}
	}
	
	public void onReceive(String ip, String data){
		mainWindow.updateConsole(ip, data);
//		mainWindow.recordToFile(data);
	}
	
	public void onReceive(String data){
		mainWindow.updateConsole(data);
		if(data.endsWith(": Client closed.")){
			mainWindow.endSignal();
		}
	}
	
	public void onPing(){
		mainWindow.onPing();
	}
	
	public HashMap<String, Service> getServices(){
		return this.services;
	}
	
	public void close(String ip){
		services.remove(ip);
//		mainWindow.deleteIP(ip);
	}
	
	public void closeAll(){
		Iterator<Entry<String, Service>> iter = services.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Service> entry = (Map.Entry<String, Service>) iter.next();
			Service service = (Service)entry.getValue();
			service.send("END");
			service.close();
			services.remove(service);
		}
	}
	
	public void closeServer(){
		serverIsOn = false;
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}