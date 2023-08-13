import java.util.Date;

class Book implements Comparable<Book> {
	String title;
	String author;
	String isbn;
	boolean isBorrowed;
	String borrowedby;
	Date borrowedDate;
	Date dueDate;
	
	public int compareTo(Book other) {
		return this.title.compareTo(other.title);
	}	
}