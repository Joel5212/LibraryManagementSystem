package validator;

import java.util.List;

import entity.Author;
import entity.Book;
import entity.Student;
import helpers.NumberHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BookValidator {

	public static Alert frontendValidatorForCreatingAndUpdatingBook(String title, String dailyPrice, List<Author> authors, String numberOfPages, String bookId) {

		Alert alert = null;

		if(title.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Title is empty!");
			return alert;

		} else if(dailyPrice.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Daily Price is empty!");
			return alert;
		} else if(!NumberHelper.isDecimal(dailyPrice)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Daily Price has to be numeric!");
			return alert;
		}
		else if(authors == null || authors.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Authors not selected/No Authors available!");
			return alert;
		} 
		else if(!numberOfPages.isEmpty() && !NumberHelper.isNumeric(numberOfPages)){
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Number of pages has to be numeric!");
			return alert;
		}
		else if(bookId != null && !bookId.isEmpty() && !NumberHelper.isNumeric(bookId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontendValidatorForSearchingAndDeletingBook(String bookId) {

		Alert alert = null;

		if (bookId == null || bookId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book Id field is empty!");
		} else if (bookId != null && !NumberHelper.isNumeric(bookId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backendBookValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("bookNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book not found!");
			return alert;
		} else if (result.equals("dependency")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Item is being loaned so item cannot be deleted!");
			return alert;
		}else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Book has been created!");
			return alert;
		} else if (result.equals("updated")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Updated!");
			alert.setHeaderText("Book has been updated!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Book has been deleted!");
			return alert;
		}
		return alert;
	}
	
	public static Alert backendValidatorForBookSearch(Book book) {

		Alert alert = null;

		if (book == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book not found!");
			return alert;
		}

		return alert;
	}
}