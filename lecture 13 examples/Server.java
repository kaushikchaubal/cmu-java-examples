import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket ss = new ServerSocket(7777);
		Socket sock = ss.accept();
		System.out.println("Connection from "+sock.getInetAddress());

		InputStream is = sock.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Object obj = ois.readObject();
		System.out.println("   Received: "+obj);
		
		OutputStream os = sock.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject("Thank you: "+sock.getInetAddress());
		oos.close();
		ois.close();
		sock.close();
	}
}
