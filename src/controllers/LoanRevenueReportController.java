package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entity.LoanComplete;
import helpers.DateHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistence.LoanDataAccess;

public class LoanRevenueReportController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TableView tvLoanReport;
	@FXML
	private TableColumn<LoanComplete, Integer> colLoanId;
	@FXML
	private TableColumn<LoanComplete, String> colItem;
	@FXML
	private TableColumn<LoanComplete, String> colStudent;
	@FXML
	private TableColumn<LoanComplete, String> colDailyPrice;
	@FXML
	private TableColumn<LoanComplete, String> colStartDate;
	@FXML
	private TableColumn<LoanComplete, String> colDueDate;
	@FXML
	private TableColumn<LoanComplete, String> colReturnedDate;
	@FXML
	private TableColumn<LoanComplete, Integer> colDaysOverdue;
	@FXML
	private TableColumn<LoanComplete, String> colOverdueFine;
	@FXML
	private TableColumn<LoanComplete, String> colTotalPayment;
	
//	public void showRevenueReport(ObservableList<Loan> loans) {
//
//		if (loans != null) {
//			colLoanId.setCellValueFactory(cellData -> {
//				return new SimpleStringProperty(cellData.getValue().getLoanId().toString());
//			});
//			colItem.setCellValueFactory(cellData -> {
//				String item = cellData.getValue().getItem().getTitle() + " ("
//						+ cellData.getValue().getItem().getItemId() + ") ";
//				return new SimpleStringProperty(cellData.getValue().getItem() != null ? item : "");
//			});
//			colStudent.setCellValueFactory(cellData -> {
//				String student = cellData.getValue().getStudent().getName() + " ("
//						+ cellData.getValue().getStudent().getStudentId() + ") ";
//				return new SimpleStringProperty(
//						cellData.getValue().getStudent() != null ? student : "");
//			});
//			colStartDate.setCellValueFactory(cellData -> {
//				return new SimpleStringProperty(LoanCalcHelper.dateToYYYYMMddDate(cellData.getValue().getStartDate()));
//			});
//			colDueDate.setCellValueFactory(cellData -> {
//				return new SimpleStringProperty(LoanCalcHelper.dateToYYYYMMddDate(cellData.getValue().getDueDate()));
//			});
//			colDailyPrice.setCellValueFactory(cellData -> {
//				return new SimpleStringProperty("$" + cellData.getValue().getItem().getDailyPrice().toString());
//			});
//			colStatus.setCellValueFactory(cellData -> {
//				return new SimpleStringProperty(cellData.getValue().isOverdue() ? "Overdue" : "Ongoing");
//			});
//
//			tvLoans.setItems(loans);
//		}
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		ObservableList<LoanComplete> loanRevenueReport = FXCollections.observableList(LoanDataAccess.loadCompletedLoansForRevenueReport());
		
		System.out.println(loanRevenueReport.get(0));
		
		if (loanRevenueReport != null) {
			colLoanId.setCellValueFactory(new PropertyValueFactory<LoanComplete, Integer>("loanId"));
			colItem.setCellValueFactory(new PropertyValueFactory<LoanComplete, String>("item"));
			colStudent.setCellValueFactory(new PropertyValueFactory<LoanComplete, String>("student"));
			colStartDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getStartDate()));
			});
			colDueDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getDueDate()));
			});
			colReturnedDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getReturnedDate()));
			});
			colDailyPrice.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getDailyPrice());
			});
			colDaysOverdue.setCellValueFactory(new PropertyValueFactory<LoanComplete, Integer>("daysOverdue"));
			colOverdueFine.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getOverdueFine());
			});
			colTotalPayment.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getTotalPayment());
			});
			
			tvLoanReport.setItems((ObservableList) loanRevenueReport);
		}
		
	}

}
