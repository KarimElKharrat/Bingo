package dad.bingo.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.bingo.BingoApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ValidarCartonDialog extends Dialog<String> implements Initializable {

	// model
	
	private StringProperty numeros = new SimpleStringProperty();
	
	// view
	
	@FXML
    private TextArea numerosText;
	
	@FXML
    private VBox view;
	
	public ValidarCartonDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ValidarCartonView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		numeros.bind(numerosText.textProperty());
		
		// init dialog
		 
		ButtonType validarButtonType = new ButtonType("Validar", ButtonData.OK_DONE);
 
		setTitle("Validar Cartón");
		setHeaderText("Introduce los númeron de la línea o del bingo para comprobar si ya han salido:");
		initOwner(BingoApp.primaryStage);
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().setAll(validarButtonType, ButtonType.CANCEL);		
 
		setResultConverter(this::onResultConverter);
		
		// disable add button
		 
		Button validarButton = (Button) getDialogPane().lookupButton(validarButtonType);
		validarButton.disableProperty().bind(numeros.isEmpty());
		
	}
	
	private String onResultConverter(ButtonType button) {
		
		if(button.getButtonData().equals(ButtonData.OK_DONE)) {
			return numeros.get();
		}
		return null;
		
	}

}
