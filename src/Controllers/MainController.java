package Controllers;

import Model.OpenQ;
import Model.Question;

import java.io.FileNotFoundException;
import java.io.IOException;

import Listeners.MainEventsListener;
import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.Manager;
import View.AbstractMainView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController implements MainEventsListener, MainUiListener  {
	private Manager manModel;
	private AbstractMainView questView;
	private Stage stage;
	
	public MainController(Manager model, AbstractMainView view) {
		manModel = model;
		questView = view;
		
		manModel.registerListener(this);
		questView.registerListener(this);
	}

	@Override
	public void updateQuestionInUi(int id, String question) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeQuestionFromUi(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedOpenQuestionToModelEvent(String question, String answer) {
		questView.addOpenQuestionToComboBoxInUi(question, answer);
		
	}

	@Override
	public void addedAmericanQuestionToModelEvent(AmericanQ question) {
		questView.addAmericanQuestionToComboBoxInUi(question);
		
	}

	@Override
	public void updatedQuestionInModelEvent(int id, String question) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedQuestionFromModelEvent(int id, String question) {
		// TODO Auto-generated method stub
		
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
	public void addAllQuestionsToTableInUi() {
		try {
			questView.addOpenQuestionToTableInUi(manModel.getOpenQuestions());
//			questView.addAmericanQuestionToTableInUi(manModel.getAmericanQuestions());
		} catch (Exception e) {
			questView.errorMessageUi(e.getMessage());
		}
	}
    
    @Override
    public void handleCloseButtonAction(MouseEvent event, Button closeButton)  {
    	Stage newStage = new Stage();
    	Node source = (Node) event.getSource();
    	stage = (Stage) closeButton.getScene().getWindow();
    	stage.close();
    }

	@Override
	public void addAmericanAnswerToAmericanQuestion(AmericanQ question, String answer, boolean isTrue) {
		manModel.addAmericanAnswerToQuestion(question, answer, isTrue);
	}

	@Override
	public void addedAmericanAnswerToAmericanQuestionModelEvent(AmericanQ aN) {
			questView.addAmericanAnswerToTableInUi(aN);
	}
}
