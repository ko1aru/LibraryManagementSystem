import java.util.*;

public class Library {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		List<User> userList = new ArrayList<>();
		Set<Book> bookList = new TreeSet<>();
		
		initializeBooks(bookList);
		
		while (true) {
			System.out.println("Are you a New User or a Existing User?\n1. New user\n2. Existing user");
			int userType = sc.nextInt();
			sc.nextLine();
			
			switch (userType) {
			case 1:
				createNewUser(userList, sc);
				break;
				
			case 2:
				System.out.println("Enter your User Id: ");
				int existingUserId = sc.nextInt();
				
				boolean flag = true;
				User user = null;
				for (User n : userList) {
					if (n.getId()==existingUserId) {
						user = n;
						System.out.println("Welcome "+user.getName()+"!\n");
						while (flag) {
							System.out.println("----------------Select what you want to do----------------");
							System.out.println("1. Add a book\n2. Delete a book\n3. Display all books\n"
									+ "4. Borrow a book\n5. Return a book\n6. Exit");
							try {
								int option = sc.nextInt();
								sc.nextLine();
								
								switch (option) {
								case 1:
									addBook(bookList, user, sc);
									System.out.println("\n-----------------------------------------------------\n\n\n");
									break;
									
								case 2:
									deleteBook(bookList, user, sc);
									System.out.println("-----------------------------------------------------\n\n\n");
									break;
									
								case 3:
									displayBooks(bookList);
									System.out.println("-----------------------------------------------------\n\n\n");
									break;
									
								case 4:
									borrowBook(bookList, user, sc);
									System.out.println("-----------------------------------------------------\n\n\n");
									break;
									
								case 5:
									returnBook(bookList, sc);
									System.out.println("-----------------------------------------------------\n\n\n");
									break;
									
								case 6:
									flag = false;
									System.out.println("-----------------------------------------------------\n\n\n");
									break;
									
								default:
									System.out.println("Invalid option\n-----------------------------------------------------\n\n\n");
									break;
								}
							} catch (InputMismatchException e1) {
								System.out.println("Please enter a valid response (1-6)\n\n");
								sc.nextLine();
							}
							
						}
					}
					else System.out.println("User id \""+existingUserId+"\" doesn't exists\n\n");
				}
				break;
				
			default:
				System.out.println("Invalid response!\n\n");
				break;
			}
		}
	}
	
	public static void initializeBooks(Set<Book> bookList) {
	    String[] titles = { "The Great Gatsby", "To Kill a Mockingbird", "1984", "Pride and Prejudice",
	    	"The Hobbit", "Harry Potter and the Sorcerer's Stone", "The Catcher in the Rye",
	    	"The Lord of the Rings", "Animal Farm", "Brave New World" };

	    String[] authors = { "F. Scott Fitzgerald", "Harper Lee", "George Orwell", "Jane Austen",
	    	"J.R.R. Tolkien", "J.K. Rowling", "J.D. Salinger", "J.R.R. Tolkien", "George Orwell",
	        "Aldous Huxley" };

	    String[] isbns = {"978-0743273565", "978-0061120084", "978-0451524935", "978-0141439518",
	        "978-0618002214", "978-0590353427", "978-0316769488", "978-0618640157", "978-0451524935",
	        "978-0060850524" };

	    for (int i = 0; i < titles.length; i++) {
	        Book book = new Book();
	        book.title = titles[i];
	        book.author = authors[i];
	        book.isbn = isbns[i];
	        bookList.add(book);
	    }
	}
	
	public static void createNewUser(List<User> userList, Scanner sc) {
		User userObj = new User();
		userObj.setId();
		
		System.out.println("Enter your name");
		String name = sc.nextLine();
		userObj.setName(name);
		
		System.out.println("Enter you DOB (DD-MM-YYYY)");
		String dob = sc.nextLine();
		userObj.setDob(dob);
		
		boolean isValidRole = false;
		while (!isValidRole) {
			try {
				System.out.println("Select your Role:\n1. User\n2. Admin");
				int role = sc.nextInt();
				if (role==1) {
					userObj.setRole("User");
					isValidRole = true;
				} else if (role==2) {
					userObj.setRole("Admin");
					isValidRole = true;
				} else {
					System.out.println("Invalid role choice");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter '1' or '2'\n");
				sc.nextLine();
			}
		}
		
		userList.add(userObj);		
		System.out.println("New User Added!\n\nID: "+userObj.getId()+"\nName: "+userObj.getName()+
				"\nRole: "+userObj.getRole()+"\n-----------------------------------------------------------\n\n\n");
			
	}
	
	public static void addBook(Set<Book> bookList, User user, Scanner sc) {
		if (user.getRole().equals("Admin")) {
			Book bookObj = new Book();
			
			System.out.println("Enter the title: ");
			bookObj.title = sc.nextLine();	
			
			System.out.println("Enter the author's name: ");
			bookObj.author = sc.nextLine();
			
			System.out.println("Enter the ISBN No.");
			bookObj.isbn = sc.nextLine();
			
			bookList.add(bookObj);
			
			System.out.println("\nBook added!\nBook name: "+bookObj.title+"\nBook Author: "+bookObj.author+
					"\nISBN: "+bookObj.isbn);
		} else System.out.println("You need to be an admin to perform this operation");		
	}
	
	public static void deleteBook(Set<Book> bookList, User user, Scanner sc) {
		if (user.getRole().equals("Admin")) {
			Iterator<Book> it = bookList.iterator();
			boolean found = false;
			System.out.println("Enter the title: ");
			String bookName = sc.nextLine();
			
			while (it.hasNext()) {
				if (bookName.equalsIgnoreCase(it.next().title)) {
					found = true;
					it.remove();
					System.out.println("Book removed successfully");
					break;
				}
			}
			if (!found) System.out.println("Book not found");
		} else System.out.println("You need to be an admin to perform this operation");
	}
	
	public static Book findBook(Set<Book> bookList, Scanner sc) {
		System.out.println("Select search criteria\n1. Title\n2. Author");
		try {
			int criteria = sc.nextInt();
			sc.nextLine();
			
			switch (criteria) {
			case 1:
				System.out.println("Enter the title: ");
				String title = sc.nextLine();			
				
				for (Book book : bookList) {
					if (title.equalsIgnoreCase(book.title)) {
						System.out.println("\nBook found!");
						System.out.println("\nBook name: "+book.title+"\nAuthor: "+book.author+"\n");
						return book;
					}
				}
				System.out.println("Book not found\n");
				return null;
					
			case 2:
				System.out.println("Enter the Author's name: ");
				String authorName = sc.nextLine();
				
				for (Book book : bookList) {
					if (authorName.equalsIgnoreCase(book.author)) {
						System.out.println("Author found!");
						System.out.println("Book name: "+book.title+"\nAuthor: "+book.author);
						return book;
					}
				}
				System.out.println("Author not found\n");
				return null;
				
			default:
				System.out.println("Invalid choice!\n\n");
				return null;
			}
		} catch (InputMismatchException e1) {
			System.out.println("Please enter '1' or '2'");
			sc.nextLine();
			return findBook(bookList, sc);
		}
	}
	
	public static void displayBooks(Set<Book> bookList) {
		System.out.println("Title                      Author's name                Status\n");
		for(Book x : bookList) {
			System.out.print(x.title);
			for (int i=0; i<27-x.title.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(x.author);
			for (int i=0; i<29-x.author.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(x.isBorrowed ? "Not available\n" : "Available\n");
		}
	}
	
	public static void borrowBook(Set<Book> bookList, User user, Scanner sc) {
		Calendar cal = Calendar.getInstance();
		Book book = findBook(bookList, sc);
		if (book != null) {
			if (!book.isBorrowed) {
				book.isBorrowed = true;
				book.borrowedby = user.getName();
				book.borrowedDate = cal.getTime();
				cal.add(Calendar.DAY_OF_MONTH, 15);
				book.dueDate = cal.getTime();
				System.out.println("Book borrowed\nUser name: "+book.borrowedby+"\nBook name: "+book.title+
						"\nBorrowed On: "+book.borrowedDate+"\nDUE DATE: "+book.dueDate+"\n\n");
			} else System.out.println("Book not available\n\n");
		} else System.out.println("Please enter a valid book name from the list");
	}
	
	public static void returnBook(Set<Book> bookList, Scanner sc) {
		Calendar cal = Calendar.getInstance();
		Book book = findBook(bookList, sc);
		int price = 2;
		int fine = 1;
		if (book.isBorrowed) {
			System.out.println("User name: "+book.borrowedby+"\nBook name: "+book.title+
					"\nBorrowed On: "+book.borrowedDate+"\nDUE DATE: "+book.dueDate+
					"\nPrice for 1 day is Rs.2\nFine after due date is Rs.1 per day\n");
			
			Date today = cal.getTime();
			System.out.println("Current time and day: "+today);
			long diff = (today.getTime() - book.borrowedDate.getTime()) / (24*60*60*1000);
			
			if(today.compareTo(book.dueDate)<=0) {
				System.out.println("Price to pay: "+price*(int)diff);
			} else {
				int toPay = price * (int) diff;
				long delay = (today.getTime() - book.dueDate.getTime()) / (24*60*60*1000);
				toPay += fine * (int) delay;
				System.out.println("Price to pay: "+toPay);
			}
			
			book.isBorrowed = false;
			book.borrowedby = null;
			book.borrowedDate = null;
			book.dueDate = null;
		} else System.out.println("Book cannot be returned");
	}
	
}