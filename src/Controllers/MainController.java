package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import Listeners.MainEventsListener;
import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.Manager;
import Model.OpenQ;
import Model.Question;
import View.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController implements MainEventsListener, MainUiListener  {
	private Manager manModel;
	private AbstractMainView questView;
	private AbstractAnswersView ansView;
	private AbstractAutoExamView examView;
	private AbstractExistingExams existingView;
	private AbstractManualExamView manExamView;
	private Stage stage;
	
	public MainController(Manager model, AbstractMainView view) {
		manModel = model;
		questView = view;

		manModel.registerListener(this);
		questView.registerListener(this);
	}
	
	public void MainController(AbstractAnswersView ansv) {
		ansView = ansv;
		ansView.registerListener(this);
	}
	
	public void MainController(AbstractAutoExamView exV) {
		examView = exV;
		examView.registerListener(this);
	}
	
	public void MainController(AbstractExistingExams eexV) {
		existingView = eexV;
		existingView.registerListener(this);
	}
	
	public void MainController(AbstractManualExamView manExamV) {
		manExamView = manExamV;
		manExamView.registerListener(this);
	}

	@Override
	public void addOpenQuestionToUi(String question, String answer) {
		try {
			manModel.addOpenQuestions(question, answer);
		} catch (Exception e) {
			questView.errorMessageUi(e.getMessage());
		}
	}

	@Override
	public void addAmericanQuestionToUi(AmericanQ question) {
		try {
			manModel.addAmericanQuestion(question);
		} catch (Exception e) {
			questView.errorMessageUi(e.getMessage());
		}
	}
	
	@Override
	public void importFromBinaryFile(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException {
		manModel.readFromBinaryFile(fileName);
	}
	
	@Override
	public void addedOpenQuestionToModelEventObject(OpenQ question) {
		questView.addOpenQuestionToTableInUi(question);
	}
	
	@Override
	public void addedAmericanQuestionToModelEventObject(AmericanQ question) {
		questView.addAmericanQuestionToTableInUi(question);
	}
    
    @Override
    public void handleCloseButtonAction(MouseEvent event, Button closeButton)  {
    	@SuppressWarnings("unused")
		Stage newStage = new Stage();
    	@SuppressWarnings("unused")
		Node source = (Node) event.getSource();
    	stage = (Stage) closeButton.getScene().getWindow();
    	stage.close();
    	Platform.exit();
    }

	@Override
	public void addAmericanAnswerToAmericanQuestion(AmericanQ question, String answer, boolean isTrue) {
		manModel.addAmericanAnswerToQuestion(question, answer, isTrue);
	}

	@Override
	public void openAnswersWindow(AmericanQ aW, Stage parent) {
		Stage newStage = new Stage();
		newStage.initOwner(parent);
		ansView = new AnswersView(newStage);
		MainController(ansView);
		ansView.loadAnswersIntoTable(aW);
	}

	@Override
	public void importedFromBinaryFile(List<Question> questions) {
		questView.addQuestionsToTable(questions);
	}

	@Override
	public void deleteAnswer(AmericanAnswers aN, AmericanQ question) {
		manModel.deleteAmericanAnswer(aN, question);
	}
	
	@Override
	public void deletedAmericanAnswer(AmericanAnswers aN, AmericanQ question) {
		ansView.deleteAmericanAnswerFromTable(aN);
	}

	@Override
	public void saveToFile(String text) throws IOException {
		manModel.writeAllExternally(text);
	}

	@Override
	public void savedAllQuestionsToFile(String fileName, int amountOfQuestions) {
		questView.savedAllQuestionsMessageBox(fileName, amountOfQuestions);
	}

	@Override
	public void createAndOpenExam(int amountOfQuestions) throws IOException {
		manModel.autoCreateExam(amountOfQuestions);
	}

	@Override
	public void createdAutoExam(int amount, List<Question> exam) {
		Stage newStage = new Stage();
		examView = new AutoExamView(newStage);
		MainController(examView);
		examView.loadExamIntoTable(amount, exam);
	}

	@Override
	public void addedAmericanAnswerToQuestion(AmericanQ question) {
		questView.addedAmericanAnswerPopUpDialog(question);
	}

	@Override
	public void openExistingExamsWithLoadIntoTable(Stage parent) {
		Stage newStage = new Stage();
		newStage.initOwner(parent);
		existingView = new ExistingExamsView(newStage);
		MainController(existingView);
		existingView.loadExamsIntoTable(manModel.showAllExistingExamsInDirectory());
	}
	
	@Override
	public void openManualExamCreationWindow(Stage parent) {
		Stage newStage = new Stage();
		newStage.initOwner(parent);
		manExamView = new ManualExamView(newStage);
		MainController(manExamView);
		for (int i = 0; i < manModel.getAllQuestionSize(); i++) {
			manExamView.loadQuestionsIntoTable(manModel.getAllQuestions(i));
		}
	}

	@Override
	public void copyExam(File f) throws IOException {
		manModel.copyExistingExamByFileName(f);
	}

	@Override
	public void copiedAnExistingExam(File fileName) {
		existingView.copiedAnExistingExamPopUpDialog(fileName);
	}

	@Override
	public void saveToBinaryFile(String text) throws FileNotFoundException, IOException {
		manModel.saveToBinaryFile(text);
	}

	@Override
	public void importPreMadeQuestionsList() throws SQLException {
		manModel.questionsList();
		
	}

	@Override
	public void addOpenQuestionToManualExamList(OpenQ oQ) {
		if (manModel.addOpenQuestionToManualExam(oQ)) {
			System.out.println("Open question added to manualExamArray");
			JOptionPane.showMessageDialog(null, "Added");
		}
		else {
			JOptionPane.showMessageDialog(null, "Question already exists in exam");
		}
	}

	@Override
	public void createManualExam() throws IOException {
		manModel.sortAndPrintManualExamArray();
	}

	@Override
	public void addAmericanQuestionToManualExamList(AmericanQ aQ) {
		if (manModel.addAmericanQuestionToManualExam(aQ)) {
			System.out.println("American question added to manualExamArray");
			JOptionPane.showMessageDialog(null, "Added");
		}
		else {
			JOptionPane.showMessageDialog(null, "Question already exists in exam");
		}
	}

	@Override
	public void saveToBinaryFileOnExit() throws FileNotFoundException, IOException {
		manModel.saveToBinaryFileAutomatically();
	}

	@Override
	public void savedToBinaryFileOnExit() {
		questView.errorMessageUi("Saved all questions to questions.ser");
	}
}
