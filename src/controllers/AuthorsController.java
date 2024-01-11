package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Author;
import entity.Book;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistence.AuthorDataAccess;
import javafx.beans.property.SimpleStringProperty;

public class AuthorsController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfAuthorId;
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfNationality;
	@FXML
	private TextField tfSubject;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnUpdate;
	@FXML
	private CheckBox cbLastNameAZ;
	@FXML
	private CheckBox cbLastNameZA;
	@FXML
	private TableView tvAuthors;
	@FXML
	private TableView tvBooks;
	@FXML
	private TableColumn<Author, Integer> colAuthorId;
	@FXML
	private TableColumn<Author, String> colName;
	@FXML
	private TableColumn<Author, String> colNationality;
	@FXML
	private TableColumn<Author, String> colSubject;
	@FXML
	private TableColumn<Item, String> colTitle;
	@FXML
	private TableColumn<Item, String> colLocation;
	@FXML
	private TableColumn<Item, String> colAvailable;
	@FXML
	private TableColumn<Item, Double> colDailyPrice;
	@FXML
	private TableColumn<Item, String> colDescription;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showAllAuthors();
	}
	
	private void showAllAuthors()
	{
		ObservableList<Author> authors = null;
		
		if(cbLastNameAZ.isSelected() && !cbLastNameZA.isSelected())
		{
			authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));
		}
		else if(cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected())
		{
			authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("lastNameZA"));
		}
		else if(!cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected())
		{
			authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("none"));
		}
		
		colAuthorId.setCellValueFactory(new PropertyValueFactory<Author, Integer>("authorId"));

		colName.setCellValueFactory(cellData -> {
			Author data = cellData.getValue();
			return new SimpleStringProperty(data.getLastName() + ", " + data.getFirstName());
		});

		colNationality.setCellValueFactory(new PropertyValueFactory<Author, String>("nationality"));
		colSubject.setCellValueFactory(new PropertyValueFactory<Author, String>("subject"));

		tvAuthors.setItems(authors);
	}
	
	@FXML
	private void showAllAuthorsAfterAction(ActionEvent event) {

		ObservableList<Author> authors = null;
		
		if(event == null)
		{
			authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("none"));
		}
		else if(event.getSource() == cbLastNameAZ) {
			if(!cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected())
			{
				authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("none"));
			}
			else
			{
				authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));
				if(cbLastNameZA.isSelected())
				{
					cbLastNameZA.setSelected(false);
				}
			}
			
		}  
		else if(event.getSource() == cbLastNameZA)
		{
			if(!cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected())
			{
				authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("none"));
			}
			else {
				authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("lastNameZA"));
				if(cbLastNameAZ.isSelected())
				{
					cbLastNameAZ.setSelected(false);
				}
			}
		}
		
		colAuthorId.setCellValueFactory(new PropertyValueFactory<Author, Integer>("authorId"));

		colName.setCellValueFactory(cellData -> {
			Author data = cellData.getValue();
			return new SimpleStringProperty(data.getLastName() + ", " + data.getFirstName());
		});

		colNationality.setCellValueFactory(new PropertyValueFactory<Author, String>("nationality"));
		colSubject.setCellValueFactory(new PropertyValueFactory<Author, String>("subject"));

		tvAuthors.setItems(authors);
	}
	
//	@FXML 
//	private void lastNameAZ()
//	{
//		authors = FXCollections.observableList(AuthorDataAccess.loadAllAuthors("lastNameZA"));
//	}
	
	public void showAllBooksOfAuthor(Author author)
	{
		ObservableList<Book> books =  FXCollections.observableList(author.getBooks());
		
		colTitle.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
		colLocation.setCellValueFactory(new PropertyValueFactory<Item, String>("location"));
		colAvailable.setCellValueFactory(new PropertyValueFactory<Item, String>("isAvailable"));
		colDailyPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("dailyPrice"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		
		tvBooks.setItems(books);
	}

	@FXML
	private void clearFields() {
		tfAuthorId.clear();
		tfFirstName.clear();
		tfLastName.clear();
		tfNationality.clear();
		tfSubject.clear();

	}

	@FXML
	public void createAuthor() {
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String nationality = tfNationality.getText().isEmpty() ? null : tfNationality.getText();
		String subject = tfSubject.getText().isEmpty() ? null : tfSubject.getText();

		AuthorDataAccess.createAuthor(firstName, lastName, subject, nationality);
		showAllAuthors();
		clearFields();
	}

	@FXML
	public void deleteAuthor() {
		Integer authorId = Integer.valueOf(tfAuthorId.getText());
		AuthorDataAccess.deleteAuthor(authorId);
		showAllAuthors();
		clearFields();
	}
	
	@FXML
	public void searchAuthor() {
		Integer authorId = Integer.valueOf(tfAuthorId.getText());
		Author author = AuthorDataAccess.loadAuthor(authorId);
		
		tfFirstName.setText(author.getFirstName());
		tfLastName.setText(author.getLastName());
		tfNationality.setText(author.getNationality());
		tfSubject.setText(author.getSubject());
		
		showAllBooksOfAuthor(author);
	}

	@FXML
	public void updateAuthor() {
		Integer authorId = Integer.valueOf(tfAuthorId.getText());
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String nationality = tfNationality.getText();
		String subject = tfSubject.getText();

		AuthorDataAccess.updateAuthor(authorId, firstName, lastName, subject, nationality);
		showAllAuthors();

	}
	
	@FXML
	public void studentsPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Students.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void booksPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Books.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void producersPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Producers.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void documentariesPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Documentaries.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void loansPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Loans.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
