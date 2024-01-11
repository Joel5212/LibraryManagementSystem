package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Documentary;
import entity.Item;
import entity.Producer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import persistence.ProducerDataAccess;

public class ProducersController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField tfProducerId;
	@FXML
	private HBox tfAuthorName1;
	@FXML
	private TextField tfFirstName;
	@FXML
	private HBox tfAuthorName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfNationality;
	@FXML
	private TextField tfStyle;
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
	private CheckBox cbLastNameAZ;
	@FXML
	private CheckBox cbLastNameZA;
	@FXML
	private TableView tvProducers;
	@FXML
	private TableView tvDocumentaries;
	@FXML
	private TableColumn<Producer, Integer> colProducerId;
	@FXML
	private TableColumn<Producer, String> colName;
	@FXML
	private TableColumn<Producer, String> colNationality;
	@FXML
	private TableColumn<Producer, String> colStyle;
	@FXML
	private TableColumn<Item, String> colTitle;
	@FXML
	private TableColumn<Item, String> colLocation;
	@FXML
	private TableColumn<Item, String> colAvailable;
	@FXML
	private TableColumn<Item, Double> colDailyPrice;
	@FXML
	private TableColumn<Item, String> colDescription;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showAllProducers();
	}

	// Event Listener on Button[#btnClear].onAction
//	@FXML
//	public void handleButtonAction(ActionEvent event) {
//		// TODO Autogenerated
//
//		if (event.getSource() == btnClear) {
//			clearFields();
//		} else if (event.getSource() == btnAdd) {
//			addProducer();
//		} else if (event.getSource() == btnSearch) {
//			searchProducer();
//		} else if (event.getSource() == btnUpdate) {
//			updateProducer();
//		} else if (event.getSource() == btnDelete) {
//			deleteProducer();
//		}
//	}

	private void showAllProducers() {
		ObservableList<Producer> producers = null;

		if (cbLastNameAZ.isSelected() && !cbLastNameZA.isSelected()) {
			producers = FXCollections.observableList(ProducerDataAccess.loadAllProducers("lastNameAZ"));
		} else if (cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected()) {
			producers = FXCollections.observableList(ProducerDataAccess.loadAllProducers("lastNameZA"));
		} else if (!cbLastNameZA.isSelected() && !cbLastNameAZ.isSelected()) {
			producers = FXCollections.observableList(ProducerDataAccess.loadAllProducers("none"));
		}

		colProducerId.setCellValueFactory(new PropertyValueFactory<Producer, Integer>("producerId"));

		colName.setCellValueFactory(cellData -> {
			Producer data = cellData.getValue();
			return new SimpleStringProperty(data.getLastName() + ", " + data.getFirstName());
		});

		colNationality.setCellValueFactory(new PropertyValueFactory<Producer, String>("nationality"));
		colStyle.setCellValueFactory(new PropertyValueFactory<Producer, String>("style"));

		tvProducers.setItems(producers);
	}

	@FXML
	private void showAllProducersAfterAction(ActionEvent event) {

		ObservableList<Producer> producers = null;

		if (event.getSource() == cbLastNameAZ) {
			if (cbLastNameZA.isSelected()) {
				cbLastNameZA.setSelected(false);
			}
			showAllProducers();
		} else if (event.getSource() == cbLastNameZA) {
			if (cbLastNameAZ.isSelected()) {
				cbLastNameAZ.setSelected(false);
			}
			showAllProducers();

		}
	}

	public void showAllDocumentariesOfProducer(Producer producer) {
		ObservableList<Documentary> documentaries = FXCollections.observableList(producer.getDocumentaries());

		colTitle.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
		colLocation.setCellValueFactory(new PropertyValueFactory<Item, String>("location"));
		colAvailable.setCellValueFactory(new PropertyValueFactory<Item, String>("isAvailable"));
		colDailyPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("dailyPrice"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

		tvDocumentaries.setItems(documentaries);
	}

	@FXML
	private void addProducer() {
		// TODO Auto-generated method stub
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String nationality = tfNationality.getText();
		String style = tfStyle.getText();

		ProducerDataAccess.createProducer(firstName, lastName, style, nationality);
		showAllProducers();
		clearFields();
	}

	@FXML
	private void deleteProducer() {
		Integer producerId = Integer.valueOf(tfProducerId.getText());
		ProducerDataAccess.deleteProducer(producerId);
		showAllProducers();
		clearFields();

	}

	@FXML
	private void updateProducer() {
		Integer producerId = Integer.valueOf(tfProducerId.getText());
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		String nationality = tfNationality.getText();
		String subject = tfStyle.getText();

		ProducerDataAccess.updateProducer(producerId, firstName, lastName, subject, nationality);
		showAllProducers();

	}

	@FXML
	private void searchProducer() {
		// TODO Auto-generated method stub
		Integer producerId = Integer.valueOf(tfProducerId.getText());
		Producer producer = ProducerDataAccess.loadProducer(producerId);

		tfFirstName.setText(producer.getFirstName());
		tfLastName.setText(producer.getLastName());
		tfNationality.setText(producer.getNationality());
		tfStyle.setText(producer.getStyle());

		showAllDocumentariesOfProducer(producer);
	}

	@FXML
	private void clearFields() {
		tfProducerId.clear();
		tfFirstName.clear();
		tfLastName.clear();
		tfNationality.clear();
		tfStyle.clear();

	}

	public void showAllDocumentariesOfProducers(Producer producer) {

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
	public void booksPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Books.fxml"));
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
	
	@FXML
	public void loansPage(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("../presentation/Loans.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}