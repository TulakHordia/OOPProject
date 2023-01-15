package Listeners;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;

public interface MainEventsListener {

	//Questions.
	void addedOpenQuestionToModelEventObject(OpenQ question);
	void addedAmericanQuestionToModelEventObject(AmericanQ question);
	void deletedOpenQuestion(OpenQ oQuest);
	void deletedAmericanQuestion(AmericanQ question);
	void deletedAmericanAnswer(AmericanAnswers aN, AmericanQ question) throws SQLException;
	void addedAmericanAnswerToQuestion(AmericanQ question);

	//Files.
	void importedFromBinaryFile(List<Question> questions);
	void savedAllQuestionsToFile(String fileName, int amountOfQuestions);
	void createdAutoExam(int amount, List<Question> exam);
	void copiedAnExistingExam(File fileName);
	void savedToBinaryFileOnExit();

	//SQL Database
	void closedConnectionToDatabase();
	void connectionEstablishedToUi();
	void connectionFailedToUi();

}

