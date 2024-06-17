package bookmarket.model;

import java.util.ArrayList;
 //장바구니
public class Cart {
	private ArrayList<CartItem> itemList = new ArrayList<>(); // 장바구니에 책을 담을 ArrayList 생성, 장바구니에 담긴 책들은 CartItem 제네릭으로 명시
	
	public boolean isEmpty() { // 장바구니가 비었는지 확인하는 메서드
		return itemList.isEmpty();
	}

	public ArrayList<CartItem> getItemList() { // 장바구니 목록을(ArrayList) 반환하는 매서드
		return itemList;
	}

	public int getNumItems() { // 장바구니에 담긴 책들의 갯수 반환
		return itemList.size();
	}

	public String getItemInfo(int index) { // 매개변수로 가져온 번호에 해당하는 책의 정보들을 반환
		return itemList.get(index).toString();
	}
	
	public void addItem(Book book) { // 장바구니에 책을 담는 메서드
		
		CartItem item = getCartItem(book); // CartItem 타입 item에 해당 책과 동일한게 있는지 확인하는 메서드 호출 후 저장
		if (item == null) { // null이 반환되었다면 (장바구니에 담긴 책들 중 동일한게 없다면)
			itemList.add(new CartItem(book)); // ItemList에 매개변수 book 인스턴스를 담은 CartItem을 추가
		}
		else {
			item.addQuantity(1); // 동일한 책이 있을시 해당 책의 갯수 하나 추가
		}
	}

	private CartItem getCartItem(Book book) { // 동일한 책이 있는지 확인하는 메서드
		
		for (CartItem item : itemList) { // 배열에서 CartItem들을 하나 씩 추출하여 if문 실행
			if (item.getBook() == book) return item; // 만약 장바구니 속 책의 id와 매개변수로 들어온 book의 id가 같을 경우 헤딩 책을 반환
		}
		
		return null; // 책 Id와 같은게 없을 경우 null 반환
	}
	
	private CartItem getCartItem(int bookId) { // 매개변수로 가져온 bookId와 같은 id들 지닌 장바구니 속 책이 있는지 확인하는 메서드
		
		for (CartItem item : itemList) { // 장바구니 속 책들을 하나 씩 꺼내어 비교
			if (item.bookId == bookId) return item; // 매개변수 값과 같은 Id를 지닌 장바구니 속 책이 있다면 그 장바구니 속 책을 반환
		}
		return null; // 같은 책 Id가 없다면 null 반환
	}
	
	public void deleteItem(Book book) { // 장바구니 속 책 제거
		itemList.remove(book); // 장바구니 속 해당 책 제거
	}

	public void resetCart() {
		this.itemList.clear(); // 장바구니 속 책들 전부 초기화
	}

	public boolean isValidItem(int bookId) { // 장바구니 속 책들 중 매개변수로 들어온 책 Id가 있는지 확인하는 메서드
		for (CartItem item : itemList) { // 장바구니에서 책을 꺼내어 확인
			if (item.bookId == bookId) return true; // 같은 Id라면 true
		}
		return false; // 아닐시 false
	}
	
	public void deleteItem(int bookId) { // 매개변수로 들여온 책 Id와 동일한 id를 가진 책을 찾아 제거하는 메서드
		CartItem item = getCartItem(bookId); // id를 찾아 item에 넣어서
		itemList.remove(item); // 해당 요소 제거
	}

	public void updateQuantity(int bookId, int quantity) { // 해당 bookId를 지닌 책의 갯수를 변경해주는 메서드
		
		if (quantity == 0) deleteItem(bookId); // 갯수가 0일시 해당 책 삭제
		else {
			CartItem item = getCartItem(bookId); // 아닐시 책의 id 확인 후 item에 저장
			item.setQuantity(quantity); // 매개변수로 들여온 값을 책의 갯수로 지정
		}
		
	}

}
