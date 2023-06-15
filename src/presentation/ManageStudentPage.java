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

import domain.Loan;
import domain.Student;

public class ManageStudentPage {
	
	public static void manageStudentPage(BorderPane root)
	{
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		
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
		listView.setPrefWidth(80);
		listView.setPrefHeight(300);
		
		
		HBox hbox0 = new HBox(broncoIdLbl, broncoIdTxtField, studentNameLbl, studentNameTxtField);
		HBox hbox1 = new HBox(studentCourseLbl, studentCourseTxtField, studentEmailLbl, studentEmailTxtField);
		
		hbox0.setMargin(broncoIdTxtField, new Insets(0, 0, 0, 60));
		hbox0.setMargin(studentNameLbl, new Insets(0, 50, 0, 30));
		
		hbox1.setMargin(studentCourseTxtField, new Insets(0, 0, 0, 13));
		hbox1.setMargin(studentEmailLbl, new Insets(0, 120, 0, 32));

		
		
		Label studentManagementLbl = new Label("Student Management");
	    studentManagementLbl.getStyleClass().add("studentManagement-label");
	    
	
	    Button btnAdd = new Button("Add");
	    Button btnDelete = new Button("Delete");
		Button btnUpdate = new Button("Update");
		Button btnSearch = new Button("Search");
		
//		btnSearch.setOnAction(e -> {
//			int broncoId = Integer.valueOf(textField.getText());
//			List<Loan> loans = LoanDataAccess.getLoans(broncoId);
//			List<String> loansString = new ArrayList<String>();
//			
//			int i = 0;
//			for(Loan loan : loans)
//			{
//				loansString.add(loan.getStudent().getName() + "        " + loan.getItem().getTitle() + "        $"  + loan.getItem().getDailyPrice() + "        " +loan.getLoanDate() + "        " + loan.getDuedate());
//				listView.getItems().add(loansString.get(i));
//				i++;
//			}
//			
//		});
		
		
		
		HBox hbox2 = new HBox(btnAdd, btnDelete, btnUpdate, btnSearch);
		hbox2.setSpacing(10);
		
		VBox vbox = new VBox(studentManagementLbl, hbox0, hbox1, loansLbl, listView, hbox2);
		
		root.setCenter(vbox);
		vbox.setSpacing(35);
		vbox.setMargin(listView, new Insets(0, 430, 0, 0));
		vbox.setMargin(studentManagementLbl, new Insets(0, 400, 0, 0));
		vbox.setMargin(loansLbl, new Insets(0, 1015, 0, 0));
		
		
		vbox.setAlignment(Pos.TOP_CENTER);
		root.setMargin(vbox, new Insets(50, 0, 0, visualBounds.getWidth()/2-300));
        
       btnAdd.setOnAction(e -> {
    	   	int broncoId = Integer.valueOf(broncoIdTxtField.getText());
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