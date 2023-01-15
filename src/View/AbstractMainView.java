package View;

import java.util.List;

import Listeners.MainUiListener;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;

public interface AbstractMainView {

	//General
	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);

	//Questions
	void addOpenQuestionToTableInUi(OpenQ question);
	void addAmericanQuestionToTableInUi(AmericanQ question);
	void addQuestionsToTable(List<Question> questions);
	void deletedOpenQuestionToUi(OpenQ oQuest);
	void deletedAmericanQuestionToUi(AmericanQ question);
	void savedAllQuestionsMessageBox(String fileName, int amountOfQuestions);
	void addedAmericanAnswerPopUpDialog(AmericanQ question);

	//SQL Database
    void closedConnectionToDatabaseToUiUpdate();
    void connectionEstablishedToUi();
	void connectionFailedToUi();


}

