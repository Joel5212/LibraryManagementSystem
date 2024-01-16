package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Author;
import entity.Book;
import entity.Loan;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuthorDataAccess {
	public static String createAuthor(String firstName, String lastName, String subject, String nationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class)
				.addAnnotatedClass(Author.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Author author = null;
		String result = "";
		Session session = null;
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			author = new Author(firstName, lastName, subject, nationality);

			session.save(author);

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

	public static Author loadAuthor(int authorId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Author author = null;

		try {
			session.beginTransaction();

			author = session.get(Author.class, authorId);

			if (author != null) {
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return author;
	}

	public static ObservableList<Author> loadAllAuthors(String orderBy) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Author> authors = null;
		Query query = null;

		try {

			session.beginTransaction();

			if (orderBy.equals("lastNameAZ")) {
				query = session.createQuery("select a from Author as a order by a.lastName asc");
			} else if (orderBy.equals("lastNameZA")) {
				query = session.createQuery("select a from Author as a order by a.lastName desc");
			} else if (orderBy.equals("none")) {
				query = session.createQuery("select a from Author as a order by a.authorId asc");
			}

			authors = FXCollections.observableArrayList(query.list());
			
			if(authors != null && !authors.isEmpty())
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
		return authors;
	}

	public static String updateAuthor(int authorId, String updatedFirstName, String updatedLastName,
			String updatedSubject, String updatedNationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = null;
		Author author = null;
		String result = "";
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			author = session.get(Author.class, authorId);

			if (author != null) {

				author.setFirstName(updatedFirstName);
				author.setLastName(updatedLastName);
				author.setSubject(updatedSubject);
				author.setNationality(updatedNationality);

				session.save(author);

				result = "updated";

				session.getTransaction().commit();
			} else {
				result = "authorNotFound";
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

	public static String deleteAuthor(int authorId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = null;
		String result = "";
		Author author = null;
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			author = session.get(Author.class, authorId);

			if (author != null) {

				List<Book> books = author.getBooks();

				for (Book book : books) {
					book.removeAuthor(author);
				}

				session.delete(author);
				
				result = "deleted";

				session.getTransaction().commit();
			}
			else {
				result = "authorNotFound";
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
