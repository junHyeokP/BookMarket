package bookmarket.model;

import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BookStorage {
	ArrayList<Book> bookList = new ArrayList<>(); // Book을 제네릭으로 지정하여 Book을 담는 ArrayList bookList생성
	final int MAX_QUANTITY = 10; // 최대 갯수 10개
	private String bookFilename = "booklist.txt"; // 책 정보들이 담긴 텍스트 파일의 이름을 담음
	private int lastId; // 마지막에 있는 책 Id
	private boolean isSaved; // 저장이 되는지 확인하는 변수
	
	public BookStorage() throws IOException { // BookStorage 생성자
		loadBookListFromFile(); // 파일에서 책 정보들을 받아오는 역할의 메서드를 호출
		generateLastId(); // 배열 마지막에 있는 책의 Id를 추출
		isSaved = true; // 저장이 완료되었다는 표시로 true로 바꿈
	}
	
	
	private void generateLastId() {
		lastId = 0; // 0으로 초기화
		for (Book book : bookList) { // 배열에서 book을 꺼내서
			int id = book.getBookId(); // 해당 book 변수의 bookId를 추출
				if (id > lastId) lastId = id; // 만약 마지막으로 저장되었던 lastId보다 크다면 그 id를 마지막 id로 지정 
			}
		}	

	private void loadBookListFromFile()throws IOException {
		FileReader fr; // 파일을 읽어주는 기능을 가진 인스턴스 생성
		try {
			fr = new FileReader(bookFilename); // 로컬 파일 내 booklist.txt라는 파일을 찾아 읽어주는 인스턴스 fr 생성
			BufferedReader bf = new BufferedReader(fr); // 하위 클래스인 BufferedReader 기능을 참조하는 bf 인스턴스를 생성하여 fr이 파일을 읽는 효울을 높임
			String idStr; // 파일안에 있는 Id를 읽어들일 문자열 변수 생성
			while ((idStr = bf.readLine()) != null && !idStr.equals("")) { // 읽은 Id가 null이 아닌동안 && "" 공백이 아닌 동안 반복
				int id = Integer.parseInt(idStr); // 위에서 읽고 저장된 변수의 값을 새로 id 변수에 정수형으로 변환하여 저장
				String title = bf.readLine(); // 텍스트 파일 내 이름을 읽어서 title 변수에 넣음
				String author = bf.readLine(); 
				String publisher = bf.readLine(); 
				int price = Integer.parseInt(bf.readLine()); // 가격도 정수형으로 변환하여 저장
				bookList.add(new Book(id, title, author, publisher, price)); // 이후 결과물들을 bookList에 저장
			}
		
		} catch (FileNotFoundException | NumberFormatException e) { // 파일이 발견되지 않거나 읽어들이는 도중 알맞지않은 데이터형이 들어왔을시
			System.out.println(e.getMessage()); // 오류 메세지 출력
		}
		
	}


	public int getNumBooks() { // bookList의 크기를 반환하는 메서드
		return bookList.size();
	}
	
	public String getBookInfo(int i) { // 해당 인덱스에 해당하는 책 정보를 반환하는 메서드
		return bookList.get(i).toString();
	}

	public boolean isValidBook(int bookId) { // 매개변수로 들어온 bookId를 bookList에 있는 book의 bookId와 비교하여 동일한 Id가 있는지 확인하는 메서드
		for (Book book : bookList) {
			if (book.getBookId() == bookId) {
				return true;
			}
		}
		return false;
	}

	public Book getBookById(int bookId) { // 매개변수로 들어온 bookId와 동일한 bookId를 지닌 책을 반환하는 메서드
		for (Book book : bookList) {
			if (book.getBookId() == bookId) {
				return book;
			}
		}
		return null; // 없을시 null 반환
	}

	public int getMaxQuantity() { // 책의 최대 갯수를 반환하는 메서드
		return this.MAX_QUANTITY;
	}
	
	public boolean isEmpty() { // bookList안에 저장된 book들이 없는지 확인하는 메서드
		return bookList.size() == 0;
	}


	public void deleteItem(int bookId) { // 해당 bookId에 해당하는 book을 bookList에서 제거하는 메서드
		bookList.remove(getBookById(bookId)); // ArrayList에 내장된 기능인 remove를 사용하여 배열속 요소를 제거
		
	}

	public void addBook(String title, String author, String Publisher, int price) { 
		// 책의 이름과 가격등을 매개변수로 전달받아 새로운 book 인스턴스를 생성, bookList에 넣는 메서드
		Book book = new Book(++lastId, title, author, Publisher, price); // lastId 증감
		bookList.add(book);
		isSaved = false; // 새 변경사항이 생겼으므로 저장 전까지 false로 변경
	}
	public boolean isSaved() { // 저장이 되어있는지 여부를 확인하는 메서드
		return isSaved(); // isSaved 변수 반환
	}


	public void saveBookList2File() {
		
		try { // 다음의 내용들을 시도
			FileWriter fw = new FileWriter(bookFilename); // bookFilename에 담긴 이름의 파일을 적는 FileWriter fw 생성
			for (Book book : bookList) { // bookList에 담긴 book들을 하나 씩 불러와서 이름과 가격등을 한줄 씩 적기
				fw.write(book.getBookId() + "\n"); // Id 추출후 한줄 넘김
				fw.write(book.getTitle() + "\n");
				fw.write(book.getAuthor() + "\n");
				fw.write(book.getPublisher() + "\n");
				fw.write(book.getPrice() + "\n");
			}
			fw.close(); // 이후 버퍼에 남아있는 내역이 없도록 FileWriter를 종료
			isSaved = true; // 저장이 되었다는 것을 확인하도록 true로 바꿔줌
		} catch (IOException e) { // 위의 코드 블럭 실행 중 입출력 예외 오류를 탐지했을시
			e.printStackTrace(); // 오류 메세지 출력
		} 
	}
}
