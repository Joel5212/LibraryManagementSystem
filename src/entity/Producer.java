package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "producer")
public class Producer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producer_id")
	private int producerId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "style")
	private String style;

	@ManyToMany(mappedBy = "producers", cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private List<Documentary> documentaries = new ArrayList<>();

	public Producer(String firstName, String lastName, String style, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.style = style;
		this.nationality = nationality;
	}

	public Producer() {

	}

	public int getProducerId() {
		return producerId;
	}

	public void setProducerId(int authorId) {
		this.producerId = authorId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Documentary> getDocumentaries() {
		return documentaries;
	}
	
	public void setDocumentaries(List<Documentary> documentaries) {
		this.documentaries = documentaries;
	}

	public void addDocumentary(Documentary documentary)
	{
		documentaries.add(documentary);
	}
	
	public void removeDocumentary(Documentary documentary)
	{
		documentaries.remove(documentary);
	}
	
	public void addDocumentaries(List<Documentary> documentaries)
	{
		for(Documentary documentary : documentaries)
		{
			documentaries.add(documentary);
		}
	}
	
	@Override
	public String toString() {
		return  lastName + ", " + firstName;
	}

}