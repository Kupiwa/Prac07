import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler extends Thread {
    private byte[] content;
    private byte[] header;
    private int port;

    public RequestHandler(byte[] data, String MIMEType, int port) {
        this.content = data;
        this.port = port;
        String headerText = "HTTP/1.0 200 OK\r\n" +
                            "Server: HTTPServer 1.0\r\n" +
                            "MIME-version: 1.0\r\n" +
                            "Content-length: " + data.length + "\r\n" +
                            "Content-type: " + MIMEType + "\r\n\r\n";
        this.header = headerText.getBytes();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server running on " + serverSocket.getInetAddress() + ":" + port);
            System.out.println("Serving content: " + new String(content));
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    StringBuilder requestBuilder = new StringBuilder();
                    int c;
                    while ((c = connection.getInputStream().read()) != -1) {
                        if (c == '\r' || c == '\n') {
                            break;
                        }
                        requestBuilder.append((char) c);
                    }
                    String request = requestBuilder.toString();
                    if (request.contains("HTTP")) {
                        connection.getOutputStream().write(header);
                        connection.getOutputStream().write(content);
                        connection.getOutputStream().flush();
                    }
                    connection.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("The port could not be opened and is probably already occupied.");
        }
    }
}
