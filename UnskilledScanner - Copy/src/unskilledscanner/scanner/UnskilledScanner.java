package unskilledscanner.scanner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UnskilledScanner {
	
	Map<Integer, Boolean> ports;
	Map<byte[], Boolean> address;
	Socket socket;
	
	public UnskilledScanner() {
		this.ports = new HashMap<Integer, Boolean>();
		this.address = new HashMap<byte[], Boolean>();
	}
	
	public void scanPorts(String address, int portDeb, int portFin) {
		for (; portDeb <= portFin; portDeb++) {
			try{
				socket = new Socket(address, portDeb);
				System.out.println("Port ouvert : " + portDeb);
				this.ports.put(portDeb, true);
			}catch(Exception e) {
				System.out.println("Erreur sur le port " + portDeb);
				this.ports.put(portDeb, false);
			}
		}
	}
	
	public void scanLocalNetwork(String ip) {
		byte[] nip = getByteFormat(ip);
		int returnVal = 0;;
		for (int i = 1; i < 255; i++) {
			nip[3] = (byte)i;
			try {
				InetAddress addr = InetAddress.getByAddress(nip);
				Process p1 = java.lang.Runtime.getRuntime().exec("ping -w 2 -n 2 " + addr.getHostAddress());
				returnVal = p1.waitFor();
				
				if (returnVal == 0) {
					System.out.println(addr.getHostAddress() + " founded on your local network.");
				}
			}catch(IOException | InterruptedException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("Local NetWork Scann finished.");
	}
	
	public byte[] getByteFormat(String iproot) {
		String nipTemp = iproot.replace('.', '-');
        String[] nip = nipTemp.split("-");
        byte[] ip = {(byte)Integer.parseInt(nip[0]), (byte)Integer.parseInt(nip[1]),
                    (byte)Integer.parseInt(nip[2]), (byte)Integer.parseInt(nip[3])};
        return ip;
    }
	
}
