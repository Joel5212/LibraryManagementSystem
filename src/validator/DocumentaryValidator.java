package validator;

import java.util.List;

import entity.Author;
import entity.Book;
import entity.Documentary;
import entity.Producer;
import helpers.NumberHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DocumentaryValidator {

	public static Alert frontendValidatorForCreatingAndUpdatingDocumentary(String title, String dailyPrice, List<Producer> producers, String length, String documentaryId) {

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
		else if(producers == null || producers.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Producers not selected/No Producers available!");
			return alert;
		} 
		else if(!length.isEmpty() && !NumberHelper.isNumeric(length)){
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Docuementary Length has to be numeric!");
			return alert;
		}
		else if(documentaryId != null && !documentaryId.isEmpty() && !NumberHelper.isNumeric(documentaryId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Book Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontendValidatorForSearchingAndDeletingDocumentary(String documentaryId) {

		Alert alert = null;

		if (documentaryId == null || documentaryId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Documentary Id field is empty!");
		} else if (documentaryId != null && !NumberHelper.isNumeric(documentaryId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Documentary Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backendDocumentaryValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("documentaryNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Documentary not found!");
			return alert;
		} else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Documentary has been created!");
			return alert;
		} else if (result.equals("updated")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Updated!");
			alert.setHeaderText("Documentary has been updated!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Documentary has been deleted!");
			return alert;
		}
		return alert;
	}
	
	public static Alert backendValidatorForDcoumentarySearch(Documentary documentary) {

		Alert alert = null;

		if (documentary == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Documentary not found!");
			return alert;
		}

		return alert;
	}
}