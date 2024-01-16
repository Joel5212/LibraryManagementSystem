package validator;

import entity.Student;
import helpers.NumberHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StudentValidator {

	public static Alert frontendLoanValidatorForCreatingAndUpdatingStudent(String name, String email, String year, String studentId) {

		Alert alert = null;

		if (name.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Name is empty!");
			return alert;

		} else if (email.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Email is empty!");
			return alert;
		} 
		if (studentId != null && studentId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Student Id is empty!");
			return alert;
		}
		else if (year.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Year is empty!");
			return alert;
		} else if (!year.isEmpty() && !NumberHelper.isNumeric(year)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Year has to be numeric!");
			return alert;
		} else if(studentId != null && !studentId.isEmpty() && !NumberHelper.isNumeric(studentId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontendLoanValidatorForSearchingAndDeletingStudent(String studentId) {

		Alert alert = null;

		if (studentId == null || studentId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student Id field is empty!");
		} else if (studentId != null && !NumberHelper.isNumeric(studentId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backendStudentValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("studentNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student not found!");
			return alert;
		} else if (result.equals("dependency")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Student has loans so student cannot be deleted!");
			return alert;
		}else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Student has been created!");
			return alert;
		} else if (result.equals("updated")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Updated!");
			alert.setHeaderText("Student has been updated!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Student has been deleted!");
			return alert;
		} 
		return alert;
	}
	
	public static Alert backendLoanValidatorForStudentSearch(Student student) {

		Alert alert = null;

		if (student == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student not found!");
			return alert;
		}

		return alert;
	}
}