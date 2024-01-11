package persistence;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Author;
import entity.Book;
import entity.Item;
import entity.Loan;
import entity.Student;

public class BookDataAccess {

	public static boolean createBook(String title, String description, String location, BigDecimal dailyPrice,
			int numberPages, String publisher, Date publicationDate, List<Author> authors) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class)
				.addAnnotatedClass(Author.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		boolean flag = false;
		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			Book book = new Book(true, title, description, location, dailyPrice, numberPages, publisher,
					publicationDate);

			book.setAuthors(authors.stream().map(author -> {
				author.addBook(book);
				return author;
			}).collect(Collectors.toList()));

			session.save(book);

			session.getTransaction().commit();

			flag = true;
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return flag;
	}

	public static Book loadBook(int bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();

		Book book = null;

		try {

			session.beginTransaction();

			book = session.get(Book.class, bookId);

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return book;
	}

//	public static Map<Integer, Author> getAuthorIds(int code) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
//				.addAnnotatedClass(Book.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<Integer> authorIds = new ArrayList<Integer>();
//
//		try {
//
//			session.beginTransaction();
//
//			List<Author> authors = session.get(Book.class, code).getAuthors();
//
//			if (authors != null) {
//				for (Author author : authors) {
//					authorIds.add(author.getAuthorId());
//				}
//			}
//
//			session.getTransaction().commit();
//
//		} catch (Exception e) {
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//		} finally {
//			factory.close();
//
//		}
//		return authorIds;
//	}

	public static List<Book> loadAllBooks(String orderBy) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		List<Book> books = null;
		Query query = null;

		try {

			session.beginTransaction();

			if (orderBy.equals("none")) {
				query = session.createQuery("select b from Book as b");
			} else if (orderBy.equals("titleAZ")) {
				query = session.createQuery("select b from Book as b order by b.title asc");
			} else if (orderBy.equals("titleZA")) {
				query = session.createQuery("select b from Book as b order by b.title desc");
			} else if(orderBy.equals("priceHL"))
			{	query = session.createQuery("select b from Book as b order by b.dailyPrice desc");
			} else if(orderBy.equals("priceLH")){
				query = session.createQuery("select b from Book as b order by b.dailyPrice asc");
			} else if(orderBy.equals("titleAZ AND priceHL")) {
				query = session.createQuery("select b from Book as b order by b.title asc, b.dailyPrice desc");
			} else if(orderBy.equals("titleZA AND priceLH")) {
				query = session.createQuery("select b from Book as b order by b.title desc, b.dailyPrice asc");
			} else if(orderBy.equals("titleAZ AND priceLH")) {
				query = session.createQuery("select b from Book as b order by b.title asc, b.dailyPrice asc");
			} else if(orderBy.equals("titleZA AND priceHL")) {
				query = session.createQuery("select b from Book as b order by b.title desc, b.dailyPrice desc");
			}

			books = query.list();

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return books;
	}

	public static Map<Integer, String> getAuthorsOfBook(int code) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();

		Map<Integer, String> authorsMap = null;
		List<Author> authorsList = null;

		try {

			session.beginTransaction();

			authorsList = session.get(Book.class, code).getAuthors();

			if (authorsList != null) {
				for (Author author : authorsList) {
					authorsMap.put(author.getAuthorId(), author.getFirstName() + author.getLastName());
				}
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return authorsMap;
	}

//	public static Map<Integer, String> loadAllAuthors() {
//
//		Map<Integer, String> authorsMap = new HashMap<Integer, String>();
//		List<Author> authorsList = null;
//
//		try {
//
//			authorsList = AuthorDataAccess.loadAllAuthors("lastName");
//
//			if (authorsList != null) {
//				for (Author author : authorsList) {
//					authorsMap.put(author.getAuthorId(), author.getLastName() + ", " + author.getFirstName());
//				}
//			}
//
//		} catch (Exception e) {
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//		}
//		return authorsMap;
//	}

	public static boolean updateBook(int bookId, String updatedTitle, String updatedDescription, String updatedLocation,
			BigDecimal updatedDailyPrice, int updatedNumberOfPages, String updatedPublisher, Date updatedPublicationDate,
			List<Author> updatedAuthors) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		boolean flag = false;

		try {
			session.beginTransaction();

			Book book = session.get(Book.class, bookId);

			book.setAuthors(updatedAuthors.stream().map(author -> {
				author.addBook(book);
				return author;
			}).collect(Collectors.toList()));
			
			book.setTitle(updatedTitle);
			book.setDescription(updatedDescription);
			book.setLocation(updatedLocation);
			book.setDailyPrice(updatedDailyPrice);
			book.setNumberPages(updatedNumberOfPages);
			book.setPublisher(updatedPublisher);
			book.setPublicationDate(updatedPublicationDate);

			session.saveOrUpdate(book);

			session.getTransaction().commit();

			flag = true;
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return flag;
	}

	public static Book deleteBook(int bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Book book = null;

		try {

			session.beginTransaction();

			book = session.get(Book.class, bookId);

			session.delete(book);

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return book;
	}

	public static boolean returnBook(int bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Item tempBook = null;
		boolean flag = false;

		try {

			session.beginTransaction();

			tempBook = session.get(Book.class, bookId);

			if (tempBook == null) {
				System.out.println("Book ID does not match with any existing book");
				return flag;
			}

			tempBook.setIsAvailable(true);

			session.getTransaction().commit();

			flag = true;

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return flag;
	}
}
