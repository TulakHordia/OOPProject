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
	void addAmericanAnswerToAmericanQuestion(AmericanQ question, String answer, boolean isTrue) throws SQLException;
	void deleteAnswer(AmericanAnswers aN, AmericanQ question) throws SQLException;
	void deleteAmericanQuestion(AmericanQ aW, boolean buttonState) throws SQLException;
	void deleteOpenQuestion(OpenQ oQ, boolean buttonState) throws SQLException;
	void updateOpenQuestionFromUi(String theQuestion, int questNum) throws SQLException;
	void updateOpenAnswerFromUi(String theAnswer, int questNum) throws SQLException;
	void updateAmericanQuestionFromUi(String theQuestion, int questNum) throws SQLException;
	void updateAmericanAnswerFromUi(String theAnswer, int ansNum) throws SQLException;
	//Stage manipulation
	void openAnswersWindowSQL(AmericanQ aW, Stage parent) throws SQLException, ClassNotFoundException;
	void openAnswersWindowCode(AmericanQ aW, Stage parent) throws SQLException, ClassNotFoundException;
	void openExistingExamsWithLoadIntoTable(Stage parent);
	void openManualExamCreationWindow(Stage parent) throws SQLException, ClassNotFoundException;
	void handleCloseButtonAction(MouseEvent event, Button closeButton) throws SQLException;
	//Import/Export
	void importFromBinaryFile(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException;
	void saveToFile(String text) throws IOException;
	void saveToBinaryFile(String text) throws FileNotFoundException, IOException;
	void importPreMadeQuestionsList() throws SQLException, ClassNotFoundException;
	void saveToBinaryFileOnExit() throws FileNotFoundException, IOException;
	//Exams
	void createAndOpenExam(int amountOfQuestions) throws IOException;
	void copyExam(File f) throws IOException;
	void addOpenQuestionToManualExamList(OpenQ oQ);
	void addAmericanQuestionToManualExamList(AmericanQ aQ);
	void createManualExam() throws IOException, SQLException;
	//Database
	void createNewTables() throws SQLException;
	void dropOldTables() throws SQLException;
	void closeDatabaseConnectionClick() throws SQLException;
	void establishConnectionToDatabase() throws SQLException, ClassNotFoundException;
	void createNewSchema() throws SQLException;
	void dropSchema() throws SQLException;
}



