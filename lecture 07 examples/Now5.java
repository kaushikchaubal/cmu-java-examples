import java.text.SimpleDateFormat;
import java.util.Date;

public class Now5 {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy @ HH:mm");
		Date d = new Date();
		System.out.println(sdf.format(d));
	}
}
