public interface ToDoListDAO {

	/**
	 * Add the item to the top of the list
	 * @param item text to be added to the ToDo list
	 */
	public abstract void addToTop(String item);
	
	/**
	 * Add the item to the end of the list
	 * @param item text to be added to the ToDo list
	 * @return the position of the added item.  First item is in position 1.
	 */
	public abstract int addToBottom(String item);
	
	/**
	 * Deletes an item from the ToDo list.  If position is not an int, false is returned.
	 * @param position the position in the list of the item to be deleted.  First item is in position 1.
	 * @return true if success, false if the is no item on the list in this position.
	 */
	public abstract boolean delete(int position);

	/**
	 * Gets an item from the ToDo list
	 * @param index the index of the item to retrieve.  First item has position 1.
	 * @return an item or null if no such item
	 */
	public abstract String getItem(int position);

	/**
	 * Gets all the items from the ToDo list
	 * @return all the items on the list
	 */
	public abstract String[] getItems();
	
	/**
	 * Gets the number of items in the ToDo list
	 * @return the number of items on the list
	 */
	public abstract int size();
}
