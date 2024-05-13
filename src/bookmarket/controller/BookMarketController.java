package bookmarket.controller;

import bookmarket.model.BookStorage; 
import bookmarket.model.Cart;
import bookmarket.view.ConsoleView;

public class BookMarketController {
	
	ConsoleView view;
	BookStorage mBookStorage;
	Cart mCart;
	String[] menuList = {
			"0. 종료",
			"1. 도서 정보 보기",
			"2. 장바구니 보기", 
			"3. 장바구니에 도서 담기",
			"4. 장바구니 비우기"
	};
	
	public BookMarketController(BookStorage bookStorage, Cart cart, ConsoleView view) {
		this.view = view;
		this.mBookStorage = bookStorage;
		this.mCart = cart;
	}

	public void start() {
		view.displaywelcome();
		
		int menu;
		
		do {
			menu = view.selectMenu(menuList);
			
			switch (menu) {
			case 1 : viewBookInfo(); break;
			case 2 : viewCart(); break;
			case 3 : addBook2Cart(); break;
			case 4 : resetCart(); break;
			}
		} while(menu != 0);
		view.showMessage("BookMarket을 종료합니다.");
		
	}

	private void resetCart() {
		view.displayCart(mCart);
		
		if (!mCart.isEmpty()) {
		view.askConfirm("장바구니를 비우시려면 yes를 입력하세요", "yes");
		mCart.resetCart();
		view.showMessage(">> 장바구니를 비웠습니다.");
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
