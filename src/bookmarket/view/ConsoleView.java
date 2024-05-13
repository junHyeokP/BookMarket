package bookmarket.view;

import java.util.Scanner;

import bookmarket.model.BookStorage;
import bookmarket.model.Cart;

public class ConsoleView {

	public void displaywelcome() {
		
		System.out.println("*****************************************\n"
				        + "*    Welcome to JunhyeokP Book Market   *\n"
				       + "*****************************************\n");
		
	}

	public int selectMenu(String[] menuList) {
		
		displayMenu(menuList);
		
		Scanner sc = new Scanner(System.in);
		int menu;
		do {
			System.out.print(">> 메뉴 선택 : ");
			menu = sc.nextInt();
			if (menu < 0 || menu >= menuList.length)
				System.out.println("0부터 " + (menuList.length - 1) + "까지의 숫자를 입력해주세요.");
		} while(menu < 0 || menu >= menuList.length);
		return menu;
	}

	private void displayMenu(String[] menuList) {
		//메뉴 요청을 받아 실행
		System.out.println("=====================================");
		for(int i = 1; i < menuList.length; i++) {
			System.out.println(menuList[i]);
		}
		System.out.println(menuList[0]);
		System.out.println("=====================================");
	}

	public void displayBookInfo(BookStorage mBookStorage) {
		// 도서 정보 입출력 요청을 받아 출력
		for (int i = 0; i < mBookStorage.getNumBooks(); i++) {
			String bookInfo = mBookStorage.getBookInfo(i);
			System.out.println(bookInfo);
	}

 }

	public void showMessage(String message) {
		System.out.println(message);
		
	}

	public void displayCart(Cart mCart) {
		if (mCart.isEmpty()) {
			System.out.println("장바구니가 비어있습니다.");
			return;
		}
		
		for (int i = 0; i < mCart.getNumItems(); i++) {
			System.out.println(mCart.getItemInfo());	
		}
	}

	public boolean askConfirm(String message, String yes) {
		
		System.out.println(message);
		
		Scanner sc = new Scanner(System.in);
		if (sc.nextLine().equals(yes)) return true;
		
		return false;
		
	}

	public int selectBookId(BookStorage bookStorage) {
		Scanner sc = new Scanner(System.in);
		
		int bookId;
		boolean result;
		do {
			System.out.print("추가하고 싶은 도서의 ID를 입력해주세요 : ");
			bookId = sc.nextInt();
			result = bookStorage.isValidBook(bookId);
			if (!result)
				System.out.println("잘못된 도서 ID입니다.");
		} while(!result);
		
		return bookId;
	}
}