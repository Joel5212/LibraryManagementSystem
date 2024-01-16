package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Documentary;
import entity.Loan;
import entity.Producer;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProducerDataAccess {

	public static String createProducer(String firstName, String lastName, String style, String nationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Producer producer = null;
		String result = "";
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			producer = new Producer(firstName, lastName, style, nationality);

			session.save(producer);
			
			result = "created";

			session.getTransaction().commit();
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

	public static Producer loadProducer(int producerId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Producer producer = null;

		try {

			session.beginTransaction();

			producer = session.get(Producer.class, producerId);

			if (producer != null) {
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return producer;
	}

//	public static String getDocumentariesOfProducer(int code){
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class).addAnnotatedClass(Producer.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<Documentary> documentaries = null;
//		String documentariesString = "";
//		
//		try
//		{
//			
//			session.beginTransaction();
//			
//			documentaries = session.get(Producer.class, code).getDocumentaries();
//			
//			
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
//		return documentariesString;
//	}
//	
//	public static List<Integer> getDocumentaryList(int code){
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Documentary.class).addAnnotatedClass(DocumentaryDataAccess.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<Integer> documentaryIds = new ArrayList<Integer>();
//		
//		try
//		{
//			
//			session.beginTransaction();
//			
//			List<Documentary> documentaries = session.get(DocumentaryDataAccess.class, code).getDocumentaries();
//			
//			if(documentaries != null) {
//				for(Documentary doc : documentaries) {
//					documentaryIds.add(doc.getItemId());
//				}
//			}
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
//		return documentaryIds;
//	}
//	
	public static ObservableList<Producer> loadAllProducers(String orderBy) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Producer> producers = null;
		Query query = null;

		try {

			session.beginTransaction();

			if (orderBy.equals("none")) {
				query = session.createQuery("select p from Producer as p order by p.producerId asc");
			} else if (orderBy.equals("lastNameAZ")) {
				query = session.createQuery("select p from Producer as p order by p.lastName asc");
			} else if (orderBy.equals("lastNameZA")) {
				query = session.createQuery("select p from Producer as p order by p.lastName desc");
			}

			producers = FXCollections.observableArrayList(query.list());

			if (producers != null && !producers.isEmpty()) {
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();

		}
		return producers;
	}

	public static String updateProducer(int producerId, String updatedFirstName, String updatedLastName,
			String updatedStyle, String updatedNationality) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = null;
		Producer producer = null;
		String result = "";
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			producer = session.get(Producer.class, producerId);

			if (producer != null) {
				producer.setFirstName(updatedFirstName);
				producer.setLastName(updatedLastName);
				producer.setStyle(updatedStyle);
				producer.setNationality(updatedNationality);

				session.save(producer);

				result = "updated";

				session.getTransaction().commit();
			} else {
				result = "producerNotFound";
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

	public static String deleteProducer(int producerId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class)
				.buildSessionFactory();
		Session session = null;
		Producer producer = null;
		String result = "";
		Transaction tx = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			producer = session.get(Producer.class, producerId);

			if (producer != null) {
				
				List<Documentary> documentaries = producer.getDocumentaries();
				
				for (Documentary documentary : documentaries) {
					documentary.removeProducer(producer);
				}

				session.delete(producer);
				
				result = "deleted";

				session.getTransaction().commit();
			}
			else {
				result = "producerNotFound";
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
