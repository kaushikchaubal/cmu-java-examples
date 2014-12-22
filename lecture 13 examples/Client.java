import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Socket sock = new Socket(args[0],7777);
		OutputStream os = sock.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(args[1]);
		oos.flush();
		
		InputStream is = sock.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Object obj = ois.readObject();
		System.out.println("Received from "+sock.getInetAddress()+": "+obj);
	}
}
