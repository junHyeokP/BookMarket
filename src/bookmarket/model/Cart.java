package bookmarket.model;

import java.util.ArrayList;

public class Cart {
	private ArrayList<CartItem> itemList = new ArrayList<>();
	// private int numItems = 0;
	
	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	public ArrayList<CartItem> getItemList() {
		return itemList;
	}

//	public void setItemList(ArrayList<CartItem> itemList) {
//		this.itemList = itemList;
//	}

	public int getNumItems() {
		return itemList.size();
	}

	public String getItemInfo(int index) {
		return itemList.get(index).toString();
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
		
		for (CartItem item : itemList) {
			if (item.getBook() == book) return item;
		}
		
		return null;
	}
	
	private CartItem getCartItem(int bookId) {
		
		for (CartItem item : itemList) {
			if (item.bookId == bookId) return item;
		}
		return null;
	}
	
	public void deleteItem(Book book) {
		itemList.remove(book);
	}

	public void resetCart() {
		this.itemList.clear();
	}

	public boolean isValidItem(int bookId) {
		for (CartItem item : itemList) {
			if (item.bookId == bookId) return true;
		}
		return false;
	}
	
	public void deleteItem(int bookId) {
		CartItem item = getCartItem(bookId);
		itemList.remove(item);
	}

	public void updateQuantity(int bookId, int quantity) {
		
		if (quantity == 0) deleteItem(bookId);
		else {
			CartItem item = getCartItem(bookId);
			item.setQuantity(quantity);
		}
		
	}

}
