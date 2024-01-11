package entity;

import java.util.Date;

public class LoanReport {

	private Integer loanId;

	private String item;

	private String student;

	private Date startDate;

	private Date dueDate;

	private Date returnedDate;

	private Double dailyPrice;

	private Double totalPayment;

	private Integer daysOverdue;

	private Double overdueFine;

	public LoanReport(Integer loanId, String item, String student, Date startDate, Date dueDate, Date returnedDate,
			Double dailyPrice, Double totalPayment, Integer daysOverdue, Double overdueFine) {
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

	public Double getOverdueFine() {
		return overdueFine;
	}

	public void setOverdueFine(Double overdueFine) {
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

	public Double getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(Double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
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
