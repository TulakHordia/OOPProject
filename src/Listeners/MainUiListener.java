package Listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.OpenQ;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface MainUiListener {
	
	//Questions manipulation
	void addOpenQuestionToUi(String question, String answer);
	void addAmericanQuestionToUi(AmericanQ question);
	void addAmericanAnswerToAmericanQuestion(AmericanQ question, String answer, boolean isTrue);
	void deleteAnswer(AmericanAnswers aN, AmericanQ question);
	//Stage manipulation
	void openAnswersWindow(AmericanQ aW, Stage parent);
	void openExistingExamsWithLoadIntoTable(Stage parent);
	void openManualExamCreationWindow(Stage parent);
	void handleCloseButtonAction(MouseEvent event, Button closeButton);
	//Import/Export
	void importFromBinaryFile(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException;
	void saveToFile(String text) throws IOException;
	void saveToBinaryFile(String text) throws FileNotFoundException, IOException;
	void importPreMadeQuestionsList() throws SQLException;
	void saveToBinaryFileOnExit() throws FileNotFoundException, IOException;
	//Exams
	void createAndOpenExam(int amountOfQuestions) throws IOException;
	void copyExam(File f) throws IOException;
	void addOpenQuestionToManualExamList(OpenQ oQ);
	void addAmericanQuestionToManualExamList(AmericanQ aQ);
	void createManualExam() throws IOException;
}



