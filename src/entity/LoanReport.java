package entity;

import java.math.BigDecimal;
import java.util.Date;

public class LoanReport {

	private Integer loanId;

	private String item;

	private String student;

	private Date startDate;

	private Date dueDate;

	private Date returnedDate;

	private BigDecimal dailyPrice;

	private BigDecimal totalPayment;

	private Integer daysOverdue;

	private BigDecimal overdueFine;

	public LoanReport(Integer loanId, String item, String student, Date startDate, Date dueDate, Date returnedDate,
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

	public LoanReport() {

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

	@Override
	public String toString() {
		return "LoanReport [loanId=" + loanId + ", item=" + item + ", student=" + student + ", startDate=" + startDate
				+ ", dueDate=" + dueDate + ", returnedDate=" + returnedDate + ", dailyPrice=" + dailyPrice
				+ ", totalPayment=" + totalPayment + ", daysOverdue=" + daysOverdue + ", overdueFine=" + overdueFine
				+ "]";
	}

}
