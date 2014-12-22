import org.mybeans.editor.BeanEditor;
import org.mybeans.factory.BeanFactory;
import org.mybeans.factory.BeanTable;

import databeans.Entry;

public class EntryEdit {
	public static void main(String[] args) {
		BeanTable<Entry> table = BeanTable.getSQLInstance(
													Entry.class,
													"entry",
													"com.mysql.jdbc.Driver",
													"jdbc:mysql:///test");
		
		if (!table.exists()) table.create("id");
		BeanFactory<Entry> factory = table.getFactory();
		BeanEditor<Entry> editor = BeanEditor.getInstance(factory);
		editor.start();
	}
}
