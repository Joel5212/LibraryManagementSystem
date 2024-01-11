package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Author;
import entity.Book;
import entity.Loan;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuthorDataAccess {
	public static Author createAuthor(String firstName, String lastName, String subject, String nationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class)
				.addAnnotatedClass(Author.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Author tempAuthor = null;
		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();

			tempAuthor = new Author(firstName, lastName, subject, nationality);

			session.save(tempAuthor);

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return tempAuthor;
	}

	public static Author loadAuthor(int authorId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();

		Author tempAuthor = null;

		try {

			session.beginTransaction();

			tempAuthor = session.get(Author.class, authorId);

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return tempAuthor;
	}

//	public static List<Integer> getBookList(int code) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
//				.addAnnotatedClass(Book.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<Integer> bookIds = new ArrayList<Integer>();
//
//		try {
//
//			session.beginTransaction();
//
//			List<Book> books = session.get(Author.class, code).getBooks();
//
//			if (books != null) {
//				for (Book book : books) {
//					bookIds.add(book.getItemId());
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
//		return bookIds;
//	}

	public static ObservableList<Author> loadAllAuthors(String orderBy) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Author> authors = null;
		Query query = null;

		try {

			session.beginTransaction();
			
			if(orderBy.equals("lastNameAZ"))
			{
				 query = session.createQuery("select a from Author as a order by a.lastName asc");
			}
			else if(orderBy.equals("lastNameZA"))
			{
				query = session.createQuery("select a from Author as a order by a.lastName desc");
			}
			else if(orderBy.equals("none"))
			{
				query = session.createQuery("select a from Author as a");
			}
			
			authors = FXCollections.observableArrayList(query.list());
			
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return authors;
	}

	public static boolean updateAuthor(int authorId, String updatedFirstName, String updatedLastName, String updatedSubject,
			String updatedNationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Author author = null;
		boolean flag = false;

		try {
			session.beginTransaction();

			author = session.get(Author.class, authorId);

			author.setFirstName(updatedFirstName);
			author.setLastName(updatedLastName);
			author.setSubject(updatedSubject);
			author.setNationality(updatedNationality);

			session.save(author);

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

	public static boolean deleteAuthor(int authorId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		boolean flag = true;

		try {

			session.beginTransaction();

			Author tempAuthor = session.get(Author.class, authorId);
			
			List<Book> books = tempAuthor.getBooks();
			
			for(Book book : books)
			{
				book.removeAuthor(tempAuthor);
			}

			session.delete(tempAuthor);

			session.getTransaction().commit();

		} catch (Exception e) {
			flag = false;
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return flag;
	}
}
