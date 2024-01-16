package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import persistence.AuthorDataAccess;
import persistence.BookDataAccess;
import validator.BookValidator;
import validator.LoanValidator;

public class BooksController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfBookId;
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
	private DatePicker publicationDatePicker;
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
	private TableColumn<Book, String> colNumberOfPages;
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
		showAllBooks();

		ObservableList<Author> authors = FXCollections
				.observableArrayList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));
		
		if (authors != null && authors.size() != 0) {
			lvAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			lvAuthors.getItems().addAll(authors);
		}
		
		cbAvailable.setSelected(false);
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
		colNumberOfPages.setCellValueFactory(cellData -> {
			Integer numberOfPages = cellData.getValue().getNumberOfPages();

			if (numberOfPages != null) {
				return new SimpleStringProperty(numberOfPages.toString());
			} else {
				return new SimpleStringProperty("");
			}
		});
		colPublisher.setCellValueFactory(cellData -> {
			String publisher = cellData.getValue().getPublisher();

			if (publisher != null && !publisher.isEmpty()) {
				return new SimpleStringProperty(publisher);
			} else {
				return new SimpleStringProperty("");
			}
		});

		colPublicationDate.setCellValueFactory(cellData -> {
			Date date = cellData.getValue().getPublicationDate();

			if (date != null) {
				return new SimpleStringProperty(
						DateHelper.dateToYYYYMMddDate(cellData.getValue().getPublicationDate()));
			} else {
				return new SimpleStringProperty("");
			}
		});
		colLocation.setCellValueFactory(cellData -> {
			String location = cellData.getValue().getLocation();

			if (location != null) {
				return new SimpleStringProperty(location);
			} else {
				return new SimpleStringProperty("");
			}
		});
		colDescription.setCellValueFactory(cellData -> {
			String description = cellData.getValue().getDescription();

			if (description != null) {
				return new SimpleStringProperty(description);
			} else {
				return new SimpleStringProperty("");
			}
		});
		colAvailable.setCellValueFactory(cellData -> {
			return cellData.getValue().getIsAvailable() ? new SimpleStringProperty("Yes")
					: new SimpleStringProperty("No");
		});

		if(books != null && !books.isEmpty())
		{
			tvBooks.setItems(books);
		}
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
		publicationDatePicker.setValue(null);
		tfNumberOfPages.clear();
		taDescription.clear();
		cbAvailable.setSelected(false);

		lvAuthors.getItems().clear();

		ObservableList<Author> authors = FXCollections
				.observableArrayList(AuthorDataAccess.loadAllAuthors("lastNameAZ"));

		lvAuthors.getItems().addAll(authors);
	}

	@FXML
	private void addBook() {
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		String dailyPrice = tfDailyPrice.getText();
		String publisher = tfPublisher.getText() != null && !tfPublisher.getText().isEmpty() ? tfPublisher.getText()
				: null;
		String description = taDescription.getText() != null && !taDescription.getText().isEmpty()
				? taDescription.getText()
				: null;
		LocalDate publicationDateLD = publicationDatePicker.getValue();
		Date publicationDate = publicationDateLD != null ? DateHelper.localDateToDate(publicationDateLD) : null;
		String numberOfPagesString = tfNumberOfPages.getText();
		List<Author> selectedAuthors = lvAuthors.getSelectionModel().getSelectedItems();

		Alert frontendAlert = BookValidator.frontendValidatorForCreatingAndUpdatingBook(title, dailyPrice,
				selectedAuthors, numberOfPagesString, null);

		if (frontendAlert == null) {
			Integer numberOfPages = numberOfPagesString != null && !numberOfPagesString.isEmpty()
					? Integer.valueOf(numberOfPagesString)
					: null;
			String result = BookDataAccess.createBook(title, description, location, new BigDecimal(dailyPrice),
					numberOfPages, publisher, publicationDate, selectedAuthors);
			Alert backendAlert = BookValidator.backendBookValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllBooks();
			}
			backendAlert.showAndWait();
		} else {
			frontendAlert.showAndWait();
		}
	}

	@FXML
	private void updateBook() {
		String bookId = tfBookId.getText();
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		String dailyPrice = tfDailyPrice.getText();
		String publisher = tfPublisher.getText() != null && !tfPublisher.getText().isEmpty() ? tfPublisher.getText()
				: null;
		String description = taDescription.getText() != null && !taDescription.getText().isEmpty()
				? taDescription.getText()
				: null;
		List<Author> selectedAuthors = lvAuthors.getSelectionModel().getSelectedItems();
		LocalDate publicationDateLD = publicationDatePicker.getValue();
		Date publicationDate = publicationDateLD != null ? DateHelper.localDateToDate(publicationDateLD) : null;
		String numberOfPagesString = tfNumberOfPages.getText();

		Alert frontendValidator = BookValidator.frontendValidatorForCreatingAndUpdatingBook(title, dailyPrice,
				selectedAuthors, numberOfPagesString, bookId);

		if (frontendValidator == null) {
			Integer numberOfPages = numberOfPagesString != null && !numberOfPagesString.isEmpty()
					? Integer.valueOf(numberOfPagesString)
					: null;
			String result = BookDataAccess.updateBook(Integer.valueOf(bookId), title, description, location,
					new BigDecimal(dailyPrice), numberOfPages, publisher, publicationDate,
					selectedAuthors);
			Alert backendAlert = BookValidator.backendBookValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllBooks();
			}
			backendAlert.showAndWait();
		} else {
			frontendValidator.showAndWait();
		}
	}

	@FXML
	private void loadBook() {
		String bookId = tfBookId.getText();

		Alert frontendAlert = BookValidator.frontendValidatorForSearchingAndDeletingBook(bookId);

		if (frontendAlert == null) {

			Book book = BookDataAccess.loadBook(Integer.valueOf(bookId));
			Alert backendAlert = BookValidator.backendValidatorForBookSearch(book);
			
			if(backendAlert == null)
			{
				tfTitle.setText(book.getTitle());
				tfLocation.setText(book.getLocation() != null && !book.getLocation().isEmpty() ? book.getLocation() : "");
				tfDailyPrice.setText(book.getDailyPrice().toString());
				tfPublisher.setText(book.getPublisher() != null && !book.getPublisher().isEmpty() ? book.getPublisher() : "");
				if(book.getPublicationDate() != null)
				{
					publicationDatePicker.setValue(DateHelper.dateToLocalDate(book.getPublicationDate()));
				}
				tfNumberOfPages.setText(book.getNumberOfPages() != null ? String.valueOf(book.getNumberOfPages()) : "");
				taDescription.setText(book.getDescription() != null && !book.getDescription().isEmpty() ? book.getDescription() : "");
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
			else
			{
				backendAlert.showAndWait();
			}
		} else {
			frontendAlert.showAndWait();
		}
	}

	@FXML
	private void deleteBook() {
		String bookId = tfBookId.getText();

		Alert frontendAlert = BookValidator.frontendValidatorForSearchingAndDeletingBook(bookId);

		if (frontendAlert == null) {
			String result = BookDataAccess.deleteBook(Integer.valueOf(bookId));
			Alert backendAlert = BookValidator.backendBookValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllBooks();
			}
			backendAlert.showAndWait();
		} else {
			frontendAlert.showAndWait();
		}
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
