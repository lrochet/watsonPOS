package javafx.application;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class GuiApplication extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double width = screenSize.getWidth();
    	double height = screenSize.getHeight();
    	System.out.println("screen="+width +" x " +height);
        Parent root = FXMLLoader.load(getClass().getResource("ihm.fxml"));
        Scene scene = new Scene(root,width,height);
        scene.getStylesheets().add(getClass().getResource("myfont.css").toExternalForm());
      
        
        primaryStage.setScene(scene);
       
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}