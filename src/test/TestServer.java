package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import controller.MainWindow;
import server.Server;
import server.Service;

public class TestServer {

	public static void main(String[] agrs){

		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		Server server = new Server();
		System.out.println(server.getIP());
		System.out.println(server.getName());
		Thread thread = new Thread(server,"server");
		thread.start();
		Scanner scanner = new Scanner(System.in);
		while(true){
			String cmd = scanner.nextLine();
			if(cmd.equals("end")){
				break;
			}
			else if(cmd.equals("ip")){
				HashMap<String,Service> map = server.getServices();
				if (map.isEmpty()){
					System.out.println("No client connected.");
					continue;
				}
				Iterator<Entry<String, Service>> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, Service> entry = (Map.Entry<String, Service>) iter.next();
					String key = (String)entry.getKey();
					System.out.println(key);
				}

			}
			else if(cmd.contains("&")){
				String[] data = cmd.split("&");
				server.send(data[0], data[1]);
			}

		}
		scanner.close();
	}
}