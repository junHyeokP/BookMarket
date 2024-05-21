package bookmarket.controller;

import bookmarket.model.BookStorage;    
import bookmarket.model.Cart;
import bookmarket.view.ConsoleView;
import bookmarket.model.Customer;

public class BookMarketController {
	
	ConsoleView view;
	BookStorage mBookStorage;
	Cart mCart;
	Customer Customer; // model에서 import해서 가져오기
	String[] menuList = {
			"0. 종료",
			"1. 도서 정보 보기",
			"2. 장바구니 보기", 
			"3. 장바구니에 도서 추가",
			"5. 장바구니 도서 삭제",
			"6. 장바구니 도서 수량 변경",
			"7. 장바구니 비우기",
			"8. 주문"
	};
	
	public BookMarketController(BookStorage bookStorage, Cart cart, ConsoleView view) {
		this.view = view;
		this.mBookStorage = bookStorage;
		this.mCart = cart;
		Customer customer = new Customer();
	}

	public void start() {
		view.displaywelcome();
	    view.CustomerInfo(Customer);
		int menu;
		
		do {
			menu = view.selectMenu(menuList);
			
			switch (menu) {
			case 1 -> viewBookInfo(); 
			case 2 -> viewCart();
			case 3 -> addBook2Cart();
			case 4 -> deleteBookInCart();
			case 5 -> updateBookInCart();
			case 6 -> resetCart();
			case 7 -> order();
			case 0 -> end();
			default -> view.showMessage("잘못된 메뉴 번호입니다.");
			}
		} while(menu != 0);
	}

	private void end() {
		view.showMessage("BookMarket을 종료합니다.");
	}
	private void order() {
		
		//배송 정보 추가
		addDeliveryInfo();
		//구매 정보 보기 : 장바구니 내역, 배송 정보
		displayOrderInfo();
		//주문할건지 다시 확인
		if (view.askConfirm("정말 주문하시겠습니까 ? yes 아님 no를 입력해주세요.", "yes")) {
			//주문 처리
			mCart.resetCart();
		}
	}

	private void addDeliveryInfo() {
		view.inputDeliveryInfo(Customer);
	}

	private void displayOrderInfo() {
		view.displayOrder(mCart, Customer);
		view.displayDeliveryInfo(Customer);
		
	}

	private void updateBookInCart() {
		// 장바구니 보여주기
		
		if (!mCart.isEmpty()) {
			//도서 ID 입력 받기
			int bookId = view.selectBookId(mCart);
			// 수량 입력 받기
			int quantity = view.inputNumber(0, mBookStorage.getMaxQuantity());
			// 도서 ID에 해당하는 cartItem 가져와서 cartItem quantity set 수량
			mCart.updateQuantity(bookId, quantity);
		}
	}

	private void deleteBookInCart() {
		// 장바구니 보여주기
		view.displayCart(mCart);
		if (!mCart.isEmpty()) {
		//도서 ID 입력 받기
			int bookId = view.selectBookId(mCart);
			if (view.askConfirm(">> 해당 도서를 삭제하려면 yes를 입력하세요 : ", "yes")) {
		// 해당 도서 ID의 cartItem 삭제
				mCart.deleteItem(bookId);
				view.showMessage(">> 해당 도서를 삭제했습니다.");
			}
		}
	}

	private void resetCart() {
		view.displayCart(mCart);
		
		if (!mCart.isEmpty()) {
			if (view.askConfirm("장바구니를 비우시려면 yes를 입력하세요", "yes")) {
				mCart.resetCart();
				view.showMessage(">> 장바구니를 비웠습니다.");
			}
		}
		
	}

	private void addBook2Cart() {
		view.displayBookInfo(mBookStorage);
		int bookId = view.selectBookId(mBookStorage);
		mCart.addItem(mBookStorage.getBookById(bookId));
		view.showMessage("장바구니에 도서를 추가하였습니다.");
	}

	private void viewCart() {
		view.displayCart(mCart);
	}

	private void viewBookInfo() {
		view.displayBookInfo(mBookStorage);
		}
		

}