package View;

import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.OpenQ;

public interface AbstractMainView {
	void addOpenQuestionToComboBoxInUi(String question, String answer);
	void addOpenQuestionToTableInUi(OpenQ question);
	void addAmericanQuestionToComboBoxInUi(AmericanQ question);
	void addAmericanQuestionToTableInUi(AmericanQ question);
	void addAmericanAnswerToTableInUi(AmericanAnswers answer);
	
	void removeQuestionFromComboBoxInUi(int id, String question);
	void updateQuestionInComboBoxInUi(int id, String question);
	
	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);
}
