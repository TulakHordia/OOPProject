package View;

import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;

public interface AbstractAnswersView {
	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);
	void loadAnswersIntoTable(AmericanQ aW);
	void deleteAmericanAnswerFromTable(AmericanAnswers aN);
}
