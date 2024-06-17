package bookmarket.controller;

import bookmarket.model.Admin;  
import bookmarket.model.BookStorage;
import bookmarket.model.Cart;
import bookmarket.model.Customer;
import bookmarket.view.ConsoleView;

public class BookMarketController {
	
	ConsoleView view;
	BookStorage mBookStorage;
	Cart mCart;
	Customer mCustomer; // model에서 import해서 가져오기
	Admin mAdmin;
	
	String[] menuList = { 
			"0. 종료",
			"1. 도서 정보 보기",
			"2. 장바구니 보기", 
			"3. 장바구니에 도서 추가",
			"4. 장바구니 도서 삭제",
			"5. 장바구니 도서 수량 변경",
			"6. 장바구니 비우기",
			"7. 주문",
			"8. 관리자 모드"
	};
	
	String[] adminMenuList = {
		"0. 종료",
		"1. 도서 정보 추가",
		"2. 도서 정보 삭제",
		"3. 도서 정보 보기"	,
		"4. 도서 정보 파일 저장"
	};
	
	// Controller 생성자
	public BookMarketController(BookStorage bookStorage, Cart cart, ConsoleView view) {
		this.view = view;	
		this.mBookStorage = bookStorage;
		this.mCart = cart;
		mAdmin = new Admin();
	}
	
	public void start() {
		view.displaywelcome();
	    registerCustomerInfo();
	    
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
			case 8 -> adminMode();
			case 0 -> end();
			default -> view.showMessage("잘못된 메뉴 번호입니다.");
			}
		} while(menu != 0);
	}
	
	

	private void adminMode() {
		
		//관리자 인증 (id, password 확인)
		if (!authenticateAdmin()) {
			view.showMessage("관리자 모드로 실행 불가");
		}
		// 관리자 모드 진입 -> 도서 추가, 도서 삭제, 도서 정보 파일 저장
			// 관리자 모드일 떄의 메뉴 출력
		    // 메뉴 선택하면 해당 기능 실행
		int menu;
		
		do {
			menu = view.selectMenu(adminMenuList);
			
			switch (menu) {
			case 1 -> addBook2Storage(); 
			case 2 -> deleteBookInStorage();
			case 3 -> viewBookInfo();
			case 4 -> saveBookList2File();
			case 0 -> adminEnd();
			default -> view.showMessage("잘못된 메뉴 번호입니다.");
			}
		} while(menu != 0);
	}
		
	private void deleteBookInStorage() {
		if (!mBookStorage.isEmpty()) {
			 view.showMessage("책 창고에 책이 없습니다.");
			 return;
		} 
		
			// 책 창고 보여주기
			viewBookInfo();
			//도서 ID 입력 받기
			int bookId = view.selectBookId(mBookStorage);
			if (view.askConfirm(">> 해당 도서를 삭제하려면 yes를 입력하세요 : ", "yes")) {
			   	// 해당 도서 ID의 cartItem 삭제
				mBookStorage.deleteItem(bookId);
				view.showMessage(">> 해당 도서를 삭제했습니다.");
				}
		}

	
	private void registerCustomerInfo() {
		mCustomer = new Customer();
		view.inputCustomerInfo(mCustomer);
	}
	
	

	private void adminEnd() {
		view.showMessage("관리자 모드 종료. \n");
	}

	private void saveBookList2File() {
		if (mBookStorage.isSaved()) { // BookStorage의 boolean isSaved가 true라면 
			view.showMessage("책 정보 파일과 동일합니다."); // 저장 없이 메세지 출력
		} else {
			mBookStorage.saveBookList2File(); // false일시 BookStroage에서 책 정보를 저장하는 메서드 호출
			view.showMessage("책 정보를 저장하였습니다.");
		}
	}
	
	// Book Storage에 도서 추가
		private void addBook2Storage() {
			view.showMessage("새로운 책을 추가합니다.");
			
			// 책정보로 Book 인스턴스 만들어서 mBookStorage에 추가
			mBookStorage.addBook(view.inputString("책 제목 : "),
					view.inputString("저자 : "), view.inputString("출판사 : "),
					view.readNumber("가격 : "));

		}

	private boolean authenticateAdmin() {
		//관리자 인증 (id, password 확인)
		view.showMessage("관리자 모드 진입을 위한 인증이 필요합니다 : ");
		String id = view.inputString("관리자 ID : ");
		String pwd = view.inputString("관리자 패스워드 : ");
		return mAdmin.login(id, pwd);
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
		view.inputDeliveryInfo(mCustomer);
	}

	private void displayOrderInfo() {
		view.displayOrder(mCart, mCustomer);
		view.displayDeliveryInfo(mCustomer);
		
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
	
	//장바구니 내용을 초기화
	private void resetCart() {
		view.displayCart(mCart); // 장바구니에 담긴 목록들 출력
		
		if (!mCart.isEmpty()) { // 장바구니가 비어있지 않다면 
			if (view.askConfirm("장바구니를 비우시려면 yes를 입력하세요", "yes")) { // 장바구니를 비울건지 여부를 묻고 입력하는 메서드 호출
				mCart.resetCart(); // 장바구니 초기화 후 메세지 출력
				view.showMessage(">> 장바구니를 비웠습니다.");
			}
		}
		
	}
	
	//장바구니에 도서 추가
	private void addBook2Cart() {
		view.displayBookInfo(mBookStorage); // 도서 목록 및 정보를 출력하는 메서드 호출
		int bookId = view.selectBookId(mBookStorage); // BookId를 받아오는 메서드 호출
		mCart.addItem(mBookStorage.getBookById(bookId)); // 장바구니에 해당 bookId를 지닌 책을 가져와서 담기 
		view.showMessage("장바구니에 도서를 추가하였습니다."); //메세지 출력
	}

	private void viewCart() {
		view.displayCart(mCart); // 현재 장바구니에 담긴 책들 목록 출력
	}

	private void viewBookInfo() {
		view.displayBookInfo(mBookStorage); // 도서 목록에 있는 책들의 목록 출력
		}
		

}
