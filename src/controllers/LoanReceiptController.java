package controllers;

import entity.Loan;
import helpers.DateHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import persistence.LoanDataAccess;

public class LoanReceiptController {
	@FXML
	private Label tfLoanId;
	@FXML
	private Label tfItem;
	@FXML
	private Label tfStudent;
	@FXML
	private Label tfStartDate;
	@FXML
	private Label tfDueDate;
	
	public void setReceipt(Integer itemId)
	{
		Loan loan = LoanDataAccess.loadLoanUsingItemId(itemId);
		
		tfLoanId.setText(loan.getLoanId().toString());
		tfItem.setText(loan.getItem().getTitle() + " (" + loan.getItem().getItemId() + ")");
		tfStudent.setText(loan.getStudent().getName() + " (" + loan.getStudent().getStudentId() + ")");
		tfStartDate.setText(DateHelper.dateToYYYYMMddDate(loan.getStartDate()));
		tfDueDate.setText(DateHelper.dateToYYYYMMddDate(loan.getDueDate()));
	}

}
