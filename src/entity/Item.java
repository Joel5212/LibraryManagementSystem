package entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "item")
@Inheritance(strategy=InheritanceType.JOINED)
public class Item
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "available")
	private boolean isAvailable;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "daily_price")
	private BigDecimal dailyPrice;
	
	@OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
	private Loan loan;
	
	public Item(boolean isAvailable, String title, String description, 
					   String location, BigDecimal dailyPrice){
	    this.isAvailable = isAvailable;
	    this.title = title;
	    this.description = description;
	    this.location = location;
	    this.dailyPrice = dailyPrice;
	}
	
	public Item() {
		
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean status) {
		this.isAvailable = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(BigDecimal dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	public String returnItemDueDate() {
		//Return current time
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}
	
	//removing item and student from loan
	public void removeLoanData() {
		setIsAvailable(true);
		setLoan(null);
//		loan.setItem(null);
//	    loan.setStudent(null);
	}
}