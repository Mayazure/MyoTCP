package test;

import java.util.Scanner;

public class TestString {
	
	public static void main(String[] args){
		
		Scanner scanner = new Scanner(System.in);
		while(true){
//			String a = "127.0.0.1 Hello world!";			
			String a = scanner.next();
			if(a.equals("end")){
				break;
			}
			a=a.replace("/", "");
			String[] data = a.split("&");
			System.out.println(data[0]);
			System.out.println(data[1]);
		}
		scanner.close();
		
	}
}
