package bookmarket.model;

public class Book {
	int bookId;
	String title;
	String author;
	String publisher;
	int price;
	
	public Book(int bookId, String title, String author, String publisher, int price) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
	}
	public int getBookId() {
		return bookId;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	public int getPrice() {
		return price;
	}
	
	//toString 기능 오버라이딩
	public String toString() {
		return bookId + ", " + title + ", " + author + ", " + publisher 
				+ ", " + price + "원";
	}
	

	
}
