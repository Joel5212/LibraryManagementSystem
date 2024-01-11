package persistence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entity.Author;
import entity.Book;
import entity.Documentary;
import entity.Item;
import entity.Loan;
import entity.LoanComplete;
import entity.Producer;
import entity.Student;
import helpers.LoanCalcHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoanDataAccess {

	public static void updateLoanStatusAtStartUp() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		List<Loan> loans = null;
		Query query = null;

		try {
			session.beginTransaction();
			String sqlQuery = "SELECT l FROM Loan l WHERE l.isOverdue = false and CURRENT_DATE > l.dueDate";
			query = session.createQuery(sqlQuery, Loan.class);
			loans = query.list();

			for (Loan loan : loans) {
				loan.setIsOverdue(true);
				session.saveOrUpdate(loan);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}

	}

	public static String createLoan(Integer itemId, Integer studentId, Date startDate, Date dueDate) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		String result = "";
		Session session = factory.getCurrentSession();
		List<Loan> overdueLoansOfStudent = null;
		Query query = null;

		try {
			session.beginTransaction();

			Item item = session.get(Item.class, itemId);

			if (item != null) {
				if (item.getIsAvailable()) {
					Student student = session.get(Student.class, studentId);
					if (student != null) {

						String sqlQuery = "SELECT l FROM Loan l where l.student.studentId = :studentId AND l.isOverdue = true";

						query = session.createQuery(sqlQuery, Loan.class);
						query.setParameter("studentId", studentId);

						overdueLoansOfStudent = FXCollections.observableArrayList(query.list());

						if (overdueLoansOfStudent.isEmpty()) {

							Loan loan = new Loan(item, student, startDate, dueDate);

							item.setIsAvailable(false);

							session.save(loan);

							result = "created";
							
							session.getTransaction().commit();
						} else {
							result = "overdueLoans";
						}
					} else {
						result = "studentNotFound";
					}
				} else {
					result = "itemUnavailable";
				}
			} else {
				result = "itemNotFound";
			}
		} catch (Exception e) {
			result = "error";
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();
		}
		return result;
	}

	public static ObservableList<Loan> loadLoans(boolean overdueFilter) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		ObservableList<Loan> loans = null;
		Query query = null;

		try {

			session.beginTransaction();

			if (overdueFilter == false) {
				query = session.createQuery("select l from Loan as l order by l.loanId");
			} else {
				query = session.createQuery("select l from Loan as l where l.isOverdue = true order by l.loanId");
			}

			loans = FXCollections.observableArrayList(query.list());
			
			if(loans != null && loans.size() != 0)
			{
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return loans;
	}

	public static Loan loadLoanUsingLoadId(Integer loanId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Loan loan = null;

		try {

			session.beginTransaction();

			loan = session.get(Loan.class, loanId);
			
			if(loan != null)
			{
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return loan;
	}

	public static Loan loadLoanUsingItemId(Integer itemId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		Loan loan = null;

		try {

			session.beginTransaction();

			Item item = session.get(Item.class, itemId);

			if (item != null) {
				loan = item.getLoan();
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return loan;
	}

	public static List<Loan> loadLoanUsingStudentId(Integer studentId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		List<Loan> loans = null;
		Query query = null;

		try {

			session.beginTransaction();

			String sqlQuery = "SELECT l FROM Loan l where l.student.studentId = :studentId";

			query = session.createQuery(sqlQuery, Loan.class);
			query.setParameter("studentId", studentId);

			loans = query.list();
			
			if(loans != null && loans.size() != 0)
			{
				session.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return loans;
	}

//	public static boolean updateLoan(int loanId, String updateDueDate, String loanDate)
//	{
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).addAnnotatedClass(Item.class).addAnnotatedClass(Loan.class).buildSessionFactory();		
//		Session session = factory.getCurrentSession();
//		Loan loan = null;
//		boolean flag = false;
//		
//		try
//		{
//			
//			session.beginTransaction();
//			
//			loan = session.get(Loan.class, loanId);
//			
//			loan.setLoanID(loanId);
//			loan.setDuedate(updateDueDate);
//			loan.setLoanDate(loanDate);
//			
//			session.getTransaction().commit();
//			
//			flag = true;
//		} catch(Exception e)
//		{
//			 System.out.println("Problem creating session factory");
//		     e.printStackTrace();
//		} finally {
//			factory.close();
//		
//		}
//		return flag;
//	}

	public static String deleteLoan(int loanId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		Loan loan = null;
		String flag = "";

		try {

			session.beginTransaction();

			loan = session.get(Loan.class, loanId);

			if (loan != null) {
				
				loan.removeLoan();

				session.delete(loan);

				flag = "deleted";
				
				session.getTransaction().commit();
			}
			else {
				flag="loanNotFound";
			}
		} catch (Exception e) {
			flag = "error";
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} finally {
			factory.close();

		}
		return flag;
	}

	public static String returnItem(int loanId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class)
				.addAnnotatedClass(LoanComplete.class).buildSessionFactory();
		Session session = null;
		Transaction tx = null;
		Loan loan = null;
		String flag = "";

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			loan = session.get(Loan.class, loanId);

			if (loan != null) {

				loan.getItem().setIsAvailable(true);

				String item = loan.getItem().getTitle() + " (" + loan.getItem().getItemId() + ") ";

				BigDecimal dailyPrice = loan.getItem().getDailyPrice();

				String student = loan.getStudent().getName() + " (" + loan.getStudent().getStudentId() + ") ";

				Date returnedDate = LoanCalcHelper.getCurrentDate();

				Date startDate = loan.getStartDate();

				Date dueDate = loan.getDueDate();

				BigDecimal totalPayment = LoanCalcHelper.currentLoanPayment(dueDate, startDate, dailyPrice);

				Integer daysOverdue = LoanCalcHelper.daysOverdue(dueDate);

				BigDecimal overdueFine = LoanCalcHelper.calcOverdueFine(daysOverdue, dailyPrice);

				LoanComplete loanComplete = new LoanComplete(loanId, item, student, startDate, dueDate, returnedDate,
						dailyPrice, totalPayment, daysOverdue, overdueFine);
				loan.removeLoan();
				session.delete(loan);
				session.save(loanComplete);
				flag = "returned";
				session.getTransaction().commit();
			} else {
				flag = "loanNotFound";
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
			flag = "error";
			tx.rollback();
		} finally {
			factory.close();

		}
		return flag;
	}

	public static List<LoanComplete> loadCompletedLoansForRevenueReport() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class)
				.addAnnotatedClass(LoanComplete.class).buildSessionFactory();
		Session session = null;
		Transaction tx = null;
		Query query = null;
		List<LoanComplete> loansComplete = null;
		String sqlQuery = null;

		try {

			session = factory.getCurrentSession();

			tx = session.beginTransaction();

			sqlQuery = "FROM LoanComplete";

			query = session.createQuery(sqlQuery);

			loansComplete = query.list();

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
			tx.rollback();
		} finally {
			factory.close();

		}
		return loansComplete;
	}

//	public static Double getDailyPriceOfLoan(Integer itemId) {
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
//				.addAnnotatedClass(Loan.class).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class).addAnnotatedClass(Documentary.class).addAnnotatedClass(Producer.class)
//				.addAnnotatedClass(Item.class).buildSessionFactory();
//		
//		Session session = null;
//		Transaction tx = null;
//		Query query = null;
//		Double dailyPrice = null;
//
//		try {
//
//			session = factory.getCurrentSession();
//
//			tx = session.beginTransaction();
//			
//			Item item = session.get(Item.class, itemId);
//
//			dailyPrice = item.getDailyPrice();
//
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			System.out.println("Problem creating session factory");
//			e.printStackTrace();
//			tx.rollback();
//		} finally {
//			factory.close();
//
//		}
//		return dailyPrice;
//	}

}
