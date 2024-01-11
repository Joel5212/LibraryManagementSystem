package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.math.BigDecimal;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import persistence.DocumentaryDataAccess;
import persistence.ProducerDataAccess;

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
	private TextField tfReleaseDate;
	@FXML
	private TextField tfLength;
	@FXML
	private TextArea taDescription;
	@FXML
	private ListView lvProducers;
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
	private TableColumn<Documentary, Integer> colLength;
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
		colLocation.setCellValueFactory(new PropertyValueFactory<Documentary, String>("location"));
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
		colLength.setCellValueFactory(new PropertyValueFactory<Documentary, Integer>("length"));
		colReleaseDate.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(DateHelper.dateToYYYYMMddDate(cellData.getValue().getReleaseDate()));
		});
		colLocation.setCellValueFactory(new PropertyValueFactory<Documentary, String>("location"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Documentary, String>("description"));
		colAvailable.setCellValueFactory(cellData -> {
			return cellData.getValue().getIsAvailable() ? new SimpleStringProperty("Yes")
					: new SimpleStringProperty("No");
		});

		if (documentaries != null && documentaries.size() != 0) {
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
		tfReleaseDate.clear();
		tfLength.clear();
		taDescription.clear();
		cbAvailable.setSelected(false);

		lvProducers.getItems().clear();

		ObservableList<Producer> producers = FXCollections
				.observableArrayList(ProducerDataAccess.loadAllProducers("lastNameAZ"));

		lvProducers.getItems().addAll(producers);

	}

	@FXML
	private void createDocumentary() {
		// TODO Auto-generated method stub
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		BigDecimal dailyPrice = new BigDecimal(tfDailyPrice.getText());
		Date releaseDate = Date.valueOf(tfReleaseDate.getText());
		Integer length = Integer.valueOf(tfLength.getText());
		String description = taDescription.getText();

		List<Producer> selectedProducers = lvProducers.getSelectionModel().getSelectedItems();

		DocumentaryDataAccess.createDocumentary(title, description, location, dailyPrice, length,
				releaseDate, selectedProducers);

		clearFields();
		showAllDocumentaries();
	}

	@FXML
	private void updateDocumentary() {
		Integer bookId = Integer.valueOf(tfDocumentaryId.getText());
		boolean available = cbAvailable.isSelected();
		String title = tfTitle.getText();
		String location = tfLocation.getText();
		BigDecimal dailyPrice = new BigDecimal(tfDailyPrice.getText());
		Date releaseDate = Date.valueOf(tfReleaseDate.getText());
		Integer length = Integer.valueOf(tfLength.getText());
		String description = taDescription.getText();

		List<Producer> selectedAuthors = lvProducers.getSelectionModel().getSelectedItems();

		DocumentaryDataAccess.updateDocumentary(bookId, available, title, description, location, dailyPrice, length,
				releaseDate, selectedAuthors);

		clearFields();
		showAllDocumentaries();
	}

	@FXML
	private void loadDocumentary() {
		Integer documentaryId = Integer.valueOf(tfDocumentaryId.getText());

		Documentary documentary = DocumentaryDataAccess.loadDocumentary(documentaryId);

		tfTitle.setText(documentary.getTitle());
		tfLocation.setText(documentary.getLocation());
		tfDailyPrice.setText(documentary.getDailyPrice().toString());
		tfReleaseDate.setText(DateHelper.dateToYYYYMMddDate(documentary.getReleaseDate()));
		tfLength.setText(String.valueOf(documentary.getLength()));
		taDescription.setText(documentary.getDescription());
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
	}

	@FXML
	private void deleteDocumentary() {
		Integer bookId = Integer.valueOf(tfDocumentaryId.getText());

		DocumentaryDataAccess.deleteDocumentary(bookId);

		clearFields();
		showAllDocumentaries();
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
