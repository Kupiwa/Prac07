import java.net.*;

public class SocketInfo {

    public static void main(String[] args) {
        
        for (String a : args) {
            try {
                Socket socket = new Socket(a, 80);
                InetAddress remoteAddress = socket.getInetAddress();
                int remotePort = socket.getPort();
                InetAddress localAddress = socket.getLocalAddress();
                int localPort = socket.getLocalPort();
                
                System.out.println("Remote InetAddress: " + remoteAddress);
                System.out.println("Remote Port: " + remotePort);
                System.out.println("Local InetAddress: " + localAddress);
                System.out.println("Local Port: " + localPort);
                
                socket.close();
            }catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}