package presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import persistence.LoanDataAccess;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class ManageBookPage {
	
	public static void manageBookPage(BorderPane root)
	{
		Label titleLbl  = new Label("Title: ");
		Label descriptionLbl = new Label("Descirption: ");
		Label locationLbl = new Label("Location: ");
		Label dailyPriceLbl = new Label("Daily Price: ");
		Label statusLbl = new Label("Status: ");
		CheckBox availableLbl = new CheckBox();
		Label numberOfPagesLbl = new Label("Number Of Pages: ");
		Label publisherLbl = new Label("Publisher: ");
		Label publicationDateLbl = new Label("Publication Date: ");
		
		TextField titleTxtField = new TextField();
		TextArea descriptionTxtField = new TextArea();
		TextField locationTxtField = new TextField();
		TextField dailyPriceTxtField = new TextField();
		TextField statusTxtField = new TextField();
		TextField numberOfPagesTxtField = new TextField();
		TextField publisherTxtField= new TextField();
		TextField publicationDateTxtField = new TextField();
		
		
		Label selectAuthorsLbl = new Label("Authors:");
		selectAuthorsLbl.getStyleClass().add("loans-label");
		
		ListView<String> listViewAuthors = new ListView<String>();
		
		
		HBox hbox1 = new HBox(titleLbl, titleTxtField, numberOfPagesLbl, numberOfPagesTxtField);
		HBox.setMargin(titleLbl, new Insets(0, 0, 0, 150));
		HBox.setMargin(titleTxtField, new Insets(0, 0, 0, 80));
		HBox.setMargin(numberOfPagesLbl, new Insets(0, 0, 0, 100));
		HBox.setMargin(numberOfPagesTxtField, new Insets(0, 0, 0, 80));
		
		HBox hbox2 = new HBox(publisherLbl, publisherTxtField);
		HBox.setMargin(publisherLbl, new Insets(0, 0, 0, 150));
		HBox.setMargin(publisherTxtField, new Insets(0, 0, 0, 50));
		
		HBox hbox3 = new HBox(publicationDateLbl, publicationDateTxtField);
		HBox.setMargin(publicationDateLbl, new Insets(0, 0, 0, 150));
		HBox.setMargin(publicationDateTxtField, new Insets(0, 0, 0, 20));
		
	
		
		Label bookManagementLbl = new Label("Book Management");
		HBox.setMargin(bookManagementLbl, new Insets(0, 0, 0, 50));
	    bookManagementLbl.getStyleClass().add("studentManagement-label");
	
	    Button btnAdd = new Button("Add");
	    Button btnDelete = new Button("Delete");
		Button btnUpdate = new Button("Update");
		Button btnSearch = new Button("Search");
		
		HBox hbox5 = new HBox(btnAdd, btnDelete, btnUpdate, btnSearch);
		hbox5.setSpacing(10);
		
		VBox vbox = new VBox(bookManagementLbl, hbox1, hbox2, hbox3, selectAuthorsLbl, listViewAuthors, hbox5);
		
		VBox.setMargin(listViewAuthors, new Insets(0, 100, 0, 100));
		
		root.setCenter(vbox);
		vbox.setSpacing(35);
		
		vbox.setAlignment(Pos.TOP_CENTER);
        
//       btnAdd.setOnAction(e -> {
//    	   	Integer broncoId = null;
//    	   	String broncoIdString = broncoIdTxtField.getText();
//    	   	if(broncoIdString != null && !broncoIdString.isEmpty())
//    	   	{
//    	   		broncoId = Integer.valueOf(broncoIdString);
//    	   	}
//			String studentName = studentNameTxtField.getText();
//			String studentCourse = studentCourseTxtField.getText();
//			String studentEmail = studentEmailTxtField.getText();
//			boolean studentAdded = StudentDataAccess.createStudent(broncoId, studentName, studentCourse, studentEmail);
//			
//			ShowAlerts.showAddedAlert(studentAdded, studentName, "Student");
//			
//			broncoIdTxtField.clear();
//			studentNameTxtField.clear();
//			studentCourseTxtField.clear();
//			studentEmailTxtField.clear();
//		});
//       
//       btnSearch.setOnAction(e ->{
//    	   	int broncoId = Integer.valueOf(broncoIdTxtField.getText());
//			
//			Student student = StudentDataAccess.searchStudent(broncoId);
//			
//			if(student != null)
//			{	
//				broncoIdTxtField.setText(String.valueOf(student.getBroncoId()));
//				studentNameTxtField.setText(student.getName());
//				studentCourseTxtField.setText(student.getCourse());
//				studentEmailTxtField.setText(student.getEmail());
//				
//				List<Loan> loans = student.getLoans();
//				for(Loan loan : loans)
//				{
//					listView.getItems().add(loan.toString());
//				}
//				
//			}
//			else
//			{
//				ShowAlerts.showNotFoundAlert();
//			}
//		});
//		
//		btnUpdate.setOnAction(e ->{
//			
//			int broncoId = Integer.valueOf(broncoIdTxtField.getText());
//			String studentName = studentNameTxtField.getText();
//			String studentCourse = studentCourseTxtField.getText();
//			String studentEmail = studentEmailTxtField.getText();
//			
//			boolean studentUpdated = StudentDataAccess.updateStudent(broncoId, studentName, studentCourse, studentEmail);
//			
//			ShowAlerts.showUpdatedAlert(studentUpdated, studentName);
//			
//			broncoIdTxtField.clear();
//			studentNameTxtField.clear();
//			studentCourseTxtField.clear();
//			studentEmailTxtField.clear();
//		});
//		
//		btnDelete.setOnAction(e ->{
//			
//			int broncoId = Integer.valueOf(broncoIdTxtField.getText());
//			
//			boolean deletedStudent = StudentDataAccess.deleteStudent(broncoId);
//			
//			ShowAlerts.showDeletedAlert(deletedStudent);
//			
//			broncoIdTxtField.clear();
//			studentNameTxtField.clear();
//			studentCourseTxtField.clear();
//			studentEmailTxtField.clear();
//		});
       
       
       
       
		
	}
	
	
}