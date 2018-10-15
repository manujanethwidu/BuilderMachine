package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(BuilderController.class.getResourceAsStream("TireBuilder.fxml"));
			final BuilderController controller = (BuilderController) loader.getController();
			stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<Event>() {
				
				@Override
				public void handle(Event event) {
					try { 
						controller.WindowShownEvent();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "No Connection with Scale");
					}   
				}
			});
			stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					controller.WindowColoasEvent();
				}
			});
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setMaximized(true);
			
			stage.show();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
