package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.math.BigDecimal;

import entity.Author;
import entity.Book;
import entity.Documentary;
import entity.Producer;
import helpers.LoanCalcHelper;
import helpers.DateHelper;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistence.BookDataAccess;
import persistence.DocumentaryDataAccess;
import persistence.ProducerDataAccess;
import validator.BookValidator;
import validator.DocumentaryValidator;

public class DocumentariesController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfDocumentaryId;
	@FXML
	private TextField tfTitle;
	@FXML
	private TextField tfLocation;
	@FXML
	private TextField tfDailyPrice;
	@FXML
	private DatePicker releaseDatePicker;
	@FXML
	private TextField tfLength;
	@FXML
	private TextArea taDescription;
	@FXML
	private ListView<Producer> lvProducers;
	@FXML
	private CheckBox cbAvailable;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnUpdate;
	@FXML
	private CheckBox cbTitleAZ;
	@FXML
	private CheckBox cbPriceLH;
	@FXML
	private CheckBox cbTitleZA;
	@FXML
	private CheckBox cbPriceHL;
	@FXML
	private TableView tvDocumentaries;
	@FXML
	private TableColumn<Documentary, Integer> colDocumentaryId;
	@FXML
	private TableColumn<Documentary, String> colTitle;
	@FXML
	private TableColumn<Documentary, String> colLocation;
	@FXML
	private TableColumn<Documentary, String> colProducers;
	@FXML
	private TableColumn<Documentary, String> colDailyPrice;
	@FXML
	private TableColumn<Documentary, String> colReleaseDate;
	@FXML
	private TableColumn<Documentary, String> colLength;
	@FXML
	private TableColumn<Documentary, String> colDescription;
	@FXML
	private TableColumn<Documentary, String> colAvailable;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showAllDocumentaries();

		ObservableList<Producer> producers = FXCollections
				.observableArrayList(ProducerDataAccess.loadAllProducers("lastNameAZ"));

		if (producers != null && producers.size() != 0) {
			lvProducers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			lvProducers.getItems().addAll(producers);
		}

		cbAvailable.setSelected(false);

	}

	public void showAllDocumentaries() {
		ObservableList<Documentary> documentaries = null;

		if (cbTitleAZ.isSelected() && cbPriceHL.isSelected()) {
			documentaries = FXCollections
					.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleAZ AND priceHL"));
		} else if (cbTitleZA.isSelected() && cbPriceLH.isSelected()) {
			documentaries = FXCollections
					.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleZA AND priceLH"));
		} else if (cbTitleAZ.isSelected() && cbPriceLH.isSelected()) {
			documentaries = FXCollections
					.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleAZ AND priceLH"));
		} else if (cbTitleZA.isSelected() && cbPriceHL.isSelected()) {
			documentaries = FXCollections
					.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleZA AND priceHL"));
		} else if (cbTitleZA.isSelected()) {
			documentaries = FXCollections.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleZA"));
		} else if (cbTitleAZ.isSelected()) {
			documentaries = FXCollections.observableList(DocumentaryDataAccess.loadAllDocumentaries("titleAZ"));
		} else if (cbPriceLH.isSelected()) {
			documentaries = FXCollections.observableList(DocumentaryDataAccess.loadAllDocumentaries("priceLH"));
		} else if (cbPriceHL.isSelected()) {
			documentaries = FXCollections.observableList(DocumentaryDataAccess.loadAllDocumentaries("priceHL"));
		} else if (!cbTitleAZ.isSelected() && !cbTitleZA.isSelected() && !cbPriceLH.isSelected()
				&& !cbPriceHL.isSelected()) {
			documentaries = FXCollections.observableList(DocumentaryDataAccess.loadAllDocumentaries("none"));
		}

		colDocumentaryId.setCellValueFactory(new PropertyValueFactory<Documentary, Integer>("itemId"));
		colTitle.setCellValueFactory(new PropertyValueFactory<Documentary, String>("title"));
		colProducers.setCellValueFactory(cellData -> {

			StringBuilder sb = new StringBuilder();

			for (Producer producer : cellData.getValue().getProducers()) {
				sb.append(producer.toString() + "  ");
			}

			return new SimpleStringProperty(sb.toString());

		});
		colDailyPrice.setCellValueFactory(cellData -> {
			return new SimpleStringProperty("$" + cellData.getValue().getDailyPrice());
		});
		colLength.setCellValueFactory(cellData -> {
			Integer length = cellData.getValue().getLength();

			if (length != null) {
				return new SimpleStringProperty(length.toString());
			} else {
				return new SimpleStringProperty("");
			}
		});

		colReleaseDate.setCellValueFactory(cellData -> {
			Date date = cellData.getValue().getReleaseDate();

			if (date != null) {
				return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getReleaseDate()));
			} else {
				return new SimpleStringProperty("");
			}
		});
		colLocation.setCellValueFactory(cellData -> {
			String location = cellData.getValue().getLocation();

			if (location != null) {
				return new SimpleStringProperty(location);
			} else {
				return new SimpleStringProperty("");
			}
		});
		colDescription.setCellValueFactory(cellData -> {
			String description = cellData.getValue().getDescription();

			if (description != null) {
				return new SimpleStringProperty(description);
			} else {
				return new SimpleStringProperty("");
			}
		});

		colAvailable.setCellValueFactory(cellData -> {
			return cellData.getValue().getIsAvailable() ? new SimpleStringProperty("Yes")
					: new SimpleStringProperty("No");
		});

		if (documentaries != null && !documentaries.isEmpty()) {
			tvDocumentaries.setItems(documentaries);
		}
	}

	@FXML
	private void showAllDocumentariesAfterAction(ActionEvent event) {

		ObservableList<Book> books = null;

		if (event.getSource() == cbPriceHL) {
			if (cbPriceHL.isSelected()) {
				cbPriceLH.setSelected(false);
			}
			showAllDocumentaries();
		} else if (event.getSource() == cbPriceLH) {
			if (cbPriceLH.isSelected()) {
				cbPriceHL.setSelected(false);
			}
			showAllDocumentaries();

		} else if (event.getSource() == cbTitleAZ) {
			if (cbTitleAZ.isSelected()) {
				cbTitleZA.setSelected(false);
			}
			showAllDocumentaries();

		} else if (event.getSource() == cbTitleZA) {
			if (cbTitleZA.isSelected()) {
				cbTitleAZ.setSelected(false);
			}
			showAllDocumentaries();
		}
	}

	@FXML
	private void clearFields() {
		tfDocumentaryId.clear();
		tfTitle.clear();
		tfLocation.clear();
		tfDailyPrice.clear();
		releaseDatePicker.setValue(null);
		tfLength.clear();
		taDescription.clear();
		cbAvailable.setSelected(false);

		lvProducers.getItems().clear();

		ObservableList<Producer> producers = FXCollections
				.observableArrayList(ProducerDataAccess.loadAllProducers("lastNameAZ"));

		lvProducers.getItems().addAll(producers);

	}

	@FXML
	private void addDocumentary() {
		// TODO Auto-generated method stub
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		String dailyPrice = tfDailyPrice.getText();
		String description = taDescription.getText() != null && !taDescription.getText().isEmpty()
				? taDescription.getText()
				: null;
		LocalDate releaseDateLD = releaseDatePicker.getValue();
		Date releaseDate = releaseDateLD != null ? DateHelper.localDateToDate(releaseDateLD) : null;
		String lengthString = tfLength.getText();
		List<Producer> selectedProducers = lvProducers.getSelectionModel().getSelectedItems();

		Alert frontendAlert = DocumentaryValidator.frontendValidatorForCreatingAndUpdatingDocumentary(title, dailyPrice,
				selectedProducers, lengthString, null);

		if (frontendAlert == null) {
			Integer length = lengthString != null && !lengthString.isEmpty() ? Integer.valueOf(lengthString) : null;
			String result = DocumentaryDataAccess.createDocumentary(title, description, location,
					new BigDecimal(dailyPrice), length, releaseDate, selectedProducers);
			Alert backendAlert = DocumentaryValidator.backendDocumentaryValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllDocumentaries();
			}
			backendAlert.showAndWait();
		} else {
			frontendAlert.showAndWait();
		}
	}

	@FXML
	private void updateDocumentary() {
		String documentaryId = tfDocumentaryId.getText();
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		String dailyPrice = tfDailyPrice.getText();
		String description = taDescription.getText() != null && !taDescription.getText().isEmpty()
				? taDescription.getText()
				: null;
		LocalDate releaseDateLD = releaseDatePicker.getValue();
		Date releaseDate = releaseDateLD != null ? DateHelper.localDateToDate(releaseDateLD) : null;
		String lengthString = tfLength.getText();
		List<Producer> selectedProducers = lvProducers.getSelectionModel().getSelectedItems();

		Alert frontendAlert = DocumentaryValidator.frontendValidatorForCreatingAndUpdatingDocumentary(title, dailyPrice,
				selectedProducers, lengthString, null);

		if (frontendAlert == null) {
			Integer numberOfPages = lengthString != null && !lengthString.isEmpty() ? Integer.valueOf(lengthString)
					: null;
			String result = DocumentaryDataAccess.updateDocumentary(Integer.valueOf(documentaryId), title, description,
					location, new BigDecimal(dailyPrice), numberOfPages, releaseDate, selectedProducers);
			Alert backendAlert = DocumentaryValidator.backendDocumentaryValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllDocumentaries();
			}
			backendAlert.showAndWait();
		} else {
			frontendAlert.showAndWait();
		}
	}

	@FXML
	private void loadDocumentary() {
		String documentaryId = tfDocumentaryId.getText();

		Alert frontendAlert = DocumentaryValidator.frontendValidatorForSearchingAndDeletingDocumentary(documentaryId);

		if (frontendAlert == null) {

			Documentary documentary = DocumentaryDataAccess.loadDocumentary(Integer.valueOf(documentaryId));
			Alert backendAlert = DocumentaryValidator.backendValidatorForDcoumentarySearch(documentary);

			if (backendAlert == null) {

				tfTitle.setText(documentary.getTitle());
				tfLocation.setText(documentary.getLocation() != null && !documentary.getLocation().isEmpty() ? documentary.getLocation() : "");
				tfDailyPrice.setText(documentary.getDailyPrice().toString());
				if (documentary.getReleaseDate() != null) {
					releaseDatePicker.setValue(DateHelper.dateToLocalDate(documentary.getReleaseDate()));
				}
				tfLength.setText(documentary.getLength() != null ? String.valueOf(documentary.getLength()) : "");
				taDescription.setText(documentary.getDescription() != null && !documentary.getDescription().isEmpty() ? documentary.getDescription() : "");
				cbAvailable.setSelected(documentary.getIsAvailable());

				List<Producer> allProducers = ProducerDataAccess.loadAllProducers("lastNameAZ");
				List<Producer> producers = documentary.getProducers();

				lvProducers.getItems().clear();

				int numOfSelectedItems = 0;
				for (int i = 0; i < allProducers.size(); i++) {
					for (int j = 0; j < producers.size(); j++) {
						if (allProducers.get(i).getProducerId() == producers.get(j).getProducerId()) {
							allProducers.remove(i);
							allProducers.add(0, producers.get(j));
							numOfSelectedItems++;
						}
					}
				}

				lvProducers.getItems().addAll(allProducers);

				while (numOfSelectedItems > 0) {
					lvProducers.getSelectionModel().select(allProducers.get(numOfSelectedItems - 1));
					numOfSelectedItems--;
				}

				lvProducers.getSelectionModel().setSelectionMode(null);

				showAllDocumentaries();
			} else {
				backendAlert.showAndWait();
			}
		} else {
			frontendAlert.showAndWait();
		}
	}

	@FXML
	private void deleteDocumentary() {
		String documentaryId = tfDocumentaryId.getText();

		Alert frontendAlert = DocumentaryValidator.frontendValidatorForSearchingAndDeletingDocumentary(documentaryId);

		if (frontendAlert == null) {
			String result = DocumentaryDataAccess.deleteDocumentary(Integer.valueOf(documentaryId));
			Alert backendAlert = DocumentaryValidator.backendDocumentaryValidator(result);
			if (backendAlert.getAlertType() == AlertType.CONFIRMATION) {
				clearFields();
				showAllDocumentaries();
			} else {
				backendAlert.showAndWait();
			}
		} else {
			frontendAlert.showAndWait();
		}
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
	public void authorsPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Authors.fxml"));
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
	public void booksPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Books.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void loansPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Loans.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
