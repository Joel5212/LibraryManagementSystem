package presentation;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import persistence.StudentDataAccess;
//import persistence.LoanDataAccess;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

//import presentation.StudentMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Loan;
import entity.Student;

public class ManageStudentPage {
	
	public static void manageStudentPage(BorderPane root)
	{
		
		Label broncoIdLbl = new Label("Bronco ID:");
		Label studentNameLbl = new Label("Student Name: ");
		Label studentCourseLbl = new Label("Student Course: ");
		Label studentEmailLbl = new Label("Email: ");
		
		
		TextField broncoIdTxtField = new TextField();
		TextField studentNameTxtField = new TextField();
		TextField studentCourseTxtField= new TextField();
		TextField studentEmailTxtField = new TextField();
		
		Label loansLbl = new Label("Loans");
		loansLbl.getStyleClass().add("loans-label");
		
		ListView<String> listView = new ListView<String>();
		
		HBox hbox0 = new HBox(broncoIdLbl, broncoIdTxtField, studentNameLbl, studentNameTxtField);
		HBox hbox1 = new HBox(studentCourseLbl, studentCourseTxtField, studentEmailLbl, studentEmailTxtField);
		
		HBox.setMargin(broncoIdTxtField, new Insets(0, 0, 0, 60));
		
//		HBox.setMargin(studentNameTxtField, new Insets(0, 200, 0, 0));
		HBox.setMargin(studentNameLbl, new Insets(0, 50, 0, 30));
		
		HBox.setMargin(studentCourseTxtField, new Insets(0, 0, 0, 13));
		HBox.setMargin(studentEmailLbl, new Insets(0, 120, 0, 32));
		
		
		HBox.setMargin(broncoIdLbl, new Insets(0, 0, 0, 180));
		HBox.setMargin(studentCourseLbl, new Insets(0, 0, 0, 180));
//		
//		
		
	
		
		Label studentManagementLbl = new Label("Student Management");
		HBox.setMargin(studentManagementLbl , new Insets(0, 0, 0, 50));
	    studentManagementLbl.getStyleClass().add("studentManagement-label");
	    
	
	    Button btnAdd = new Button("Add");
	    Button btnDelete = new Button("Delete");
		Button btnUpdate = new Button("Update");
		Button btnSearch = new Button("Search");
		
		
		
		HBox hbox2 = new HBox(btnAdd, btnDelete, btnUpdate, btnSearch);
		hbox2.setSpacing(10);
		
		VBox vbox = new VBox(studentManagementLbl, hbox0, hbox1, loansLbl, listView, hbox2);
		
		root.setCenter(vbox);
		vbox.setSpacing(35);
		VBox.setMargin(studentManagementLbl, new Insets(0, 0, 0, 0));
		VBox.setMargin(hbox2, new Insets(0, 0, 0, 100));
		VBox.setMargin(listView, new Insets(0, 100, 0, 100));
		
		vbox.setAlignment(Pos.TOP_CENTER);
		BorderPane.setMargin(vbox, new Insets(50, 0, 0, 0));
        
       btnAdd.setOnAction(e -> {
    	   	Integer broncoId = null;
    	   	String broncoIdString = broncoIdTxtField.getText();
    	   	if(broncoIdString != null && !broncoIdString.isEmpty())
    	   	{
    	   		broncoId = Integer.valueOf(broncoIdString);
    	   	}
			String studentName = studentNameTxtField.getText();
			String studentCourse = studentCourseTxtField.getText();
			String studentEmail = studentEmailTxtField.getText();
			boolean studentAdded = StudentDataAccess.createStudent(broncoId, studentName, studentCourse, studentEmail);
			
			ShowAlerts.showAddedAlert(studentAdded, studentName, "Student");
			
			broncoIdTxtField.clear();
			studentNameTxtField.clear();
			studentCourseTxtField.clear();
			studentEmailTxtField.clear();
		});
       
       btnSearch.setOnAction(e ->{
    	   	int broncoId = Integer.valueOf(broncoIdTxtField.getText());
			
			Student student = StudentDataAccess.searchStudent(broncoId);
			
			if(student != null)
			{	
				broncoIdTxtField.setText(String.valueOf(student.getBroncoId()));
				studentNameTxtField.setText(student.getName());
				studentCourseTxtField.setText(student.getCourse());
				studentEmailTxtField.setText(student.getEmail());
				
				List<Loan> loans = student.getLoans();
				for(Loan loan : loans)
				{
					listView.getItems().add(loan.toString());
				}
				
			}
			else
			{
				ShowAlerts.showNotFoundAlert();
			}
		});
		
		btnUpdate.setOnAction(e ->{
			
			int broncoId = Integer.valueOf(broncoIdTxtField.getText());
			String studentName = studentNameTxtField.getText();
			String studentCourse = studentCourseTxtField.getText();
			String studentEmail = studentEmailTxtField.getText();
			
			boolean studentUpdated = StudentDataAccess.updateStudent(broncoId, studentName, studentCourse, studentEmail);
			
			ShowAlerts.showUpdatedAlert(studentUpdated, studentName);
			
			broncoIdTxtField.clear();
			studentNameTxtField.clear();
			studentCourseTxtField.clear();
			studentEmailTxtField.clear();
		});
		
		btnDelete.setOnAction(e ->{
			
			int broncoId = Integer.valueOf(broncoIdTxtField.getText());
			
			boolean deletedStudent = StudentDataAccess.deleteStudent(broncoId);
			
			ShowAlerts.showDeletedAlert(deletedStudent);
			
			broncoIdTxtField.clear();
			studentNameTxtField.clear();
			studentCourseTxtField.clear();
			studentEmailTxtField.clear();
		});
       
       
       
       
		
	}
	
	
}