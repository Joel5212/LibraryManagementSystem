package validator;

import java.util.Date;
import java.util.List;

import entity.Loan;
import helpers.LoanCalcHelper;
import helpers.DateHelper;
import helpers.IntegerHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoanValidator {

	public static Alert frontEndLoanValidatorForCreatingLoan(String itemId, String studentId, Date dueDate) {

		Alert alert = null;

		if (itemId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Item Id is empty");
			return alert;

		} else if (studentId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Student Id is empty!");
			return alert;
		}else if (dueDate == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Select a Due Date!");
			return alert;
		} else if (!itemId.isEmpty() && !IntegerHelper.isNumeric(itemId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Item Id has to be numeric!");
			return alert;

		} else if (!studentId.isEmpty() && !IntegerHelper.isNumeric(studentId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Student Id has to be numeric!");
			return alert;

		} else if (dueDate.before(LoanCalcHelper.getCurrentDate())) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Due Date cannot be before today!");
			return alert;

		} else if (LoanCalcHelper.isMoreThanSixMonthsLater(DateHelper.dateToLocalDate(LoanCalcHelper.getCurrentDate()),
				DateHelper.dateToLocalDate(dueDate))) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Date Error!");
			alert.setContentText("Due Date cannot be more than six months later!");
			return alert;
		}
		return alert;
	}

	public static Alert frontEndLoanValidatorForLoanSearch(String loanId, String itemId, String studentId) {

		Alert alert = null;

		if (loanId == null && itemId == null && studentId == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("All fields for searching are empty!");
		} else if (loanId != null && !IntegerHelper.isNumeric(loanId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id has to be numeric!");
			return alert;
		} else if (itemId != null && !IntegerHelper.isNumeric(itemId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Item Id has to be numeric!");
			return alert;
		} else if (studentId != null && !IntegerHelper.isNumeric(studentId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setContentText("Student Id has to be numeric!");
			return alert;
		}
		return alert;
	}

	public static Alert frontEndLoanValidatorForReturningItem(String loanId) {

		Alert alert = null;

		if (loanId == null || loanId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id field is empty!");
		}
		else if (loanId != null && !IntegerHelper.isNumeric(loanId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id has to be numeric!");
			return alert;
		} 
		return alert;
	}
	
	public static Alert frontEndLoanValidatorForDeletingLoan(String loanId) {
		
		Alert alert = null;

		if (loanId == null || loanId.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id field is empty!");
		}
		else if (loanId != null && !IntegerHelper.isNumeric(loanId)) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan Id has to be numeric!");
			return alert;
		} 
		return alert;
	}

	public static Alert backEndLoanValidator(String result) {

		Alert alert = null;

		if (result.equals("error")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("");
		} else if (result.equals("itemNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Item not found!");
			return alert;
		} else if (result.equals("itemUnavailable")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Item is unavailable!");
			return alert;
		} else if (result.equals("loanNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan not found!");
			return alert;
		} else if (result.equals("studentNotFound")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student not found!");
			return alert;
		} else if (result.equals("overdueLoans")) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student has Overdue Loans!");
			return alert;
		} else if (result.equals("returned")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Returned!");
			alert.setHeaderText("Loan has been returned!");
			return alert;
		} else if (result.equals("created")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Created!");
			alert.setHeaderText("Loan has been created!");
			return alert;
		} else if (result.equals("deleted")) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Deleted!");
			alert.setHeaderText("Loan has been deleted!");
			return alert;
		}
		return alert;
	}

	public static Alert backEndLoanValidatorForLoanSearchUsingLoanId(Loan loan) {

		Alert alert = null;

		if (loan == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan not found using Loan Id!");
			return alert;
		}

		return alert;
	}

	public static Alert backEndLoanValidatorForLoanSearchUsingItemId(Loan loan) {

		Alert alert = null;

		if (loan == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan not found using Item Id!");
			return alert;
		}

		return alert;
	}

	public static Alert backEndLoanValidatorForLoanSearchUsingStudentId(List<Loan> loans) {

		Alert alert = null;

		if (loans == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Loan(s) not found using Student Id!");
			return alert;
		} else if (loans.isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error!");
			alert.setHeaderText("Student has no loans!");
			return alert;
		}

		return alert;
	}
}