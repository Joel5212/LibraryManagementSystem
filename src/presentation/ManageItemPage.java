package presentation;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
//import persistence.LoanDataAccess;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ManageItemPage {
	
	public static void manageItemPage(BorderPane root)
	{
		
		Button manageBooksBtn = new Button("Manage Books");
		manageBooksBtn.setPrefHeight(80);
		manageBooksBtn.setPrefWidth(200);
		
		Button manageDocumentaryBtn = new Button("Manage Documentary");
		manageDocumentaryBtn.setPrefHeight(80);
		manageDocumentaryBtn.setPrefWidth(200);
		
		
		VBox vbox = new VBox(manageBooksBtn, manageDocumentaryBtn);
		
		vbox.setSpacing(50);
		
		vbox.setAlignment(Pos.CENTER);
		
		root.setCenter(vbox);
		
		manageBooksBtn.setOnAction(e ->{
			ManageBookPage.manageBookPage(root);
		});
		
		manageDocumentaryBtn.setOnAction(e -> {
			
		});
		
		
        
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
//		btnUpdate.setOnAction(e -> {
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