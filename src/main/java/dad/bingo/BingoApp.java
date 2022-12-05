package dad.bingo;

import dad.bingo.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BingoApp extends Application {

	private MainController controller = new MainController();
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BingoApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("Bingo");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		BingoApp.primaryStage.setOnCloseRequest(e -> {
			e.consume();
			controller.onClose();
		});	
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
