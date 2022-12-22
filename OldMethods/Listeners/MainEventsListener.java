package Listeners;

import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;
import javafx.collections.ObservableList;

public interface MainEventsListener {
	
	void addedOpenQuestionToModelEvent(String question, String answer);
	void addedOpenQuestionToModelEventObject(OpenQ question);
	void addedAmericanQuestionToModelEventObject(AmericanQ question);
	void addedAmericanQuestionToModelEvent(AmericanQ question);
	void addedAmericanAnswerToAmericanQuestionModelEvent(AmericanAnswers aN);
	void updatedQuestionInModelEvent(int id, String question);
	void removedQuestionFromModelEvent(int id, String question);
}
