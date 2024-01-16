package controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import entity.Loan;
import entity.LoanComplete;
import entity.LoanReport;
import helpers.DateHelper;
import helpers.LoanCalcHelper;
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
	private TableColumn<LoanReport, Integer> colLoanId;
	@FXML
	private TableColumn<LoanReport, String> colItem;
	@FXML
	private TableColumn<LoanReport, String> colStudent;
	@FXML
	private TableColumn<LoanReport, String> colDailyPrice;
	@FXML
	private TableColumn<LoanReport, String> colStartDate;
	@FXML
	private TableColumn<LoanReport, String> colDueDate;
	@FXML
	private TableColumn<LoanReport, String> colReturnedDate;
	@FXML
	private TableColumn<LoanReport, Integer> colDaysOverdue;
	@FXML
	private TableColumn<LoanReport, String> colOverdueFine;
	@FXML
	private TableColumn<LoanReport, String> colTotalPayment;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<LoanComplete> loansComplete = LoanDataAccess.loadCompletedLoansForRevenueReport();

		ObservableList<Loan> loans = LoanDataAccess.loadLoans(false);

		ObservableList<LoanReport> loansRevenueReport = FXCollections.observableArrayList();

		int i = 0;
		int j = 0;
		while ((i < loans.size() && j < loansComplete.size())) {
			if (loans.get(i).getLoanId() < loansComplete.get(j).getLoanId()) {
				LoanReport loanReport = new LoanReport();
				loanReport.setLoanId(loans.get(i).getLoanId());
				loanReport
						.setItem(loans.get(i).getItem().getTitle() + " (" + loans.get(i).getItem().getItemId() + ") ");
				loanReport.setStudent(
						loans.get(i).getStudent().getName() + " (" + loans.get(i).getStudent().getStudentId() + ") ");
				Date startDate = loans.get(i).getStartDate();
				loanReport.setStartDate(startDate);
				Date dueDate = loans.get(i).getDueDate();
				loanReport.setDueDate(dueDate);
				loanReport.setReturnedDate(null);
				BigDecimal dailyPrice = loans.get(i).getItem().getDailyPrice();
				loanReport.setDailyPrice(dailyPrice);
				Integer daysOverdue = LoanCalcHelper.daysOverdue(loans.get(i).getDueDate());
				loanReport.setDaysOverdue(daysOverdue);
				loanReport.setOverdueFine(LoanCalcHelper.calcOverdueFine(daysOverdue, dailyPrice));
				loanReport.setTotalPayment(LoanCalcHelper.currentLoanPayment(dueDate, startDate, dailyPrice));
				loansRevenueReport.add(loanReport);

				i++;
			} else if (loans.get(i).getLoanId() > loansComplete.get(j).getLoanId()) {
				LoanReport loanReport = new LoanReport();
				loanReport.setLoanId(loansComplete.get(j).getLoanId());
				loanReport.setItem(loansComplete.get(j).getItem());
				loanReport.setStudent(loansComplete.get(j).getStudent());
				loanReport.setStartDate(loansComplete.get(j).getStartDate());
				loanReport.setDueDate(loansComplete.get(j).getDueDate());
				loanReport.setReturnedDate(loansComplete.get(j).getReturnedDate());
				loanReport.setDailyPrice(loansComplete.get(j).getDailyPrice());
				loanReport.setDaysOverdue(loansComplete.get(j).getDaysOverdue());
				loanReport.setOverdueFine(loansComplete.get(j).getOverdueFine());
				loanReport.setTotalPayment(loansComplete.get(j).getTotalPayment());
				loansRevenueReport.add(loanReport);

				j++;
			}
		}

		while (i < loans.size()) {
			LoanReport loanReport = new LoanReport();
			loanReport.setLoanId(loans.get(i).getLoanId());
			loanReport.setItem(loans.get(i).getItem().getTitle() + " (" + loans.get(i).getItem().getItemId() + ") ");
			loanReport.setStudent(
					loans.get(i).getStudent().getName() + " (" + loans.get(i).getStudent().getStudentId() + ") ");
			Date startDate = loans.get(i).getStartDate();
			loanReport.setStartDate(startDate);
			Date dueDate = loans.get(i).getDueDate();
			loanReport.setDueDate(dueDate);
			loanReport.setReturnedDate(null);
			BigDecimal dailyPrice = loans.get(i).getItem().getDailyPrice();
			loanReport.setDailyPrice(dailyPrice);
			Integer daysOverdue = LoanCalcHelper.daysOverdue(loans.get(i).getDueDate());
			loanReport.setDaysOverdue(daysOverdue);
			loanReport.setOverdueFine(LoanCalcHelper.calcOverdueFine(daysOverdue, dailyPrice));
			loanReport.setTotalPayment(LoanCalcHelper.currentLoanPayment(dueDate, startDate, dailyPrice));
			loansRevenueReport.add(loanReport);

			i++;
		}

		while (j < loansComplete.size()) {
			LoanReport loanReport = new LoanReport();
			loanReport.setLoanId(loansComplete.get(j).getLoanId());
			loanReport.setItem(loansComplete.get(j).getItem());
			loanReport.setStudent(loansComplete.get(j).getStudent());
			loanReport.setStartDate(loansComplete.get(j).getStartDate());
			loanReport.setDueDate(loansComplete.get(j).getDueDate());
			loanReport.setReturnedDate(loansComplete.get(j).getReturnedDate());
			loanReport.setDailyPrice(loansComplete.get(j).getDailyPrice());
			loanReport.setDaysOverdue(loansComplete.get(j).getDaysOverdue());
			loanReport.setOverdueFine(loansComplete.get(j).getOverdueFine());
			loanReport.setTotalPayment(loansComplete.get(j).getTotalPayment());
			loansRevenueReport.add(loanReport);

			j++;
		}

		if (loansRevenueReport != null && !loansRevenueReport.isEmpty()) {
			colLoanId.setCellValueFactory(new PropertyValueFactory<LoanReport, Integer>("loanId"));
			colItem.setCellValueFactory(new PropertyValueFactory<LoanReport, String>("item"));
			colStudent.setCellValueFactory(new PropertyValueFactory<LoanReport, String>("student"));
			colStartDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getStartDate()));
			});
			colDueDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getDueDate()));
			});
			colDailyPrice.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getDailyPrice());
			});
			colReturnedDate.setCellValueFactory(cellData -> {
				Date returnedDate = cellData.getValue().getReturnedDate();

				if (returnedDate != null) {
					return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(returnedDate));
				}
				return null;

			});
			colDaysOverdue.setCellValueFactory(new PropertyValueFactory<LoanReport, Integer>("daysOverdue"));
			colOverdueFine.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getOverdueFine());
			});
			colTotalPayment.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getTotalPayment());
			});

			if (loansRevenueReport != null && !loansRevenueReport.isEmpty()) {
				tvLoanReport.setItems(loansRevenueReport);
			} else {
				tvLoanReport.setItems(null);
			}
		}

	}

}
