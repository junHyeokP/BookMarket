package bookmarket.model;

import java.util.ArrayList;

public class Cart {
	ArrayList<CartItem> itemList = new ArrayList<>();
	int numItems = 0;
	
	public boolean isEmpty() {
		return numItems == 0;
	}

	public ArrayList<CartItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<CartItem> itemList) {
		this.itemList = itemList;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public String getItemInfo() {
		return itemList.toString();
	}
	public void addItem(Book book) {
		
		CartItem item = getCartItem(book);
		if (item == null) {
			itemList.add(new CartItem(book));
		}
		else {
			item.addQuantity(1);
		}
	}

	private CartItem getCartItem(Book book) {
		for (int i = 0; i < numItems; i++) { 
			if(itemList.get(i).getBook() == book) {
				return itemList.get(i);
			}	
		}
		return null;
	}
	
	public void deleteItem(Book book) {
		itemList.remove(book);
	}

	public void resetCart() {
		numItems = 0;
		this.itemList.clear();
	}

}
