package entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loan_incomplete")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private Integer loanId;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "student_id")
	private Student student;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "is_overdue")
	private boolean isOverdue;

	public Loan(Item item, Student student, Date startDate, Date dueDate) {
		super();
		this.item = item;
		this.student = student;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.isOverdue = false;
	}
	
	public Loan() {
		
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanID(int loanID) {
		this.loanId = loanID;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
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

	public boolean isOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	public void removeLoanData() {
		//remove loan from the item side
		getItem().removeLoanData();
	    //remove loan from the student side
		getStudent().removeLoanData(this);
	}

}
