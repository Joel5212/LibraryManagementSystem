package presentation;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowAlerts{
	
	static void showAddedAlert(boolean addedCustomer, String name, String object) {
		if(addedCustomer)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(object + " Added!");
			alert.setContentText(name + " is added into the system!");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Error!");
			alert.setContentText("There was a problem with adding the " + object + " into the system.");
			alert.showAndWait();
		}
	}
	
	static void showNotFoundAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Error!");
		alert.setContentText("No Records Found!");
		alert.showAndWait();
	}
	
	static void showUpdatedAlert(boolean updatedCustomer, String object) {
		if(updatedCustomer)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(object + " Updated!");
			alert.setContentText(object + " Data Successfully Updated!");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Error!");
			alert.setContentText("There was a problem with updating the " + object);
			alert.showAndWait();
		}
	}
	
	static void showDeletedAlert(boolean deletedStudent) {
		if(deletedStudent)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Student Deleted!");
			alert.setContentText("Student is deleted!");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Error!");
			alert.setContentText("There was a problem with deleting the student");
			alert.showAndWait();
		}
	}
}