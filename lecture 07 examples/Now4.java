import java.util.Calendar;

public class Now4 {
	public static void main(String[] args) {
	    Calendar c = Calendar.getInstance();
	    int year   = c.get(Calendar.YEAR);
	    int month  = c.get(Calendar.MONTH);
	    int day    = c.get(Calendar.DAY_OF_MONTH);
	    int dow    = c.get(Calendar.DAY_OF_WEEK);
	    System.out.println("year = "+year);
	    System.out.println("mon  = "+month);
	    System.out.println("day  = "+day);
	    System.out.println("dow  = "+dow);
	}
}
