package View;

import Listeners.MainUiListener;
import Model.UiElements;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AmericanAnswersView implements UiElements {

	@Override
	public Text topText() {
		Text text = new Text();
		text.setText("American Answers for the selected question:");
		text.setFont(new Font(25));
		text.setUnderline(true);
		return text;
	}

	@Override
	public Button saveAndExitOrReturn(Scene scene) {
		Button closeWindow = new Button();
		closeWindow.setText("Exit");
		closeWindow.setMinSize(50, 34);
		closeWindow.setPrefSize(50, 34);

		closeWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					l.handleCloseButtonAction(event, closeWindow);
				}
			}
		});
		return closeWindow;	
	}

	@Override
	public void sceneInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentsInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gridPaneInit() {
		// TODO Auto-generated method stub
		
	}

}
