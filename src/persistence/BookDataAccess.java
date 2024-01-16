package persistence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Author;
import entity.Book;
import entity.Item;
import entity.Loan;
import entity.Student;

public class BookDataAccess {

	public static String createBook(String title, String description, String location, BigDecimal dailyPrice,
			Integer numberOfPages, String publisher, Date publicationDate, List<Author> authors) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class)
				.addAnnotatedClass(Author.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		String result = "";
		Session session = null;
		Transaction tx = null;

		try {
			
			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			Book book = new Book(true, title, description, location, dailyPrice, numberOfPages, publisher,
					publicationDate);

			book.setAuthors(authors.stream().map(author -> {
				author.addBook(book);
				return author;
			}).collect(Collectors.toList()));

			session.save(book);

			session.getTransaction().commit();
			
			result = "created";
		} catch (Exception e) {
			result = "error";
			tx.rollback();
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}

	public static Book loadBook(Integer bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();

		Book book = null;

		try {

			session.beginTransaction();

			book = session.get(Book.class, bookId);
			
			if(book != null)
			{
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return book;
	}

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
			
			if (books != null && !books.isEmpty()) {
				session.getTransaction().commit();
			}
			
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return books;
	}

	public static Map<Integer, String> loadAuthorsOfBook(int bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Map<Integer, String> authorsMap = null;
		List<Author> authors = null;

		try {

			session.beginTransaction();

			authors = session.get(Book.class, bookId).getAuthors();

			if (authors != null) {
				for (Author author : authors) {
					authorsMap.put(author.getAuthorId(), author.getFirstName() + author.getLastName());
				}
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return authorsMap;
	}

	public static String updateBook(Integer bookId, String updatedTitle, String updatedDescription, String updatedLocation,
			BigDecimal updatedDailyPrice, Integer updatedNumberOfPages, String updatedPublisher, Date updatedPublicationDate,
			List<Author> updatedAuthors) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = null;
		Transaction tx = null;
		String result = "";;

		try {
			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			Book book = session.get(Book.class, bookId);
			
			if(book != null)
			{
				book.setAuthors(updatedAuthors.stream().map(author -> {
					author.addBook(book);
					return author;
				}).collect(Collectors.toList()));
				
				book.setTitle(updatedTitle);
				book.setDescription(updatedDescription);
				book.setLocation(updatedLocation);
				book.setDailyPrice(updatedDailyPrice);
				book.setNumberOfPages(updatedNumberOfPages);
				book.setPublisher(updatedPublisher);
				book.setPublicationDate(updatedPublicationDate);

				session.saveOrUpdate(book);

				session.getTransaction().commit();
				
				result = "updated";
			}
			else
			{
				result = "bookNotFound";
			}
		} catch (Exception e) {
			result = "error";
			tx.rollback();
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}

	public static String deleteBook(int bookId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = null;
		Transaction tx = null;
		Book book = null;
		String result = "";
		
		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			book = session.get(Book.class, bookId);
			
			if(book != null)
			{
				
				if(book.getAuthors() != null && !book.getAuthors().isEmpty())
				{
					book.removeAuthors();
				}
				
				if(book.getLoan() != null)
				{
					book.removeLoanData();
				}
				
				session.delete(book);

				session.getTransaction().commit();
				
				result = "deleted";
			}
			else
			{
				result = "bookNotFound";
			}
		} catch (Exception e) {
			result = "error";
			tx.rollback();
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}
}
