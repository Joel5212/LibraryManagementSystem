package entity;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@PrimaryKeyJoinColumn(name = "item_id")
public class Book extends Item {
	@Column(name = "number_pages")
	private int numberPages;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "item_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	private List<Author> authors = new ArrayList<>();

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publication_date")
	private Date publicationDate;

	public Book() {

	}

	public Book(boolean isAvailable, String title, String description, String location, BigDecimal dailyPrice,
			int numberPages, String publisher, Date publicationDate) {
		super(isAvailable, title, description, location, dailyPrice);
		this.numberPages = numberPages;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
	}

	public int getNumberPages() {
		return numberPages;
	}

	public void setNumberPages(int numberPages) {
		this.numberPages = numberPages;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void addAuthor(Author tempAuthor) {		
		authors.add(tempAuthor);
	}

	public void removeAuthor(Author author) {
		authors.remove(author);
	}

	// @Override TODO: remove comment when superclass is implemented
//	public String toString() {
//		return;
//	}
}