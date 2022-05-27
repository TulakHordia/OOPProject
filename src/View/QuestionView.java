package View;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import Controllers.MainController;
import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;
import Model.UiElements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class QuestionView extends GridPane implements UiElements, AbstractMainView {

	private MainController questionsController;
	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	
	private Scene questionScene;
	private GridPane gpRoot;
	private ComboBox<String> cmbAllQuestions = new ComboBox<String>();
	private ComboBox<AmericanAnswers> cmbAmericanAnswers;
	private RadioButton sTrue;
	private RadioButton sFalse;
	private Label allQuestions;
	private Label theAnswers;
	private Label openQuestionsLabel;
	private Label americanQuestionsLabel;
	private Label currentAmountOfQuestions;
	private Label fileNameLabel;
	private Label americanAnswerLabel;
	private Label americanAnswers;
	private TextField openQuestionTextField;
	private TextField openAnswerTextField;
	private TextField americanQuestionTextField;
	private TextField americanAnswerTextField;
	private TextField americanAnswerBoolean;
	private TextField fileName;
	private TableView<Question> openQuestionTable;
	private TableView<AmericanQ> americanQuestionTable;
	private TableView<AmericanAnswers> americanAnswersTable;
	TableColumn<Question, Object> questionCol;
	TableColumn<Question, Object> answerCol;
	TableColumn<AmericanQ, Object> americanQuestionCol;
	TableColumn<AmericanAnswers, Object> americanAnswerCol;
	private ObservableList<AmericanAnswers> americanAnswersList = FXCollections.observableArrayList();
;
	
	public QuestionView(MainController questionController) {
		this.questionsController = questionController;
	}
	
	private ComboBox getAmericanAnswers() {
		if (cmbAmericanAnswers == null) {
			cmbAmericanAnswers = new ComboBox<AmericanAnswers>();
			cmbAmericanAnswers.setEditable(true);
			cmbAmericanAnswers.setItems(americanAnswersList);
		}
		return cmbAmericanAnswers;
	}
	
	public QuestionView(Stage theStage) {
		theStage.setTitle("Questions Menu");
		Image icon = new Image("list-check.png");
		theStage.getIcons().add(icon);
		theStage.setWidth(1800);
		theStage.setHeight(800);
		theStage.setResizable(false);
		sceneInit();
		componentsInit();
		textFields();
		createTable();
		gridPaneInit();
		theStage.setScene(questionScene);
		theStage.show();
	}
	
	private void textFields() {
		int minWidth = 150;
		this.openQuestionTextField = new TextField();
		this.openAnswerTextField = new TextField();
		this.americanQuestionTextField = new TextField();
		this.americanAnswerTextField = new TextField();
		this.fileName = new TextField();
		this.americanAnswerBoolean = new TextField();
		americanAnswerBoolean.setPromptText("true/false");
		fileName.setPromptText("filename");
		openQuestionTextField.setPromptText("Question");
		openAnswerTextField.setPromptText("Answer");
		americanQuestionTextField.setPromptText("Question");
		americanAnswerTextField.setPromptText("Answer");
		americanAnswerBoolean.setMinWidth(70);
		fileName.setMinWidth(minWidth);
		openQuestionTextField.setMinWidth(minWidth);
		openAnswerTextField.setMinWidth(minWidth);
		americanQuestionTextField.setMinWidth(minWidth);
		americanAnswerTextField.setMinWidth(minWidth);
	}
	
	@Override
	public void componentsInit() {
		this.allQuestions = new Label("All questions: ");
		this.theAnswers = new Label("The answers: ");
		this.openQuestionsLabel = new Label("Add open question: ");
		this.americanQuestionsLabel = new Label("Add american question: ");
		this.currentAmountOfQuestions = new Label("There are currently 0 questions.");
		this.fileNameLabel = new Label("Enter file name: ");
		this.americanAnswerLabel = new Label("Select amount of answers: ");
		this.americanAnswers = new Label("Enter your answer: (max 10) ");
		this.sTrue = new RadioButton("True");
		this.sFalse = new RadioButton("False");
		this.openQuestionTable = new TableView<Question>();
		this.americanQuestionTable = new TableView<AmericanQ>();
		this.americanAnswersTable = new TableView<AmericanAnswers>();
	}
	
	@Override
	public void gridPaneInit() {
		cmbAllQuestions.setPrefSize(200, 34);
		TilePane r = new TilePane();
		ToggleGroup tG = new ToggleGroup();
		sTrue.setToggleGroup(tG);
		sFalse.setToggleGroup(tG);
		r.getChildren().add(sTrue);
		r.getChildren().add(sFalse);
		gpRoot.setHgap(2);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(10));
		gpRoot.add(topText(), 0, 0);
		gpRoot.add(allQuestions, 0, 1);
		gpRoot.add(sceneButton1(questionScene), 0, 2);
		gpRoot.add(openQuestionsLabel, 0, 3);
		gpRoot.add(openQuestionTextField, 1, 3);
		gpRoot.add(openAnswerTextField, 2, 3);
		gpRoot.add(addQuestionButton(), 3, 3);
		gpRoot.add(cmbAllQuestions, 4, 3);
		gpRoot.add(americanQuestionsLabel, 0, 4);
		gpRoot.add(americanQuestionTextField, 1, 4);
		gpRoot.add(addAmericanQuestionButton(), 2, 4);
		gpRoot.add(americanAnswers, 0, 5);
		gpRoot.add(americanAnswerTextField, 1, 5);
		gpRoot.add(r, 2, 5);
		gpRoot.add(addAmericanAnswersToQuestionButton(), 3, 5);
		gpRoot.add(fileNameLabel, 0, 6);
		gpRoot.add(fileName, 1, 6);
		gpRoot.add(importFromBinaryFileButton(), 2, 6);
		gpRoot.add(saveAndExitOrReturn(questionScene), 50, 9);
	}
	
	@Override
	public void sceneInit() {
		this.gpRoot = new GridPane();
		this.questionScene = new Scene(gpRoot, 1800, 800);
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		
		questionCol = new TableColumn<Question, Object>("Open Question");
		answerCol = new TableColumn<Question, Object>("Answer");
		americanQuestionCol = new TableColumn<AmericanQ, Object>("American Question");
		americanAnswerCol = new TableColumn<AmericanAnswers, Object>("Answers");
		
		questionCol.setSortable(false);
		answerCol.setSortable(false);
		americanQuestionCol.setSortable(false);
		americanAnswerCol.setSortable(false);
			
		openQuestionTable.setMinSize(400, 300);
		americanQuestionTable.setMinSize(200, 300);
		americanAnswersTable.setMinSize(200, 300);
		
		openQuestionTable.setEditable(true);
		americanQuestionTable.setEditable(true);
		americanAnswersTable.setEditable(true);

		questionCol.setCellValueFactory(new PropertyValueFactory<Question, Object>("question"));
		answerCol.setCellValueFactory(new PropertyValueFactory<Question, Object>("answer"));
		americanQuestionCol.setCellValueFactory(new PropertyValueFactory<AmericanQ, Object>("question"));
		americanAnswerCol.setCellFactory(new Callback<TableColumn<AmericanAnswers, Object>, TableCell<AmericanAnswers, Object>>() {
			@Override
			public TableCell call(TableColumn param) {
				return new TableCell() {
					
					@Override
					protected void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							setGraphic(getAmericanAnswers());
						} else {
							setGraphic(null);
						}
					}
				};
			}
		});

		int width = 500;
		int height = 500;
		int minWidth = 100;
		
		openQuestionTable.resizeColumn(answerCol, 120);
		openQuestionTable.resizeColumn(questionCol, 120);
		openQuestionTable.setLayoutX(500);
		questionCol.setMinWidth(minWidth);
		answerCol.setMinWidth(minWidth);
		questionCol.setMaxWidth(width);
		answerCol.setMaxWidth(width);
		openQuestionTable.setPrefHeight(height);
		
		americanQuestionTable.resizeColumn(americanQuestionCol, 150);
		americanQuestionTable.setLayoutX(1000);
		americanQuestionCol.setMinWidth(minWidth);
		americanQuestionCol.setMaxWidth(width);
		americanQuestionTable.setPrefHeight(height);
		
		americanAnswersTable.resizeColumn(americanAnswerCol, 150);
		americanAnswersTable.setLayoutX(1000);
		americanAnswersTable.setMinWidth(minWidth);
		americanAnswersTable.setMaxWidth(width);
		americanAnswersTable.setPrefHeight(height);
			
		openQuestionTable.getColumns().addAll(questionCol, answerCol);
		americanQuestionTable.getColumns().addAll(americanQuestionCol);
		americanAnswersTable.getColumns().addAll(americanAnswerCol);

		gpRoot.add(openQuestionTable, 5, 8);
		gpRoot.add(americanQuestionTable, 6, 8);
		gpRoot.add(americanAnswersTable, 7, 8);
	}

	@Override
	public Text topText() {
		Text text = new Text();
		text.setText("Questions Menu");
		text.setFont(new Font(25));
		text.setUnderline(true);
		return text;
	}

	public Button sceneButton1(Scene scene) {
		Button showAllQuestions = new Button();
		showAllQuestions.setText("Show all questions");
		showAllQuestions.setPrefSize(140, 34);
		showAllQuestions.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					l.addAllQuestionsToTableInUi();
				}
			}
		});
		return showAllQuestions;	
	}
	
	public Button addQuestionButton() {
		Button addQuestionClick = new Button();
		addQuestionClick.setText("Add");
		addQuestionClick.setMinSize(50, 34);
		addQuestionClick.setPrefSize(50, 34);
		addQuestionClick.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (MainUiListener l : questUiListeners) {
					l.addOpenQuestionToUi(openQuestionTextField.getText(), openAnswerTextField.getText());
				}
			}
		});
		return addQuestionClick;
	}
	
	public Button addAmericanQuestionButton() {
		Button addAmericanQuestionClick = new Button();
		addAmericanQuestionClick.setText("Add");
		addAmericanQuestionClick.setMinSize(50, 34);
		addAmericanQuestionClick.setPrefSize(50, 34);
		addAmericanQuestionClick.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (MainUiListener l : questUiListeners) {
					AmericanQ aW = new AmericanQ(americanQuestionTextField.getText());
					l.addAmericanQuestionToUi(aW);
				}
			}
		});
		return addAmericanQuestionClick;
	}
	
	public Button addAmericanAnswersToQuestionButton() {
		Button addAmericanAnswersClick = new Button();
		addAmericanAnswersClick.setText("Add");
		addAmericanAnswersClick.setMinSize(50, 34);
		addAmericanAnswersClick.setPrefSize(50, 34);
		addAmericanAnswersClick.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				boolean buttonState = true;
				if (sTrue.isSelected()) {
					buttonState = true;
				}
				else {
					buttonState = false;
				}
				AmericanQ aW = americanQuestionTable.getSelectionModel().getSelectedItem();
				for (MainUiListener l : questUiListeners) {
					l.addAmericanAnswerToAmericanQuestion(aW, americanAnswerTextField.getText(), buttonState);
				}
			}
		});
		return addAmericanAnswersClick;
	}
	
	public Button importFromBinaryFileButton() {
		Button importFromBinaryButton = new Button();
		importFromBinaryButton.setText("Import from a binary file");
		importFromBinaryButton.setPrefSize(200, 20);
		importFromBinaryButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.importFromBinaryFile(fileName.getText());
					} catch (ClassNotFoundException | IOException e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return importFromBinaryButton;	
	}

	@Override
	public Button saveAndExitOrReturn(Scene scene) {
		Button closeWindow = new Button();
		closeWindow.setText("Exit");
		closeWindow.setMinSize(50, 34);
		closeWindow.setPrefSize(50, 34);

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
	public void addOpenQuestionToComboBoxInUi(String question, String answer) {
		cmbAllQuestions.getItems().add(question + " " + answer);
	}

	@Override
	public void addAmericanQuestionToComboBoxInUi(AmericanQ question) {
		cmbAllQuestions.getItems().add(question.getQuestion());
	}

	@Override
	public void removeQuestionFromComboBoxInUi(int id, String question) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateQuestionInComboBoxInUi(int id, String question) {
		// TODO Auto-generated method stub
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
	public void addOpenQuestionToTableInUi(OpenQ question) {
		openQuestionTable.getItems().add(question);
	}

	@Override
	public void addAmericanQuestionToTableInUi(AmericanQ question) {
		americanQuestionTable.getItems().addAll(question);
	}

	@Override
	public void addAmericanAnswerToTableInUi(AmericanQ aN) {
//		americanAnswersList = FXCollections.observableArrayList();
		for (int i = 0; i < aN.getAnswersNum(); i++) {
			americanAnswersList.add(aN.getAnswers(i));
		}
//		americanAnswersList.add(aN);
		americanAnswersTable.setItems(americanAnswersList);
	}
}
