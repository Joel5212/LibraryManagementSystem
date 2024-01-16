package validator;

import entity.Author;
import entity.Producer;
import entity.Student;
import helpers.NumberHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProducerValidator {

	public static Alert frontendLoanValidatorForCreatingAndUpdatingProducer(String firstName, String lastName, String producerId) {

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
		else if(producerId != null && !producerId.isEmpty() && !NumberHelper.isNumeric(producerId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Producer Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontendLoanValidatorForSearchingAndDeletingProducer(String producerId) {

		Alert alert = null;

		if (producerId == null || producerId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Producer Id field is empty!");
		} else if (producerId != null && !NumberHelper.isNumeric(producerId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Producer Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backendProducerValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("producerNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Producer not found!");
			return alert;
		} else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Producer has been created!");
			return alert;
		} else if (result.equals("updated")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Updated!");
			alert.setHeaderText("Producer has been updated!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Producer has been deleted!");
			return alert;
		}
		return alert;
	}
	
	public static Alert backendLoanValidatorForProducerSearch(Producer producer) {

		Alert alert = null;

		if (producer == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Producer not found!");
			return alert;
		}

		return alert;
	}
}