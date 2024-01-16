package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "documentary")
@PrimaryKeyJoinColumn(name="item_id")
public class Documentary extends Item
{	
	@Column(name = "length")
	private Integer length;
	
	@Column(name = "release_date")
	private Date releaseDate;
	
	@ManyToMany(fetch=FetchType.EAGER,  cascade = { CascadeType.PERSIST })
	@JoinTable(
	        name = "documentary_producer", 
	        joinColumns = { @JoinColumn(name = "item_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "producer_id") }
	    )
	private List<Producer> producers;
	
	public Documentary(boolean isAvailable, String title, String description, 
					   String location, BigDecimal dailyPrice, Integer length, Date releaseDate)
	{
		super(isAvailable, title, description, location, dailyPrice);
	    this.length = length;
	    this.releaseDate = releaseDate;
	}
	
	public Documentary() {
		
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public void addProducer(Producer producer) {
		if(producers == null) {
			producers = new ArrayList<Producer>();
		}
		producers.add(producer);
	}

	public void removeProducer(Producer producer) {
		producers.remove(producer);
	}
	
	public List<Producer> getProducers() {
		return producers;
	}

	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}
	
	public void removeProducers() {
		
		for(Producer producer : this.producers)
		{
			producer.removeDocumentary(this);
		}
		
		setProducers(null);
	}

	@Override
	public String toString() {
		return super.toString() + "\nlength="
				+ length + "\nreleaseDate=" + releaseDate;

	}
}