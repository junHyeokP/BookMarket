package noOOP;

import java.util.Scanner;

public class junHyeokBookMarket {
     
	 static final int NUM = 4;
	
     public static void main(String[] args) {
		displayWelcome();
		//불리언 false상태로 초기화
		boolean quit = false;
		//0을 입력하여 quit가 true가 되기 전까지 계속 출력
		while (!quit) {
			String menu = displayGetMenu();
			switch (menu) {
			case "1":
				menuBookList();
				break;
			case "2":
				menuCartList();
				break;
			case "3":
				menuAddCartItem();
				break;
			case "4":
				menuClearCart();
				break;
			case "0" :
				menuExit();
				quit = true;
				break;
			default :
				menuWrongNumber();;
			}
		}

	}
	
	static void displayWelcome() {
		String welcome = "*****************************************\n"
				       + "*    Welcome to JunhyeokP Book Market    *\n"
				       + "*****************************************\n";
		System.out.print(welcome);
	}
	static void menuBookList() {
		Scanner sc = new Scanner(System.in);
		String menuBookList =">> 도서 목록\r\n"
		        + "------------------------------------------------------------------------\r\n"
		        + "ID2401, 쉽게 배우는 자바 프로그래밍 2판, 우종정, 한빛아카데미, 20,000원, \r\n"
				+ "ID2402, 코딩 자율학습 HTML+CSS+자바스크립트, 김기수, 길벗, 30,000원, \r\n"
				+ "ID2403, Do It! 자료구조와 함께 배우는 알고리즘 입문 - 자바편, 보요시바타, 이지스퍼블리싱, 25,000원\r\n"
				+ "------------------------------------------------------------------------\n"
				+ "";
		System.out.println(menuBookList);
		 
	    
	}	
	
	static void menuCartList() {

		if (isNot(cartList)) {
			System.out.println(">> 장바구니가 비어 있습니다.");
		}
		
		else if (!isNot(cartList)) {
			System.out.print(">> 장바구니 보기 \n"
							 + "------------------------------------------------------------------------\n");
			                            if (cartList[0] != 0) {
										//cartList배열에 숫자가 있는 목록들을 출력
			             				System.out.print(cartList[0] + "권, ");
										addCart(0);
			                            }
										if(cartList[1] > 0) {
										System.out.print(cartList[1] + "권, ");
										addCart(1);
										}
										if(cartList[2] > 0) {
										System.out.print(cartList[2] + "권, ");
										addCart(2);
										}
					
			System.out.println("------------------------------------------------------------------------");
			}
	}
	//배열을 0으로 초기화 시키는 메소드
	static boolean isNot(int[] cartList) {
		// 배열의 인덱스는 0부터 시작하므로 1을 뺌
		int[] remain = new int [cartList.length-1];
		
		// remain에 넣을 배열 인덱스
		int r = 0;
		
		// 다른 배열에 숫자가 남아있을 수도 있으므로 for문으로 차례대로 비교
			for (int n = 0; n < cartList.length; n++) {
				if (cartList[n] != 0) {
		
					//한번 넣고 그 뒤에 "++" 증감 
				 remain[r++] = cartList[n];
									  }
			
				// 비우기버튼을 눌렀다면 -1 * -1 = 1이므로 true반환
				else if (-1 * cartList[n] >= 1) {
					// 여전히 -1일테니 다시 0으로
					cartList[0] = 0;
					return true;
				}	
			}
			if (remain[0] >= 1) {
				return false;
			}
			return true;
}
	
	static void menuAddCartItem() {
		 Scanner sc = new Scanner(System.in);
		 	if (isNot(cartList)) {
		 	  cartList[0] = 0;	
		 	}
		 		
			menuBookList();
		   System.out.print("추가할 도서 ID 입력 : ");
		   String addID = sc.next();
		   int n = 0;
		   switch (addID) {
		   case "ID2401" : 
			   n = 0;
			   System.out.print(">> 장바구니 추가 : ");
			   addCart(n);
			   break;
		   case "ID2402" : 
			   n = 1;
			   System.out.print(">> 장바구니 추가 : ");
			   addCart(n);
			   break;
		   case "ID2403" : 
			   n = 2;
			   System.out.print(">> 장바구니 추가 : ");
			   addCart(n);
			   break;
		   default : System.out.println(">> 없는 ID입니다, 도서 목록에 있는 ID를 입력하세요.");
		   menuAddCartItem();
		   break;
		   }
		 
	}
	
	
	static void addCart(int n) {
		// [n]번째 열에 있는 [i]행을 출력
		for (int i = 0; i < 5; i++) {
			System.out.print(bookList[n][i] + ", ");
		}
		
		System.out.println();
		
		cartList[n] += 1;
		
	}
	static String[][] bookList = {  { "ID2401", "쉽게 배우는 자바 프로그래밍 2판", "우종정", "한빛아카데미", "20,000원" },
							     	{ "ID2402", "코딩 자율학습 HTML+CSS+자바스크립트", "김기수", "길벗", "30,000원" },
							        { "ID2403", "Do It! 자료구조와 함께 배우는 알고리즘 입문 - 자바편", "보요시바타", "이지스퍼블리싱", "25,000원" } };


static int[] cartList = {0, 0, 0};

static int numCartItem = 0;

	static void menuClearCart() {
		System.out.println(">> 장바구니에 있는 아이템을 모두 삭제하였습니다.");
		
		// 배열에 있는 숫자들을 0으로 변환
		for (int i = 0; i < cartList.length; i++) {
			cartList[i] = 0;
		}
		// 첫번째 배열을 -1로 변환
		    cartList[0] = -1;
	}
	
	static void menuExit() {
		System.out.println(">> 종료합니다.");
	}
	static void menuWrongNumber() {
		System.out.println("없는 메뉴입니다, 0번 부터 " + NUM + "번 메뉴 중에서 선택하세요.");
	}
	static String displayGetMenu() {
		String chose = "=========================================\r\n"
				+ "1. 도서 목록 보기\r\n"
				+ "2. 장바구니 보기\r\n"
				+ "3. 장바구니에 도서 추가\r\n"
				+ "4. 장바구니 비우기\r\n"
				+ "0. 종료\r\n"
				+ "=========================================\n";
		System.out.print(chose);
		System.out.print(">> 메뉴 선택 : ");
	    Scanner sc = new Scanner(System.in);
	    return sc.next();
		
	}
}
