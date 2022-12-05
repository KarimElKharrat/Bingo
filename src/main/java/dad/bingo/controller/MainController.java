package dad.bingo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.bingo.BingoApp;
import dad.bingo.dialogs.ValidarCartonDialog;
import dad.bingo.modelo.Bingo;
import dad.bingo.modelo.Bola;
import dad.bingo.utils.Voces;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MainController implements Initializable {

	// logica de negocio
	
	private static Bingo bingo = new Bingo();
	private Timeline timeline;
	
	// model
	
	private BooleanProperty iniciado = new SimpleBooleanProperty();
	private StringProperty ultimaBola = new SimpleStringProperty();
	private IntegerProperty tiempo = new SimpleIntegerProperty();
	
	// view

    @FXML
    private Button iniciarButton;
    
    @FXML
    private Button detenerButton;

    @FXML
    private GridPane numerosPane;

    @FXML
    private Slider tiempoSlider;

    @FXML
    private Label ultimaBolaLabel;

    @FXML
    private GridPane view;
	
	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		// bindings
		
		iniciarButton.disableProperty().bind(iniciado);
		detenerButton.disableProperty().bind(iniciado.not());
		
		ultimaBolaLabel.textProperty().bind(ultimaBola);
		
		tiempo.bind(tiempoSlider.valueProperty());
		
		// listeners
		
		tiempo.addListener(this::onTiempoChanged);
		
		// load data
		
	}
	

	private void onTiempoChanged(ObservableValue<? extends Number> o, Number ov, Number nv) {
		
		if(nv != null && timeline != null) {
			
			
			timeline.pause();
			timeline = new Timeline(new KeyFrame(Duration.seconds(1 * tiempo.get()), ev -> {
				
				accion();
				
		    }));
			timeline.setCycleCount(100);
			timeline.play();
			
		}
		
	}

	@FXML
	void onValidarCarton(ActionEvent event) {
		
		detener();
		
		ValidarCartonDialog dialog = new ValidarCartonDialog();
    	
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			List<Integer> numerosAValidar = new ArrayList<>();
			for(String s:result.get().split("\\s+")) {
				numerosAValidar.add(Integer.parseInt(s));
			}
			
			System.out.println("valido: " + bingo.validar(numerosAValidar));
			if(bingo.validar(numerosAValidar))
				acierto();
			else
				fallo();
		}

	}
	
	private void acierto() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("¡Has acertado!");
		alert.setHeaderText("¡Felicidades has acertado!");
		alert.initOwner(BingoApp.primaryStage);
		alert.show();
			
	}
	
	private void fallo() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("¡Has fallado!");
		alert.setHeaderText("¡No todos los números se han cantado todavía!");
		alert.initOwner(BingoApp.primaryStage);
		alert.show();
		
	}

	@FXML
	void onIniciar(ActionEvent event) {
		
		iniciado.set(true);
		timeline = new Timeline(new KeyFrame(Duration.seconds(1 * tiempo.get()), ev -> {
			accion();
	    }));
		
		timeline.setCycleCount(100);
		timeline.play();
		
	}
	
	@FXML
	void onDetener(ActionEvent event) {
		
		detener();
		
	}
	
	@FXML
	void onReiniciar(ActionEvent event) {
		
		detener();
		bingo = new Bingo();
		ultimaBola.set("");
		
		for(Node node : numerosPane.getChildren()) {
			((Circle) ((StackPane) node).getChildren().get(0)).setFill(Paint.valueOf("#000000"));
			((Label) ((StackPane) node).getChildren().get(1)).setTextFill(Paint.valueOf("#ffffff"));;
		}
	
	}
	
	@FXML
	void onSalir(ActionEvent event) {
		onClose();
	}
	
	public void detener() {
		
		iniciado.set(false);
		
		if(timeline != null) {
			timeline.pause();
		}
		
	}
	
	public void accion() {
		
		if(bingo.getPanel().getBolas().size() < 90) {
			
			Bola nuevaBola = bingo.sacarBola();
			ultimaBola.set(nuevaBola.getNumero() + "");
			
			Voces.reproducir(Integer.parseInt(ultimaBola.get()));
			
			for(Node node : numerosPane.getChildren()) {
				if(ultimaBola.get().equals(((Label) ((StackPane) node).getChildren().get(1)).getText())) {
					((Circle) ((StackPane) node).getChildren().get(0)).setFill(Paint.valueOf("#ffffff"));
					((Label) ((StackPane) node).getChildren().get(1)).setTextFill(Paint.valueOf("#000000"));;
					break;
				}
			}
		} else {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Se acabó");
			alert.setHeaderText("No quedan más bolas.");
			alert.setContentText("Dele al botñon reiniciar si quiere jugar otra vez.");
			alert.initOwner(BingoApp.primaryStage);
			alert.show();
			
			detener();
			
		}
		
	}
	
	public void onClose() {
		
		detener();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText("Está a punto de salir de la aplicación.");
		alert.setContentText("¿Desea continuar?");
		alert.initOwner(BingoApp.primaryStage);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			BingoApp.primaryStage.close();
		} else {
			BingoApp.primaryStage.show();
		}
		
	}
	
	public GridPane getView() {
		return view;
	}
	
}
