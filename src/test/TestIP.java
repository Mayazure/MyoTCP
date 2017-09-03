package test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class TestIP {
    public static void main(String[] args) throws UnknownHostException,
            SocketException {
        System.out.println("Host:/t" + InetAddress.getLocalHost() + "/n");
        Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces();
        Enumeration<InetAddress> addresses;
        while (en.hasMoreElements()) {
            NetworkInterface networkinterface = en.nextElement();
            System.out.println(networkinterface.getName());
            addresses = networkinterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                System.out.println("/t"
                        + addresses.nextElement().getHostAddress() + "");
            }
        }
    }
}