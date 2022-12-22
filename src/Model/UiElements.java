package Model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public interface UiElements {
	void sceneInit();
	void componentsInit();
	void gridPane();
	void closeStage(Stage stage);
	public Button saveAndExitOrReturn(Scene scene);
}
