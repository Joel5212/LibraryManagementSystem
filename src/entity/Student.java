package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int studentId;

	@Column(name = "name")
	private String name;

	@Column(name = "graduation_year")
	private Integer graduationYear;

	@Column(name = "email")
	private String email;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Loan> loans;

	public Student(String name, Integer graduationYear, String email) {
		this.name = name;
		this.graduationYear = graduationYear;
		this.email = email;
	}

	public Student() {

	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setBroncoId(int broncoId) {
		this.studentId = broncoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(Integer graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
	
	//removing loan info from student side
	public void removeLoan(Loan loan) {
	    loan.getItem().setIsAvailable(true);
	    loan.setStudent(null);
	    getLoans().remove(loan);
	}
	
	//removing all loans of a student
	public void removeAllLoans()
	{
		if(loans != null)
		{
			for(Loan loan : this.loans)
			{
				 loan.getItem().setIsAvailable(true);
				 loan.setStudent(null);
				 loan.getItem().setLoan(null);
			}
		}
	}

	@Override
	public String toString() {
		return "broncoId=" + studentId + "\nname=" + name + "\ncourse=" + graduationYear + "\nemail=" + email;
	}

}