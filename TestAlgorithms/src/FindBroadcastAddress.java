import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class FindBroadcastAddress {
	public static void main(String[] args) throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if(networkInterface.isLoopback()) {
				continue;
			}
			for(InterfaceAddress interfaceAddress: networkInterface.getInterfaceAddresses()) {
				InetAddress broadcast = interfaceAddress.getBroadcast();
				if(broadcast == null) {
					continue;
				} else {
					System.out.println("The broadcast IP address is: " + broadcast.getHostAddress());
				}
			}
		}
		DatagramSocket socket = new DatagramSocket();
		
		
	}

}
