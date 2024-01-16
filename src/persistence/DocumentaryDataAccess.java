package persistence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
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
import entity.Documentary;
import entity.Loan;
import entity.Producer;
import entity.Student;

public class DocumentaryDataAccess {

	public static String createDocumentary(String title, String description, String location, BigDecimal dailyPrice,
			Integer length, Date releaseDate, List<Producer> producers) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Producer.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		String result = "";
		Session session = null;
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			Documentary documentary = new Documentary(true, title, description, location, dailyPrice, length,
					releaseDate);

			documentary.setProducers(producers.stream().map(producer -> {
				producer.addDocumentary(documentary);
				return producer;
			}).collect(Collectors.toList()));

			session.save(documentary);

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

	public static Documentary loadDocumentary(int documentaryId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Producer.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();

		Documentary documentary = null;

		try {
			session.beginTransaction();

			documentary = session.get(Documentary.class, documentaryId);

			if (documentary != null) {
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return documentary;
	}

	public static List<Documentary> loadAllDocumentaries(String orderBy) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		List<Documentary> documentaries = null;
		Query query = null;

		try {

			session.beginTransaction();

			if (orderBy.equals("none")) {
				query = session.createQuery("select d from Documentary as d");
			} else if (orderBy.equals("titleAZ")) {
				query = session.createQuery("select d from Documentary as d order by d.title asc");
			} else if (orderBy.equals("titleZA")) {
				query = session.createQuery("select d from Documentary as d order by d.title desc");
			} else if (orderBy.equals("priceHL")) {
				query = session.createQuery("select d from Documentary as d order by d.dailyPrice desc");
			} else if (orderBy.equals("priceLH")) {
				query = session.createQuery("select d from Documentary as d order by d.dailyPrice asc");
			} else if (orderBy.equals("titleAZ AND priceHL")) {
				query = session.createQuery("select d from Documentary as d order by d.title asc, d.dailyPrice desc");
			} else if (orderBy.equals("titleZA AND priceLH")) {
				query = session.createQuery("select d from Documentary as d order by d.title desc, d.dailyPrice asc");
			} else if (orderBy.equals("titleAZ AND priceLH")) {
				query = session.createQuery("select d from Documentary as d order by d.title asc, d.dailyPrice asc");
			} else if (orderBy.equals("titleZA AND priceHL")) {
				query = session.createQuery("select d from Documentary as d order by d.title desc, d.dailyPrice desc");
			}

			documentaries = query.list();

			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return documentaries;
	}

	public static Map<Integer, String> loadProducersOfDocumentary(int code) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Producer.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Map<Integer, String> producersMap = null;
		List<Producer> producers = null;

		try {

			session.beginTransaction();

			producers = session.get(Documentary.class, code).getProducers();

			if (producers != null) {
				for (Producer producer : producers) {
					producersMap.put(producer.getProducerId(), producer.getFirstName() + producer.getLastName());
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
		return producersMap;
	}

//	public static List<DocumentaryDataAccess> getProducerList(int code){
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class).addAnnotatedClass(DocumentaryDataAccess.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<DocumentaryDataAccess> producers = null;
//		
//		try
//		{
//			
//			session.beginTransaction();
//			
//			producers = session.get(Documentary.class, code).getProducers();
//			
//			session.getTransaction().commit();
//		
//		} catch(Exception e)
//		{
//			 System.out.println("Problem creating session factory");
//		     e.printStackTrace();
//		} finally {
//			factory.close();
//		
//		}
//		return producers;
//	}

	public static String updateDocumentary(Integer documentaryId, String updatedTitle,
			String updatedDescription, String updatedLocation, BigDecimal updatedDailyPrice, Integer updatedLength,
			Date updatedReleaseDate, List<Producer> updatedProducers) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Producer.class).addAnnotatedClass(Loan.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = null;
		Transaction tx = null;
		String result = "";

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			Documentary documentary = session.get(Documentary.class, documentaryId);

			if (documentary != null) {
				documentary.setProducers(updatedProducers.stream().map(producer -> {
					producer.addDocumentary(documentary);
					return producer;
				}).collect(Collectors.toList()));

				documentary.setTitle(updatedTitle);
				documentary.setDescription(updatedDescription);
				documentary.setLocation(updatedLocation);
				documentary.setDailyPrice(updatedDailyPrice);
				documentary.setLength(updatedLength);
				documentary.setReleaseDate(updatedReleaseDate);

				session.saveOrUpdate(documentary);

				session.getTransaction().commit();

				result = "updated";
			}
			else
			{
				result = "documentaryNotFound";
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

	public static String deleteDocumentary(int documentaryId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Producer.class).addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Documentary documentary = null;
		Transaction tx = null;
		String result = "";

		try {
			
			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			documentary = session.get(Documentary.class, documentaryId);
			
			if(documentary != null)
			{
				if (documentary.getProducers() != null && !documentary.getProducers().isEmpty()) 
				{
					documentary.removeProducers();
				}

				if (documentary.getLoan() != null) 
				{
					documentary.removeLoanData();
				}

				session.delete(documentary);

				session.getTransaction().commit();

				result = "deleted";
			}
			else
			{
				result = "documentaryNotFound";
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