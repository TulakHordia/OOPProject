package View;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import Listeners.MainUiListener;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;
import Model.UiElements;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ManualExamView implements UiElements, AbstractManualExamView {

	private Scene scene;
	private GridPane gp;
	private Label titleText;
	private Label selectToAddToExamList;
	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	private TableView<OpenQ> openQuestionsTable;
	private TableView<AmericanQ> americanQuestionsTable;
	private TableColumn<OpenQ, String> openQuestionsColumn;
	private TableColumn<AmericanQ, String> americanQuestionsColumn;
	
	public ManualExamView(Stage stage) {
		stage.setTitle("Manual Exam Creator");
		stage.setWidth(480);
		stage.setHeight(600);
		stage.setResizable(false);
		sceneInit();
		closeStage(stage);
		componentsInit();
		createTable();
		gridPane();
		stage.setScene(scene);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		int width = 230;
		this.openQuestionsTable = new TableView<OpenQ>();
		this.openQuestionsColumn = new TableColumn<OpenQ, String>("Open Questions");
		
		this.americanQuestionsTable = new TableView<AmericanQ>();
		this.americanQuestionsColumn = new TableColumn<AmericanQ, String>("American Questions");
		
		openQuestionsTable.setMinSize(200, 300);
		openQuestionsTable.setEditable(true);
		
		americanQuestionsTable.setMinSize(200, 300);
		americanQuestionsTable.setEditable(true);
		
		openQuestionsColumn.setSortable(false);
		openQuestionsColumn.setMinWidth(width);
		openQuestionsColumn.setMaxWidth(width);
		openQuestionsColumn.setCellValueFactory(new PropertyValueFactory<OpenQ, String>("question"));
		
		americanQuestionsColumn.setSortable(false);
		americanQuestionsColumn.setMinWidth(width);
		americanQuestionsColumn.setMaxWidth(width);
		americanQuestionsColumn.setCellValueFactory(new PropertyValueFactory<AmericanQ, String>("question"));
		
		openQuestionsTable.getColumns().addAll(openQuestionsColumn);
		americanQuestionsTable.getColumns().addAll(americanQuestionsColumn);
	}

	@Override
	public void registerListener(MainUiListener listener) {
		questUiListeners.add(listener);
	}

	@Override
	public void errorMessageUi(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void sceneInit() {
		this.gp = new GridPane();
		this.scene = new Scene(gp, 600, 600);
	}

	@Override
	public void componentsInit() {
		titleText = new Label("Manual Exam Creator");
		titleText.setFont(new Font("Arial", 20));
		titleText.setTextFill(Color.DARKBLUE);
		titleText.setUnderline(true);
		selectToAddToExamList = new Label("Select a value to add");
		selectToAddToExamList.setFont(new Font("Arial",20));
		selectToAddToExamList.setTextFill(Color.RED);
		selectToAddToExamList.setUnderline(true);
	}

	@SuppressWarnings("static-access")
	@Override
	public void gridPane() {
		gp.setHgap(2);
		gp.setVgap(10);
		gp.setPadding(new Insets(10));
		gp.add(titleText, 0, 0);
		gp.setHalignment(titleText, HPos.CENTER);
		gp.add(selectToAddToExamList, 0, 1);
		gp.setHalignment(selectToAddToExamList, HPos.CENTER);
		gp.add(openQuestionsTable, 0, 2);
		gp.add(americanQuestionsTable, 1, 2);
		gp.add(addAmericanQuestionToList(), 1, 3);

		gp.add(addOpenQuestionToList(), 0, 3);
		gp.add(createExam(), 0, 4);
	}
	
	public Button addOpenQuestionToList() {
		Button addOpenQuestion = new Button();
		addOpenQuestion.setText("Add Open");
		addOpenQuestion.setPrefSize(100, 34);
		addOpenQuestion.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (openQuestionsTable.getSelectionModel().getSelectedItem() == null) {
					errorMessageUi("No question selected");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						OpenQ oQ = openQuestionsTable.getSelectionModel().getSelectedItem();
						l.addOpenQuestionToManualExamList(oQ);
					}
				}
			}
		});
		return addOpenQuestion;	
	}
	
	public Button addAmericanQuestionToList() {
		Button addAmericanQuestion = new Button();
		addAmericanQuestion.setText("Add American");
		addAmericanQuestion.setPrefSize(100, 34);
		addAmericanQuestion.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (americanQuestionsTable.getSelectionModel().getSelectedItem() == null) {
					errorMessageUi("No question selected");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						AmericanQ aQ = americanQuestionsTable.getSelectionModel().getSelectedItem();
						l.addAmericanQuestionToManualExamList(aQ);
					}
				}
			}
		});
		return addAmericanQuestion;	
	}
	
	public Button createExam() {
		Button create = new Button();
		create.setText("Create the exam");
		create.setPrefSize(150, 34);
		create.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.createManualExam();
					} catch (IOException e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return create;	
	}

	@Override
	public void closeStage(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	        if (KeyCode.ESCAPE == event.getCode()) {
	            stage.close();
	        }
	    });
	}

	@Override
	public Button saveAndExitOrReturn(Scene scene) {
		Button closeWindow = new Button();
		closeWindow.setText("Exit");
		closeWindow.setMinSize(100, 50);
		closeWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					l.handleCloseButtonAction(event, closeWindow);
				}
			}
		});
		return closeWindow;	
	}

	@Override
	public void loadQuestionsIntoTable(Question allQuestions) {
		if (allQuestions instanceof OpenQ) {
			OpenQ oQ = (OpenQ) allQuestions;
			openQuestionsTable.getItems().add(oQ);
		}
		if (allQuestions instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) allQuestions;
			americanQuestionsTable.getItems().add(aQ);
		}
	}
}
