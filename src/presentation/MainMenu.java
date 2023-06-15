package presentation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
//import presentation.StudentMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainMenu extends Application {
	
	private Stage primaryStage;
    private BorderPane root;

	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	     this.primaryStage.setTitle("Library Management System");

	        // Create the header navigation bar
	     HBox header = createHeader();
	     
	        // Create the root layout
	     root = new BorderPane();
	     root.setTop(header);
	     root.getStyleClass().add("root");

	        // Show the home screen by default

	        // Create the scene and set it on the primary stage
	     
	     Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
	     
	     
	     Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
	     scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	     primaryStage.setScene(scene);
	     primaryStage.show();
	}
	
	

	private HBox createHeader() {
        // Create the header navigation bar
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(10);
        header.getStyleClass().add("header");
        
        // Create the navigation buttons
        Button manageLoansBtn = createNavButton("Manage Loans");
        Button manageStudentsBtn = createNavButton("Manage Students");
        Button manageItemsBtn = createNavButton("Manage Items");
        Button home = createNavButton("Home");

        // Set action event handlers for the navigation buttons
        manageLoansBtn.setOnAction(event -> ManageStudentPage.manageStudentPage(root));
        manageStudentsBtn.setOnAction(event -> showManageStudentScreen());
        manageItemsBtn.setOnAction(event -> showManageItemScreen());

        // Add navigation buttons to the header
        header.getChildren().addAll(home, manageLoansBtn, manageStudentsBtn, manageItemsBtn);
        header.setSpacing(50);

        // Return the header
        return header;
    }

	private Button createNavButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("nav-button");
        button.setShape(new Circle(10));
//        button.setMaxSize(3,3);
        return button;
    }

	private void showManageLoanScreen() {
        // Display the home screen
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        Label homeLabel = new Label("Welcome to the Home Screen!");
        homeLabel.getStyleClass().add("content-label");
        contentArea.getChildren().add(homeLabel);
        root.setCenter(contentArea);
    }

	private void showManageStudentScreen() {
        // Display the settings screen
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        Label settingsLabel = new Label("Welcome to the Settings Screen!");
        settingsLabel.getStyleClass().add("content-label");
        contentArea.getChildren().add(settingsLabel);
        root.setCenter(contentArea);
    }

	private void showManageItemScreen() {
        // Display the about screen
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        Label aboutLabel = new Label("Welcome to the About Screen!");
        aboutLabel.getStyleClass().add("content-label");
        contentArea.getChildren().add(aboutLabel);
        root.setCenter(contentArea);
    }
	

	public static void main(String[] args) {
		launch(args);
	}
}

