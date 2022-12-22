package View;

import java.util.List;

import Listeners.MainUiListener;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;

public interface AbstractMainView {
	
	void addOpenQuestionToTableInUi(OpenQ question);
	void addAmericanQuestionToTableInUi(AmericanQ question);
	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);
	void addQuestionsToTable(List<Question> questions);
	void savedAllQuestionsMessageBox(String fileName, int amountOfQuestions);
	void addedAmericanAnswerPopUpDialog(AmericanQ question);
}

