package View;

import Listeners.MainUiListener;
import Model.Question;

public interface AbstractManualExamView {

	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);
	void loadQuestionsIntoTable(Question allQuestions);
}

