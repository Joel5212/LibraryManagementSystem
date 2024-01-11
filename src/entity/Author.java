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
@Table(name = "author")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id")
	private int authorId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "subject")
	private String subject;

	@ManyToMany(mappedBy = "authors", cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<>();

	public Author(String firstName, String lastName, String subject, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.subject = subject;
		this.nationality = nationality;
	}

	public Author() {

	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
	
	public void addBook(Book book)
	{
		books.add(book);
	}
	
	public void removeBook(Book book)
	{
		books.remove(book);
	}
	
	public void addBooks(List<Book> addBooks)
	{
		for(Book book : addBooks)
		{
			books.add(book);
		}
	}
	
	@Override
	public String toString() {
		return  lastName + ", " + firstName;
	}

}