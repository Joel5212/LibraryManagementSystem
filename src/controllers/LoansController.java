package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.Loan;
import helpers.DateHelper;
import helpers.LoanCalcHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.LoanDataAccess;
import validator.LoanValidator;

public class LoansController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfLoanId;
	@FXML
	private Label itemId;
	@FXML
	private TextField tfItemId;
	@FXML
	private TextField tfStudentId;
	@FXML
	private TextField tfStatus;
	@FXML
	private TextField tfPayment;
	@FXML
	private TextField tfDailyPrice;
	@FXML
	private DatePicker loanStartDatePicker;
	@FXML
	private DatePicker loanDueDatePicker;
	@FXML
	private Label itemId1;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnReturnItem;
	@FXML
	private Button btnRenewItem;
	@FXML
	private Button btnClear;
	@FXML
	private TableView tvLoans;
	@FXML
	private Button btnLoanReport;
	@FXML
	private Button btnGenerateLoanReport;
	@FXML
	private CheckBox cbShowOnlyOverdue;
	@FXML
	private TableColumn<Loan, String> colLoanId;
	@FXML
	private TableColumn<Loan, String> colItem;
	@FXML
	private TableColumn<Loan, String> colStudent;
	@FXML
	private TableColumn<Loan, String> colStartDate;
	@FXML
	private TableColumn<Loan, String> colDueDate;
	@FXML
	private TableColumn<Loan, String> colDailyPrice;
	@FXML
	private TableColumn<Loan, String> colStatus;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showAllLoans();
	}

	@FXML
	public void showAllLoans() {
		ObservableList<Loan> loans = null;

		if (cbShowOnlyOverdue.isSelected()) {
			loans = LoanDataAccess.loadLoans(true);
		} else {
			loans = LoanDataAccess.loadLoans(false);
		}
		showLoans(loans);
	}

	public void showLoans(ObservableList<Loan> loans) {

		if (loans != null && !loans.isEmpty()) {
			colLoanId.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(cellData.getValue().getLoanId().toString());
			});
			colItem.setCellValueFactory(cellData -> {
				String item = cellData.getValue().getItem().getTitle() + " ("
						+ cellData.getValue().getItem().getItemId() + ") ";
				return new SimpleStringProperty(cellData.getValue().getItem() != null ? item : "");
			});
			colStudent.setCellValueFactory(cellData -> {
				String student = cellData.getValue().getStudent().getName() + " ("
						+ cellData.getValue().getStudent().getStudentId() + ") ";
				return new SimpleStringProperty(cellData.getValue().getStudent() != null ? student : "");
			});
			colStartDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getStartDate()));
			});
			colDueDate.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getDueDate()));
			});
			colDailyPrice.setCellValueFactory(cellData -> {
				return new SimpleStringProperty("$" + cellData.getValue().getItem().getDailyPrice().toString());
			});
			colStatus.setCellValueFactory(cellData -> {
				return new SimpleStringProperty(cellData.getValue().isOverdue() ? "Overdue" : "Ongoing");
			});

			tvLoans.setItems(loans);
		}
	}

	@FXML
	private void clearFields() {
		tfLoanId.clear();
		tfItemId.clear();
		tfStudentId.clear();
		loanDueDatePicker.setValue(null);
		loanStartDatePicker.setValue(null);
		tfStatus.clear();
		tfPayment.clear();
		tfDailyPrice.clear();
	}

	@FXML
	private void returnItem() {
		Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForReturningItem(tfLoanId.getText());

		if (frontEndAlert == null) {
			String result = LoanDataAccess.returnItem(Integer.valueOf(tfLoanId.getText()));
			Alert backEndAlert = LoanValidator.backEndLoanValidator(result);
			if (backEndAlert.getAlertType() == AlertType.CONFIRMATION) {
				showAllLoans();
				clearFields();
			}
			backEndAlert.showAndWait();
		} else {
			frontEndAlert.showAndWait();
		}

		clearFields();
	}

	@FXML
	public void addLoan() throws IOException {

		Date startDate = LoanCalcHelper.getCurrentDate();
		LocalDate dueDateLD = loanDueDatePicker.getValue();
		Date dueDate = null;
		
		if(dueDateLD != null)
		{
			dueDate = DateHelper.localDateToDate(dueDateLD);
		}

		Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForCreatingLoan(tfItemId.getText(), tfStudentId.getText(),
				dueDate);

		if (frontEndAlert == null) {

			Integer itemId = Integer.valueOf(tfItemId.getText());
			Integer studentId = Integer.valueOf(tfStudentId.getText());

			String result = LoanDataAccess.createLoan(itemId, studentId, startDate, dueDate);
			Alert backEndAlert = LoanValidator.backEndLoanValidator(result);

			if (backEndAlert.getAlertType() == AlertType.CONFIRMATION) {
				showAllLoans();
				clearFields();
				generateLoanReceipt(itemId);
			} else {
				backEndAlert.showAndWait();
			}
		} else {
			frontEndAlert.showAndWait();
		}
	}

	@FXML
	public void deleteLoan() {
		String loanId = tfLoanId.getText();

		Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForDeletingLoan(loanId);

		if (frontEndAlert != null) {
			frontEndAlert.showAndWait();
		} else {
			String loanDeleted = LoanDataAccess.deleteLoan(Integer.valueOf(loanId));

			Alert backEndAlert = LoanValidator.backEndLoanValidator(loanDeleted);

			if (backEndAlert.getAlertType() == AlertType.CONFIRMATION) {
				showAllLoans();
				clearFields();
			}
			backEndAlert.showAndWait();
		}
	}

	@FXML
	public void searchLoan() {

		boolean isLoanIdEmpty = tfLoanId.getText().isEmpty();
		boolean isStudentIdEmpty = tfStudentId.getText().isEmpty();
		boolean isItemIdEmpty = tfItemId.getText().isEmpty();

		if (isLoanIdEmpty && isStudentIdEmpty && isItemIdEmpty) {
			Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForLoanSearch(null, null, null);
			frontEndAlert.showAndWait();
		} 
		//use loan Id even if there may be a student and item id
		else if (!isLoanIdEmpty) {

			Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForLoanSearch(tfLoanId.getText(), null, null);

			if (frontEndAlert == null) {
				Integer loanId = Integer.valueOf(tfLoanId.getText());

				Loan loan = LoanDataAccess.loadLoanUsingLoadId(loanId);
				Alert backEndAlert = LoanValidator.backEndLoanValidatorForLoanSearchUsingLoanId(loan);

				if (backEndAlert == null) {
					int studentId = loan.getStudent().getStudentId();
					String studentName = loan.getStudent().getName();
					int itemId = loan.getItem().getItemId();
					String itemTitle = loan.getItem().getTitle();
					Date dueDate = loan.getDueDate();
					Date startDate = loan.getStartDate();
					BigDecimal dailyPrice = loan.getItem().getDailyPrice();

					tfStudentId.setText(studentId + " (" + studentName + ")");
					tfItemId.setText(itemId + " (" + itemTitle + ")");
					loanDueDatePicker.setValue(DateHelper.dateToLocalDate(dueDate));
					loanStartDatePicker.setValue(DateHelper.dateToLocalDate(startDate));
					BigDecimal currentLoanPayment = LoanCalcHelper.currentLoanPayment(dueDate, startDate, dailyPrice);
					if (loan.isOverdue() == false) {
						tfStatus.setText("Not Overdue");
					} else if (loan.isOverdue() == true) {
						Integer amtOfDaysPassedAfterDueDate = LoanCalcHelper.daysOverdue(loan.getDueDate());
						String loanFine = String.format("%.2f", LoanCalcHelper.calcOverdueFine(amtOfDaysPassedAfterDueDate, dailyPrice));
						tfStatus.setText("Overdue by " + amtOfDaysPassedAfterDueDate + " day(s); Fine: $" + loanFine);
					}
					tfPayment.setText("$" + String.format("%.2f", currentLoanPayment));
					tfDailyPrice.setText("$" + String.format("%.2f", dailyPrice));
				} else {
					backEndAlert.showAndWait();
				}
			} else {
				frontEndAlert.showAndWait();
			}
			//use item id when there is no loanId and if there may or may not be a studentId
		} else if (isLoanIdEmpty && !isItemIdEmpty) {

			Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForLoanSearch(null, tfItemId.getText(), null);

			if (frontEndAlert == null) {
				Integer itemId = Integer.valueOf(tfItemId.getText());
				Loan loan = LoanDataAccess.loadLoanUsingItemId(itemId);
				Alert backEndAlert = LoanValidator.backEndLoanValidatorForLoanSearchUsingItemId(loan);

				if (backEndAlert == null) {
					int studentId = loan.getStudent().getStudentId();
					String studentName = loan.getStudent().getName();
					int loanId = loan.getLoanId();
					String itemTitle = loan.getItem().getTitle();
					Date dueDate = loan.getDueDate();
					Date startDate = loan.getStartDate();
					BigDecimal dailyPrice = loan.getItem().getDailyPrice();

					tfLoanId.setText(String.valueOf(loanId));
					tfStudentId.setText(studentName + " (" + studentId + ")");
					tfItemId.setText(itemTitle + " (" + itemId + ")");
					loanDueDatePicker.setValue(DateHelper.dateToLocalDate(dueDate));
					loanStartDatePicker.setValue(DateHelper.dateToLocalDate(startDate));
					BigDecimal currentLoanPayment = LoanCalcHelper.currentLoanPayment(dueDate, startDate, dailyPrice);
					if (loan.isOverdue() == false) {
						tfStatus.setText("Not Overdue");
					} else if (loan.isOverdue() == true) {
						int amtOfDaysPassedAfterDueDate = LoanCalcHelper.daysOverdue(loan.getDueDate());
						BigDecimal loanFine = LoanCalcHelper.calcOverdueFine(amtOfDaysPassedAfterDueDate, dailyPrice);
						tfStatus.setText("Overdue by " + amtOfDaysPassedAfterDueDate + " day(s); Fine: $" + loanFine);
					}
					tfPayment.setText("$" + String.format("%.2f", currentLoanPayment));
					tfDailyPrice.setText("$" + String.format("%.2f", dailyPrice));
				} else {
					backEndAlert.showAndWait();
				}
			} else {
				frontEndAlert.showAndWait();
			}
			//use student is where there is no loanId and if there may or may not be an itemId
		} else if (isLoanIdEmpty && !isStudentIdEmpty) {

			Alert frontEndAlert = LoanValidator.frontEndLoanValidatorForLoanSearch(null, null, tfStudentId.getText());

			if (frontEndAlert == null) {
				Integer studentId = Integer.valueOf(tfStudentId.getText());
				List<Loan> loans = LoanDataAccess.loadLoanUsingStudentId(studentId);
				System.out.println(loans);
				Alert backEndAlert = LoanValidator.backEndLoanValidatorForLoanSearchUsingStudentId(loans);

				if (backEndAlert == null) {
					showLoans(FXCollections.observableList(loans));
				} else {
					backEndAlert.showAndWait();
				}
			} else {
				frontEndAlert.showAndWait();
			}

		}
	}

	public void generateLoanReceipt(Integer itemId) throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Loan Receipt");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/LoanReceipt.fxml"));
		Parent root = loader.load();

		LoanReceiptController controller = loader.getController();

		controller.setReceipt(itemId);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void generateLoanRevenueReport(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Loan Revenue Report");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/LoanRevenueReport.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void authorsPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../presentation/Authors.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void booksPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Books.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void producersPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Producers.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void studentsPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Students.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void documentariesPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Documentaries.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
