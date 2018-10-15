package application;

import java.io.IOException;
import java.sql.Connection;

import javax.swing.JOptionPane;

import db.CreateConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tables.BPTbl;

public class PressScreenController {

	@FXML
	public void pressButton(ActionEvent event) {
		String press = "";
		String txt = event.getSource().toString();
		String substr = txt.substring(txt.length() - 4);

		String[] words = txt.split("\\'");

		System.out.println(words[1]);
		press = words[1];

	}

	@FXML
	public void changeScreenBtnPushed(ActionEvent event) {
		try {
			Parent builderScene = FXMLLoader.load(getClass().getResource("TireBuilder.fxml"));
			Scene pressScene = new Scene(builderScene);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(pressScene);
			window.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

}
