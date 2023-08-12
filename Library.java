import java.util.*;

public class Library {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Calendar cal = Calendar.getInstance();
		
		List<User> userList = new ArrayList<>();
		List<Book> bookList = new ArrayList<>();
		
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
								borrowBook(bookList, user, sc, cal);
								System.out.println("-----------------------------------------------------\n\n\n");
								break;
								
							case 5:
								returnBook(bookList, sc, cal);
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
						}
					}
					else System.out.println("User id \""+existingUserId+"\" doesn't exists");
				}
				break;
				
			default:
				System.out.println("Invalid response!");
				break;
			}
		}
	}
	
	public static void createNewUser(List<User> userList, Scanner sc) {
		User userObj = new User();
		userObj.setId();
		
		System.out.println("Enter your name");
		String name = "a";//sc.nextLine();
		userObj.setName(name);
		
		System.out.println("Enter you DOB (DD-MM-YYYY)");
		String dob = "25-09-2001";//sc.nextLine();
		userObj.setDob(dob);
		
		System.out.println("Select your Role:\n1. User\n2. Admin");
		int role = sc.nextInt();
		if (role==1) {
			userObj.setRole("User");
		} else if (role==2) {
			userObj.setRole("Admin");
		} else {
			System.out.println("Invalid role choice");
		}
		
		userList.add(userObj);		
		System.out.println("New User Added!\n\nID: "+userObj.getId()+"\nName: "+userObj.getName()+
				"\nRole: "+userObj.getRole()+"\n-----------------------------------------------------------\n\n\n");
			
	}
	
	public static void addBook(List<Book> bookList, User user, Scanner sc) {
		if (user.getRole().equals("Admin")) {
			Book bookObj = new Book();
			
			System.out.println("Enter the title: ");
			bookObj.title = "alice";//sc.nextLine();	
			
			System.out.println("Enter the author's name: ");
			bookObj.author = "Lewis Caroll";//sc.nextLine();
			
			System.out.println("Enter the ISBN No.");
			bookObj.isbn = "007";//sc.nextLine();
			
			bookList.add(bookObj);
			
			System.out.println("Book added!\nBook name: "+bookObj.title+"\nBook Author: "+bookObj.author+
					"\nISBN: "+bookObj.isbn);
		} else System.out.println("You need to be an admin to perform this operation");
		
	}
	
	public static void deleteBook(List<Book> bookList, User user, Scanner sc) {
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
	
	public static Book findBook(List<Book> bookList, Scanner sc) {
		Book book = new Book();
		System.out.println("Select search criteria\n1. Title\n2. Author");
		int criteria = sc.nextInt();
		sc.nextLine();
		
		switch (criteria) {
		case 1:
			System.out.println("Enter the title: ");
			String title = sc.nextLine();			
			
			for (Book x : bookList) {
				if (title.toLowerCase().contains(x.title)) {
					System.out.println("Book found!");
					book = x;
					System.out.println("Book name: "+book.title+"\nAuthor: "+book.author);
				} else System.out.println("Book not found\n\n");
			}
			return book;
			
		case 2:
			System.out.println("Enter the Author's name: ");
			String authorName = sc.nextLine();
			
			for (Book x : bookList) {
				if (authorName.equalsIgnoreCase(x.author)) {
					System.out.println("Author found!");
					book = x;
					System.out.println("Book name: "+book.title+"\nAuthor: "+book.author);
				} else System.out.println("Author not found\n\n");
			}
			return book;
			
		default:
			System.out.println("Invalid choice!\n\n");
			return book;
		}		
	}
	
	public static void displayBooks(List<Book> bookList) {
		System.out.println("Title               Author's name                Status\n");
		for(Book x : bookList) {
			System.out.print(x.title);
			for (int i=0; i<20-x.title.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(x.author);
			for (int i=0; i<29-x.author.length(); i++) {
				System.out.print(" ");
			}
			System.out.print(x.isBorrowed ? "Not available" : "Available\n");
			System.out.println("-----------------------------------------------------\n");
		}
	}
	
	public static void borrowBook(List<Book> bookList, User user, Scanner sc, Calendar cal) {
		Book book = findBook(bookList, sc);
		if (!book.isBorrowed) {
			book.isBorrowed = true;
			book.borrowedby = user.getName();
			book.borrowedDate = cal.getTime();
			cal.add(Calendar.DAY_OF_MONTH, 15);
			book.dueDate = cal.getTime();
			System.out.println("Book borrowed\nUser name: "+book.borrowedby+"\nBook name: "+book.title+
					"\nBorrowed On: "+book.borrowedDate+"\nDUE DATE: "+book.dueDate+"\n\n");
		} else System.out.println("Book not available\n\n");
	}
	
	public static void returnBook(List<Book> bookList, Scanner sc, Calendar cal) {
		Book book = findBook(bookList, sc);
		int price = 2;
		int fine = 1;
		if (book.isBorrowed) {
			System.out.println("User name: "+book.borrowedby+"\nBook name: "+book.title+
					"\nBorrowed On: "+book.borrowedDate+"\nDUE DATE: "+book.dueDate+
					"\nPrice for 1 day is Rs.2\nFine after due date is Rs.1 per day\n");
			
			Date today = cal.getTime();
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