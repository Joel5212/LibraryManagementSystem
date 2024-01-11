//package persistence;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.query.Query;
//
//import entity.Author;
//import entity.Book;
//import entity.Item;
//import entity.Loan;
//import entity.LoanComplete;
//import entity.Student;
//import helpers.DateHelper;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//public class LoanCompleteDataAccess {
//
//	public static void moveToLoanComplete(Loan loan) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
//		String flag = "";
//		Session session = null;
//		Transaction tx = null;
//		List<Loan> overdueLoansOfStudent = null;
//		Query query = null;
//
//		try {
//			session = factory.getCurrentSession();
//			
//			tx = session.beginTransaction();
//			
//			String item = loan.getItem().getTitle() + " (" + loan.getItem().getItemId() + ") ";
//			String student = loan.getStudent().getName() + " (" + loan.getStudent().getStudentId() + ") ";
//			
//			LoanComplete loanComplete = new LoanComplete(loan.getLoanID(), item, student, loan.getStartDate(), loan.getDueDate(), loan.getReturnedDate(), loan.getItem().getDailyPrice(), loan.getTotalPayment());
//			
//			session.save(loanComplete);
//			
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			flag = "error";
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//			tx.rollback();
//		} finally {
//			factory.close();
//		}
//
//	}
//
//	public static ObservableList<Loan> loadAllLoans(boolean overdueFilter) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		ObservableList<Loan> loans = null;
//		Query query = null;
//
//		try {
//
//			session.beginTransaction();
//
//			if (overdueFilter == false) {
//				query = session.createQuery("select l from Loan as l where l.status= 'loaning' or l.status = 'overdue' order by l.loanId");
//			} else {
//				query = session.createQuery("select l from Loan as l where l.status='overdue' order by l.loanId");
//			}
//
//			loans = FXCollections.observableArrayList(query.list());
//
//			session.getTransaction().commit();
//
//		} catch (Exception e) {
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//			
//		} finally {
//			factory.close();
//
//		}
//		return loans;
//	}
//
////	public static List<Loan> loadLoans(Integer broncoId) {
////		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
////				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
////		Session session = factory.getCurrentSession();
////		List<Loan> loans = null;
////
////		try {
////
////			session.beginTransaction();
////
////			String hql = "FROM Loan L WHERE L.student.broncoId = " + broncoId;
////			Query<Loan> query = session.createQuery(hql);
////			loans = query.list();
////
////			for (Loan loan : loans) {
////				System.out.println("Row" + loan.toString());
////			}
////
////			session.getTransaction().commit();
////
////		} catch (Exception e) {
////			System.out.println("Problem creating session factory");
////			e.printStackTrace();
////		} finally {
////			factory.close();
////
////		}
////		return loans;
////	}
//
//	public static Loan loadLoanUsingLoadId(Integer loanId) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		Loan loan = null;
//
//		try {
//
//			session.beginTransaction();
//
//			loan = session.get(Loan.class, loanId);
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
//		return loan;
//	}
//
//	public static Loan loadLoanUsingItemId(Integer itemId) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		Loan loan = null;
//
//		try {
//
//			session.beginTransaction();
//
//			Item item = session.get(Item.class, itemId);
//
//			if (item != null) {
//				loan = item.getLoan();
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
//		return loan;
//	}
//
//	public static List<Loan> loadLoanUsingStudentId(Integer studentId) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		List<Loan> loans = null;
//		Query query = null;
//
//		try {
//
//			session.beginTransaction();
//
//			String sqlQuery = "SELECT l FROM Loan l where l.student.studentId = :studentId";
//
//			query = session.createQuery(sqlQuery, Loan.class);
//			query.setParameter("studentId", studentId);
//
//			loans = query.list();
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
//		return loans;
//	}
//
////	public static boolean updateLoan(int loanId, String updateDueDate, String loanDate)
////	{
////		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();		
////		Session session = factory.getCurrentSession();
////		Loan loan = null;
////		boolean flag = false;
////		
////		try
////		{
////			
////			session.beginTransaction();
////			
////			loan = session.get(Loan.class, loanId);
////			
////			loan.setLoanID(loanId);
////			loan.setDuedate(updateDueDate);
////			loan.setLoanDate(loanDate);
////			
////			session.getTransaction().commit();
////			
////			flag = true;
////		} catch(Exception e)
////		{
////			 System.out.println("Problem creating session factory");
////		     e.printStackTrace();
////		} finally {
////			factory.close();
////		
////		}
////		return flag;
////	}
//
//	public static boolean deleteLoan(int loanId) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class)
//				.buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		Loan loan = null;
//		boolean flag = false;
//
//		try {
//
//			session.beginTransaction();
//
//			loan = session.get(Loan.class, loanId);
//			
//			loan.removeLoan();
//			
//			session.delete(loan);
//
//			session.getTransaction().commit();
//
//			flag = true;
//		} catch (Exception e) {
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//		} finally {
//			factory.close();
//
//		}
//		return flag;
//	}
//	
////	public static String returnItem(int loanId) {
////		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
////				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class)
////				.buildSessionFactory();
////		Session session = factory.getCurrentSession();
////		Loan loan = null;
////		String flag = "";
////
////		try {
////
////			session.beginTransaction();
////
////			loan = session.get(Loan.class, loanId);
////			
////			if(loan != null)
////			{
////				loan.setStatus("complete");
////				
////				loan.getItem().setIsAvailable(true);
////				
////				loan.setReturnedDate(DateHelper.getCurrentDate());
////				
////				loan.setTotalPayment(DateHelper.currentLoanPayment(loan.getDueDate(), loan.getStartDate(), loan.getItem().getDailyPrice()));
////				
////				session.saveOrUpdate(loan);
////				
////				flag = "returned";
////			}
////			else
////			{
////				flag = "loanNotFound";
////			}
////
////			session.getTransaction().commit();
////		} catch (Exception e) {
////			System.out.println("Problem creating session factory");
////			e.printStackTrace();
////			flag = e.toString();
////		} finally {
////			factory.close();
////
////		}
////		return flag;
////	}
//
//}
