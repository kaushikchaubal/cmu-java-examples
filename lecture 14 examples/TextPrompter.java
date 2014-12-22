import java.io.IOException;

public class TextPrompter {
	public static String promptLine(String prompt, String defaultValue) {
		while (true) {
			System.out.print(prompt);
			if (defaultValue != null) System.out.print(" ["+defaultValue+"]");
			System.out.print(" ");
	
			String line = trim(readLine());
			if (line.length() == 0) {
				return defaultValue;
			}
			
			return line;
		}
	}

	private static String readLine() {
		StringBuffer b = new StringBuffer();
		try {
			int c = System.in.read();
			while (true) {
				if (c == -1) { System.err.println(" * * * End of input stream"); System.exit(1); }
				if (c == '\n') return b.toString();				
				if (c != '\r') b.append((char) c);
				c = System.in.read();
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(1);
			return null;
		}
	}

	private static String trim(String s) {
		if (s.length() == 0) return s;
		if (s.charAt(0) != ' ' && s.charAt(s.length()-1) != ' ') return s;

		int begin = 0;
		while (begin < s.length() && s.charAt(begin) == ' ') begin++;

		int end = s.length();
		while (end > 0 && s.charAt(end-1) == ' ') end--;

		if (begin >= end) return "";
		return s.substring(begin,end);
	}
}

