package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Item;
import entity.Loan;
import entity.Student;

public class StudentDataAccess {
	
	public static boolean createStudent(Integer broncoId, String name, String course, String email)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
																				   .addAnnotatedClass(Loan.class)
																				   .addAnnotatedClass(Item.class).buildSessionFactory();
		boolean flag = false;
		Session session = factory.getCurrentSession();
		Student student = null;
		
		try
		{
			if(broncoId == null)
			{
				student = new Student(name, course, email);
			}
			else
			{
				student = new Student(broncoId, name, course, email);
			}
			
			session.beginTransaction();
			
			session.save(student);
			
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
	
	public static Student searchStudent(int broncoId)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
																				   .addAnnotatedClass(Loan.class)
																				   .addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		
		try
		{
			
			session.beginTransaction();
			
			student = session.get(Student.class, broncoId);
			
			session.getTransaction().commit();
		
		} catch(Exception e)
		{
			 System.out.println("Problem creating session factory");
		     e.printStackTrace();
		} finally {
			factory.close();
		
		}
		return student;
	}
	
	public static boolean updateStudent(int broncoId, String updatedName, String updatedCourse, String updatedEmail)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				   																   .addAnnotatedClass(Loan.class)
				   																   .addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		boolean flag = false;
		
		try
		{
			
			session.beginTransaction();
			
			student = session.get(Student.class, broncoId);
			
			student.setName(updatedName);
			student.setCourse(updatedCourse);
			student.setEmail(updatedEmail);
			
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
	
	public static boolean deleteStudent(int broncoId)
	{
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				   																   .addAnnotatedClass(Loan.class)
				   																   .addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		boolean flag = false;
		
		try
		{
			
			session.beginTransaction();
			
			student = session.get(Student.class, broncoId);
			
			session.delete(student);
			
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
