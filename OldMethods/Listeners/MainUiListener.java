package Listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import Model.AmericanQ;
import Model.OpenQ;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public interface MainUiListener {
	void addOpenQuestionToUi(String question, String answer);
	void addAmericanQuestionToUi(AmericanQ question);
	void addAllQuestionsToTableInUi();
	void addAmericanAnswerToAmericanQuestion(AmericanQ question, String answer, boolean isTrue);
	void updateQuestionInUi(int id, String question);
	void removeQuestionFromUi(int id);
	void handleCloseButtonAction(MouseEvent event, Button closeButton);
	void importFromBinaryFile(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException;
}
