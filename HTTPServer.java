
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class HTTPServer {

	/**
	 * The main method.
	 * @param args the file to serve and the port to listen on.
	 */
	public static void main(String[] args) {
		try { 
			/*
			 * If the file is not an HTML file, use text/plain as the 
			 * content type:
			 */
  		String contentType = "text/plain"; 
  		if (args[0].endsWith(".html") || args[0].endsWith(".htm")) { 
  			contentType = "text/html"; 
  		} 
  		/*
  		 * Load the file into a byte array:
  		 */
  		InputStream in = new FileInputStream(args[0]); 
  		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
  		int b; 
  		while ((b = in.read()) != -1) out.write(b); 
  		byte[] data = out.toByteArray(); 
      in.close();
  		/*
  		 *  Determine which port to listen on:
  		 */ 
  		int port; 
  		try { 
  			port = Integer.parseInt(args[1]); 
  			if (port < 1 || port > 65535) port = 80; 
  	  } catch (Exception e) { 
  	  	port = 80; 
  	  } 
  	  /*
  	   * Start an HTTP request handler:
  	   */
  	  Thread t = new RequestHandler(data, contentType, port); 
  	  t.start(); 
    } catch (ArrayIndexOutOfBoundsException e) { 
    	System.out.println( "Usage: java httpserver.HTTPServer <filename> <port>"); 
    } catch (Exception e) { 
    	System.err.println(e); 
    }
	}
}
