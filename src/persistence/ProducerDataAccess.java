package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Documentary;
import entity.Loan;
import entity.Producer;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProducerDataAccess {
	
	public static Producer createProducer(String firstName, String lastName, String style, String nationality)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class).addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Producer producer = null;
		
		try
		{
			session.beginTransaction();
			
			producer = new Producer(firstName, lastName, style, nationality);
			
			session.save(producer);
			
			session.getTransaction().commit();
			
		} catch(Exception e)
		{
			 System.out.println("Problem creating session factory");
		     e.printStackTrace();
		} finally {
			factory.close();
		
		}
		return producer;
	}
	
	public static Producer loadProducer(int producerId)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				   																   .addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Producer producer = null;
		
		try
		{
			
			session.beginTransaction();
			
			producer = session.get(Producer.class, producerId);
			
			session.getTransaction().commit();
		
		} catch(Exception e)
		{
			 System.out.println("Problem creating session factory");
		     e.printStackTrace();
		} finally {
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
				.addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Producer> producers = null;
		Query query = null;

		try {

			session.beginTransaction();
			
			if(orderBy.equals("none"))
			{
				 query = session.createQuery("select p from Producer as p");
			}
			else if(orderBy.equals("lastNameAZ"))
			{
				query = session.createQuery("select p from Producer as p order by p.lastName asc");
			}
			else if(orderBy.equals("lastNameZA"))
			{
				query = session.createQuery("select p from Producer as p order by p.lastName desc");
			}
			
			producers = FXCollections.observableArrayList(query.list());
			
			session.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return producers;
	}
	
	public static boolean updateProducer(int producerId, String updatedFirstName, String updatedLastName, String updatedStyle,
			String updatedNationality)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
		   																		   .addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Producer producer = null;
		boolean flag = false;
		
		try {
			session.beginTransaction();

			producer = session.get(Producer.class, producerId);
			producer.setFirstName(updatedFirstName);
			producer.setLastName(updatedLastName);
			producer.setStyle(updatedStyle);
			producer.setNationality(updatedNationality);

			session.save(producer);

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

	public static boolean deleteProducer(int producerId)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Producer.class)
				   																   .addAnnotatedClass(Documentary.class).addAnnotatedClass(Student.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Producer producer = null;
		boolean flag = false;
		
		try
		{
			
			session.beginTransaction();
			
			producer = session.get(Producer.class, producerId);
			
			for(Documentary doc : producer.getDocumentaries()) {
				doc.removeProducer(producer);
			}
			
			session.delete(producer);
			
			session.getTransaction().commit();
			
			flag = true;
		} catch(Exception e)
		{
			 System.out.println("Problem creating session factory");
		     e.printStackTrace();
		} finally {
			factory.close();
		
		}
		return flag;
	}
	
}
