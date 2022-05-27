package Model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public interface UiElements {

	public Text topText();
	public Button saveAndExitOrReturn(Scene scene);
	void sceneInit();
	void componentsInit();
	void gridPaneInit();
	
}
