package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Item;
import entity.Loan;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDataAccess {

	public static String createStudent(String name, Integer graduationYear, String email) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		String result = "";

		try {

			student = new Student(name, graduationYear, email);

			session.beginTransaction();

			session.save(student);

			session.getTransaction().commit();

			result = "created";
		} catch (Exception e) {
			result = "error";
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}

	public static Student loadStudent(int studentId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;

		try {

			session.beginTransaction();

			student = session.get(Student.class, studentId);
			
			if(student != null)
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
		return student;
	}

	public static ObservableList<Student> loadAllStudents() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Student> students = null;

		try {

			session.beginTransaction();

			Query query = session.createQuery("select s from Student as s order by s.studentId");
			students = FXCollections.observableArrayList(query.list());
			
			if(students != null && !students.isEmpty()) {
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return students;
	}

	public static String updateStudent(int studentId, String updatedName, Integer updatedGraduationYear,
			String updatedEmail) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		String result = "";

		try {

			session.beginTransaction();

			student = session.get(Student.class, studentId);
			
			if(student != null)
			{
				student.setName(updatedName);
				student.setEmail(updatedEmail);
				student.setGraduationYear(updatedGraduationYear);

				session.update(student);

				result = "updated";
				
				session.getTransaction().commit();
			}
			else
			{
				result = "studentNotFound";
			}
		} catch (Exception e) {
			result = "error";
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}

	public static  String deleteStudent(Integer studentId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Item.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Student student = null;
		String result = "";

		try {

			session.beginTransaction();

			student = session.get(Student.class, studentId);
			
			if(student != null)
			{
				student.removeAllLoansData();
				
				session.delete(student);
				
				result = "deleted";

				session.getTransaction().commit();
			}
			else
			{
				result = "studentNotFound";
			}
		} catch (Exception e) {
			result = "error";
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
		return result;
	}

}
