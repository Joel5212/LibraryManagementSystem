package entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loan_complete")
public class LoanComplete {
	@Id
	@Column(name = "loan_id")
	private Integer loanId;

	@Column(name = "item")
	private String item;

	@Column(name = "student")
	private String student;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "returned_date")
	private Date returnedDate;
	
	@Column(name = "daily_price")
	private BigDecimal dailyPrice;

	@Column(name = "total_payment")
	private BigDecimal totalPayment;
	
	@Column(name = "days_overdue")
	private Integer daysOverdue;
	
	@Column(name = "overdue_fine")
	private BigDecimal overdueFine;

	public LoanComplete(Integer loanId, String item, String student, Date startDate, Date dueDate, Date returnedDate,
			BigDecimal dailyPrice, BigDecimal totalPayment, Integer daysOverdue, BigDecimal overdueFine) {
		super();
		this.loanId = loanId;
		this.item = item;
		this.student = student;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.dailyPrice = dailyPrice;
		this.totalPayment = totalPayment;
		this.daysOverdue = daysOverdue;
		this.overdueFine = overdueFine;
	}
	
	
	
	public Integer getDaysOverdue() {
		return daysOverdue;
	}

	public void setDaysOverdue(Integer daysOverdue) {
		this.daysOverdue = daysOverdue;
	}

	public BigDecimal getOverdueFine() {
		return overdueFine;
	}

	public void setOverdueFine(BigDecimal overdueFine) {
		this.overdueFine = overdueFine;
	}

	public LoanComplete() {

	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}

	public BigDecimal getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(BigDecimal dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}
}
