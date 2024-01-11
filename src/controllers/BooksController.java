package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.math.BigDecimal;

import entity.Author;
import entity.Book;
import helpers.DateHelper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import persistence.AuthorDataAccess;
import persistence.BookDataAccess;

public class BooksController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfBookId;
	@FXML
	private HBox tfAuthorName1;
	@FXML
	private TextField tfTitle;
	@FXML
	private HBox tfAuthorName;
	@FXML
	private TextField tfLocation;
	@FXML
	private TextField tfDailyPrice;
	@FXML
	private TextField tfPublisher;
	@FXML
	private TextField tfPublicationDate;
	@FXML
	private TextField tfNumberOfPages;
	@FXML
	private TextArea taDescription;
	@FXML
	private CheckBox cbAvailable;
	@FXML
	private ListView<Author> lvAuthors;
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
	private CheckBox cbTitleAZ;
	@FXML
	private CheckBox cbTitleZA;
	@FXML
	private CheckBox cbPriceHL;
	@FXML
	private CheckBox cbPriceLH;
	@FXML
	private TableView tvBooks;
	@FXML
	private TableColumn<Book, Integer> colBookId;
	@FXML
	private TableColumn<Book, String> colTitle;
	@FXML
	private TableColumn<Book, String> colLocation;
	@FXML
	private TableColumn<Book, String> colAuthors;
	@FXML
	private TableColumn<Book, String> colDailyPrice;
	@FXML
	private TableColumn<Book, Integer> colNumberPages;
	@FXML
	private TableColumn<Book, String> colPublisher;
	@FXML
	private TableColumn<Book, String> colPublicationDate;
	@FXML
	private TableColumn<Book, String> colDescription;
	@FXML
	private TableColumn<Book, String> colAvailable;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showAllBooks();
		
		lvAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		cbAvailable.setSelected(false);

		ObservableList<Author> authors = FXCollections
				.observableArrayList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));

		lvAuthors.getItems().addAll(authors);

	}

	public void showAllBooks() {
		ObservableList<Book> books = null;

		if (cbTitleAZ.isSelected() && cbPriceHL.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleAZ AND priceHL"));
		} else if (cbTitleZA.isSelected() && cbPriceLH.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleZA AND priceLH"));
		} else if (cbTitleAZ.isSelected() && cbPriceLH.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleAZ AND priceLH"));
		} else if (cbTitleZA.isSelected() && cbPriceHL.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleZA AND priceHL"));
		} else if (cbTitleZA.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleZA"));
		} else if (cbTitleAZ.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("titleAZ"));
		} else if (cbPriceLH.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("priceLH"));
		} else if (cbPriceHL.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("priceHL"));
		} else if (!cbTitleAZ.isSelected() && !cbTitleZA.isSelected() && !cbPriceLH.isSelected()
				&& !cbPriceHL.isSelected()) {
			books = FXCollections.observableList(BookDataAccess.loadAllBooks("none"));
		}

		colBookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("itemId"));
		colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		colLocation.setCellValueFactory(new PropertyValueFactory<Book, String>("location"));
		colAuthors.setCellValueFactory(cellData -> {

			StringBuilder sb = new StringBuilder();

			for (Author author : cellData.getValue().getAuthors()) {
				sb.append(author.toString() + "  ");
			}

			return new SimpleStringProperty(sb.toString());

		});
		colDailyPrice.setCellValueFactory(cellData -> {
			return new SimpleStringProperty("$" + cellData.getValue().getDailyPrice());
		});
		colNumberPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numberPages"));
		colPublisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
		colPublicationDate.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getPublicationDate()));
		});
		colLocation.setCellValueFactory(new PropertyValueFactory<Book, String>("location"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
		colAvailable.setCellValueFactory(cellData -> {
			return cellData.getValue().getIsAvailable() ? new SimpleStringProperty("Yes")
					: new SimpleStringProperty("No");
		});

		tvBooks.setItems(books);
	}

	@FXML
	private void showAllBooksAfterAction(ActionEvent event) {

		ObservableList<Book> books = null;

		if (event.getSource() == cbPriceHL) {
			if (cbPriceHL.isSelected()) {
				cbPriceLH.setSelected(false);
			}
			showAllBooks();
		} else if (event.getSource() == cbPriceLH) {
			if (cbPriceLH.isSelected()) {
				cbPriceHL.setSelected(false);
			}
			showAllBooks();

		} else if (event.getSource() == cbTitleAZ) {
			if (cbTitleAZ.isSelected()) {
				cbTitleZA.setSelected(false);
			}
			showAllBooks();

		} else if (event.getSource() == cbTitleZA) {
			if (cbTitleZA.isSelected()) {
				cbTitleAZ.setSelected(false);
			}
			showAllBooks();

		}
	}

	@FXML
	private void clearFields() {
		tfBookId.clear();
		tfTitle.clear();
		tfLocation.clear();
		tfDailyPrice.clear();
		tfPublisher.clear();
		tfPublicationDate.clear();
		tfNumberOfPages.clear();
		taDescription.clear();
		cbAvailable.setSelected(false);

		lvAuthors.getItems().clear();

		ObservableList<Author> authors = FXCollections
				.observableArrayList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));

		lvAuthors.getItems().addAll(authors);

	}

	@FXML
	private void createBook() {
		// TODO Auto-generated method stub
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		BigDecimal dailyPrice = new BigDecimal(tfDailyPrice.getText());
		String publisher = tfPublisher.getText();
		Date publicationDate = Date.valueOf(tfPublicationDate.getText());
		Integer numberOfPages = Integer.valueOf(tfNumberOfPages.getText());
		String description = taDescription.getText();

		List<Author> selectedAuthors = lvAuthors.getSelectionModel().getSelectedItems();

		BookDataAccess.createBook(title, description, location, dailyPrice, numberOfPages, publisher, publicationDate,
				selectedAuthors);

		clearFields();
		showAllBooks();
	}

	@FXML
	private void updateBook() {
		Integer bookId = Integer.valueOf(tfBookId.getText());
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		BigDecimal dailyPrice = new BigDecimal(tfDailyPrice.getText());
		String publisher = tfPublisher.getText();
		Date publicationDate = Date.valueOf(tfPublicationDate.getText());
		Integer numberOfPages = Integer.valueOf(tfNumberOfPages.getText());
		String description = taDescription.getText();

		List<Author> selectedAuthors = lvAuthors.getSelectionModel().getSelectedItems();

		BookDataAccess.updateBook(bookId, title, description, location, dailyPrice, numberOfPages, publisher,
				publicationDate, selectedAuthors);

		showAllBooks();
	}

	@FXML
	private void loadBook() {
		Integer bookId = Integer.valueOf(tfBookId.getText());

		Book book = BookDataAccess.loadBook(bookId);

		tfTitle.setText(book.getTitle());
		tfLocation.setText(book.getLocation());
		tfDailyPrice.setText(book.getDailyPrice().toString());
		tfPublisher.setText(book.getPublisher());
		tfPublicationDate.setText(DateHelper.dateToYYYYMMddDate(book.getPublicationDate()));
		tfNumberOfPages.setText(String.valueOf(book.getNumberPages()));
		taDescription.setText(book.getDescription());
		cbAvailable.setSelected(book.getIsAvailable());

		List<Author> allAuthors = AuthorDataAccess.loadAllAuthors("lastNameAZ");
		List<Author> authors = book.getAuthors();

		lvAuthors.getItems().clear();

		int numOfSelectedItems = 0;
		for (int i = 0; i < allAuthors.size(); i++) {
			for (int j = 0; j < authors.size(); j++) {
				if (allAuthors.get(i).getAuthorId() == authors.get(j).getAuthorId()) {
					allAuthors.remove(i);
					allAuthors.add(0, authors.get(j));
					numOfSelectedItems++;
				}
			}
		}

		lvAuthors.getItems().addAll(allAuthors);

		while (numOfSelectedItems > 0) {
			lvAuthors.getSelectionModel().select(allAuthors.get(numOfSelectedItems - 1));
			numOfSelectedItems--;
		}

		lvAuthors.getSelectionModel().setSelectionMode(null);

		showAllBooks();
	}

	@FXML
	private void deleteBook() {
		Integer bookId = Integer.valueOf(tfBookId.getText());

		BookDataAccess.deleteBook(bookId);

		clearFields();
		showAllBooks();
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
	public void authorsPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Authors.fxml"));
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
