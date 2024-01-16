package validator;

import entity.Author;
import entity.Student;
import helpers.NumberHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AuthorValidator {

	public static Alert frontendLoanValidatorForCreatingAndUpdatingAuthor(String firstName, String lastName, String authorId) {

		Alert alert = null;

		if (firstName.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Name is empty!");
			return alert;

		} else if (lastName.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Name is empty!");
			return alert;
		} 
		else if(authorId != null && !authorId.isEmpty() && !NumberHelper.isNumeric(authorId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Author Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontendLoanValidatorForSearchingAndDeletingAuthor(String authorId) {

		Alert alert = null;

		if (authorId == null || authorId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Author Id field is empty!");
		} else if (authorId != null && !NumberHelper.isNumeric(authorId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Author Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backendAuthorValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("authorNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Author not found!");
			return alert;
		} else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Author has been created!");
			return alert;
		} else if (result.equals("updated")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Updated!");
			alert.setHeaderText("Author has been updated!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Author has been deleted!");
			return alert;
		}
		return alert;
	}
	
	public static Alert backendLoanValidatorForAuthorSearch(Author author) {

		Alert alert = null;

		if (author == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Author not found!");
			return alert;
		}

		return alert;
	}
}