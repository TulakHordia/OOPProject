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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuestionView extends GridPane implements UiElements, AbstractMainView {

	@SuppressWarnings("unused")
	private MainController questionsController;
	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	
	private Stage stage;
	private Scene questionScene;
	private GridPane gpRoot;
	private RadioButton sTrue;
	private RadioButton sFalse;
	private int savedValue;
	private Label space;
	private Label space2;
	private Label space3;
	private Label titleText;
	private Label americanAnswerSelectLabel;
	private Label openQuestionsLabel;
	private Label americanQuestionsLabel;
	private Label fileNameLabel;
	private Label enterYourAnswerLabel;
	private Label doubleClickValueToEdit;
	private Label doubleClickToSeeAnswer;
	private Label saveToFileText;
	private Label saveToBinaryFileLabel;
	private Label amountOfQuestionsLabel;
	private Label createManualExamLabel;
	private Label showAllExistingExamsLabel;
	private Label questionsRelatedLabel;
	private Label importExportRelatedLabel;
	private Label examRelatedLabel;
	private Label importFromQuestionsListLabel;
	private TextField openQuestionTextField;
	private TextField openAnswerTextField;
	private TextField americanQuestionTextField;
	private TextField americanAnswerTextField;
	private TextField americanAnswerBoolean;
	private TextField fileName;
	private TextField saveToFileField;
	private TextField saveToBinaryFileField;
	private TextField amountOfQuestionsField;
	private TableView<OpenQ> openQuestionTable;
	private TableView<AmericanQ> americanQuestionTable;
	TableColumn<OpenQ, String> questionCol;
	TableColumn<OpenQ, String> answerCol;
	TableColumn<AmericanQ, String> americanQuestionCol;
	TableColumn<AmericanAnswers, Object> americanAnswerCol;
	
	public QuestionView(MainController questionController) {
		this.questionsController = questionController;
	}
	
	public QuestionView(Stage theStage) {
		this.stage = theStage;
		theStage.setTitle("Exam Creator 1.0");
		Image icon = new Image("list-check.png");
		theStage.getIcons().add(icon);
		theStage.setWidth(1000);
		theStage.setHeight(1000);
		theStage.setResizable(false);
		sceneInit();
		componentsInit();
		textFields();
		createTable();
		gridPane();
		closeStage(theStage);
		theStage.setScene(questionScene);
		theStage.show();
	}
	
	public void closeStage(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	        if (KeyCode.ESCAPE == event.getCode()) {
	            stage.close();
	        }
	    });
	}
	
	private void textFields() {
		int minWidth = 150;
		this.openQuestionTextField = new TextField();
		this.openAnswerTextField = new TextField();
		this.americanQuestionTextField = new TextField();
		this.americanAnswerTextField = new TextField();
		this.fileName = new TextField();
		this.americanAnswerBoolean = new TextField();
		this.saveToFileField = new TextField();
		this.amountOfQuestionsField = new TextField();
		this.saveToBinaryFileField = new TextField();
		americanAnswerBoolean.setPromptText("true/false");
		fileName.setPromptText("Enter Filename");
		fileName.setText("questions.ser");
		openQuestionTextField.setPromptText("Question");
		openAnswerTextField.setPromptText("Answer");
		americanQuestionTextField.setPromptText("Question");
		americanAnswerTextField.setPromptText("Answer");
		americanAnswerBoolean.setMinWidth(70);
		saveToFileField.setPromptText("Filename");
		amountOfQuestionsField.setPromptText("Amount of questions");
		saveToBinaryFileField.setPromptText("Filename");
		saveToBinaryFileField.setMinWidth(minWidth);
		amountOfQuestionsField.setMinWidth(minWidth);
		saveToFileField.setMinWidth(minWidth);
		fileName.setMinWidth(minWidth);
		openQuestionTextField.setMinWidth(minWidth);
		openAnswerTextField.setMinWidth(minWidth);
		americanQuestionTextField.setMinWidth(minWidth);
		americanAnswerTextField.setMinWidth(minWidth);
	}
	
	@Override
	public void componentsInit() {
		this.titleText = new Label("Exam Creator 1.0");
		titleText.setFont(new Font("Arial", 30));
		titleText.setTextFill(Color.DARKBLUE);
		titleText.setUnderline(true);
		this.openQuestionsLabel = new Label("Add an Open question: ");
		openQuestionsLabel.setFont(new Font("Cambria",15));
		this.americanQuestionsLabel = new Label("Add an American question: ");
		americanQuestionsLabel.setFont(new Font("Cambria",15));
		this.fileNameLabel = new Label("Enter binary file name to import: ");
		fileNameLabel.setFont(new Font("Cambria",15));
		this.enterYourAnswerLabel = new Label("Enter your answer: (max 10) ");
		enterYourAnswerLabel.setFont(new Font("Cambria",15));
		this.americanAnswerSelectLabel = new Label("Select a question first!");
		americanAnswerSelectLabel.setFont(new Font("Arial",18));
		americanAnswerSelectLabel.setTextFill(Color.RED);
		americanAnswerSelectLabel.setUnderline(true);
		this.doubleClickValueToEdit = new Label("Double click to edit Questions/Answers");
		doubleClickValueToEdit.setFont(new Font("Arial",15));
		doubleClickValueToEdit.setTextFill(Color.RED);
		this.doubleClickToSeeAnswer = new Label("Double click to edit Questions");
		doubleClickToSeeAnswer.setFont(new Font("Arial",15));
		doubleClickToSeeAnswer.setTextFill(Color.RED);
		this.saveToFileText = new Label("Enter file name for saving: ");
		saveToFileText.setFont(new Font("Cambria",15));
		this.saveToBinaryFileLabel = new Label("Enter file name for saving to a binary file: ");
		saveToBinaryFileLabel.setFont(new Font("Cambria",15));
		this.amountOfQuestionsLabel = new Label("Enter amount of questions:");
		amountOfQuestionsLabel.setFont(new Font("Cambria",15));
		this.showAllExistingExamsLabel = new Label("Show all existing exams:");
		showAllExistingExamsLabel.setFont(new Font("Cambria",15));
		this.questionsRelatedLabel = new Label("Question editing:");
		questionsRelatedLabel.setFont(new Font("Cambria",15));
		questionsRelatedLabel.setTextFill(Color.OLIVE);
		questionsRelatedLabel.setUnderline(true);
		this.importExportRelatedLabel = new Label("Importing/Exporting:");
		importExportRelatedLabel.setFont(new Font("Cambria",15));
		importExportRelatedLabel.setTextFill(Color.OLIVE);
		importExportRelatedLabel.setUnderline(true);
		this.examRelatedLabel = new Label("Exam Creation:");
		examRelatedLabel.setFont(new Font("Cambria",15));
		examRelatedLabel.setTextFill(Color.OLIVE);
		examRelatedLabel.setUnderline(true);
		this.createManualExamLabel = new Label("Create manual exam");
		createManualExamLabel.setFont(new Font("Cambria",15));
		this.importFromQuestionsListLabel = new Label("(Only if no viable .ser file exists)");
		importFromQuestionsListLabel.setFont(new Font("Cambria",15));
		importFromQuestionsListLabel.setTextFill(Color.BLUE);
		this.space = new Label(" ");
		this.space2 = new Label(" ");
		this.space3 = new Label(" ");
		this.sTrue = new RadioButton("True");
		this.sFalse = new RadioButton("False");
		this.openQuestionTable = new TableView<OpenQ>();
		this.americanQuestionTable = new TableView<AmericanQ>();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void gridPane() {
		//Radio Buttons
		TilePane radioButtonTilePane = new TilePane();
		ToggleGroup tG = new ToggleGroup();
		sTrue.setToggleGroup(tG);
		sFalse.setToggleGroup(tG);
		radioButtonTilePane.getChildren().add(sTrue);
		radioButtonTilePane.getChildren().add(sFalse);
		
		//GridPane properties
		gpRoot.setHgap(2);
		gpRoot.setVgap(2);
		gpRoot.setPadding(new Insets(15));
		
		//Title
		gpRoot.add(titleText, 0, 0);
		gpRoot.setHalignment(titleText, HPos.CENTER);
		
		//space
		gpRoot.add(space, 0, 1);
		
		//Open questions related Label
		gpRoot.add(questionsRelatedLabel, 0, 2);
		//Open question things
		gpRoot.add(openQuestionsLabel, 0, 3);
		gpRoot.add(openQuestionTextField, 1, 3);
		gpRoot.add(openAnswerTextField, 2, 3);
		gpRoot.add(addQuestionButton(), 3, 3);
		//Add american question 
		gpRoot.add(americanQuestionsLabel, 0, 4);
		gpRoot.add(americanQuestionTextField, 1, 4);
		gpRoot.add(addAmericanQuestionButton(), 2, 4);
		//Select american answer in table label
		gpRoot.add(americanAnswerSelectLabel, 0, 5);
		//Add American Answer
		gpRoot.add(enterYourAnswerLabel, 0, 6);
		gpRoot.add(americanAnswerTextField, 1, 6);
		gpRoot.add(radioButtonTilePane, 2, 6);
		gpRoot.add(addAmericanAnswersToQuestionButton(), 3, 6);
		//Import/Export related Label
		gpRoot.add(importExportRelatedLabel, 0, 7);
		//Import binary file
		gpRoot.add(fileNameLabel, 0, 8);
		gpRoot.add(fileName, 1, 8);
		gpRoot.add(importFromBinaryFileButton(), 2, 8);
		//Save to binary file elements
		gpRoot.add(saveToBinaryFileLabel, 0, 9);
		gpRoot.add(saveToBinaryFileField, 1, 9);
		gpRoot.add(saveToBinaryFileButton(), 2, 9);
		//Save to file elements
		gpRoot.add(saveToFileText, 0, 10);
		gpRoot.add(saveToFileField, 1, 10);
		gpRoot.add(saveToFileButton(), 2, 10);
		//Space
		gpRoot.add(space3, 0, 11);
		//Import from premade questions list
		gpRoot.add(importPreMadeQuestions(), 0, 12);
		gpRoot.add(importFromQuestionsListLabel, 1, 12);
		//Exams related Label
		gpRoot.add(examRelatedLabel, 0, 13);
		//Create manual exam elements
		gpRoot.add(createManualExamLabel, 0, 14);
		gpRoot.add(createManualExamButton(), 1, 14);
		//Create auto exam elements
		gpRoot.add(amountOfQuestionsLabel, 0, 15);
		gpRoot.add(amountOfQuestionsField, 1, 15);
		gpRoot.add(createAndOpenExam(), 2, 15);
		//Show all existing exams
		gpRoot.add(showAllExistingExamsLabel, 0, 16);
		gpRoot.add(showAllExistingExams(), 1, 16);
		//Space
		gpRoot.add(space2, 0, 17);
		//Tables
		gpRoot.add(doubleClickValueToEdit, 1, 18);
		gpRoot.setHalignment(doubleClickValueToEdit, HPos.CENTER);
		gpRoot.add(openQuestionTable, 1, 19);
		gpRoot.add(doubleClickToSeeAnswer, 0, 18);
		gpRoot.setHalignment(doubleClickToSeeAnswer, HPos.CENTER);
		gpRoot.add(americanQuestionTable, 0, 19);
		//Select American Question and open the answers button
		gpRoot.add(openAnswers(), 0, 20);
		//Exit button
		gpRoot.add(saveAndExitOrReturn(questionScene), 3, 65);
	}
	
	@Override
	public void sceneInit() {
		this.gpRoot = new GridPane();
		this.questionScene = new Scene(gpRoot, 1000, 1000);
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		
		questionCol = new TableColumn<OpenQ, String>("Open Question");
		answerCol = new TableColumn<OpenQ, String>("Answer");
		americanQuestionCol = new TableColumn<AmericanQ, String>("American Question");
		
		questionCol.setSortable(false);
		answerCol.setSortable(false);
		americanQuestionCol.setSortable(false);
			
		openQuestionTable.setMinSize(400, 300);
		americanQuestionTable.setMinSize(300, 300);
		
		openQuestionTable.setEditable(true);
		americanQuestionTable.setEditable(false);

		questionCol.setCellValueFactory(new PropertyValueFactory<OpenQ, String>("question"));
		questionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		questionCol.setOnEditCommit(new EventHandler<CellEditEvent<OpenQ, String>>() {

			@Override
			public void handle(CellEditEvent<OpenQ, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuestion(event.getNewValue());
			}
		});
		answerCol.setCellValueFactory(new PropertyValueFactory<OpenQ, String>("answer"));
		answerCol.setCellFactory(TextFieldTableCell.forTableColumn());
		answerCol.setOnEditCommit(new EventHandler<CellEditEvent<OpenQ, String>>() {

			@Override
			public void handle(CellEditEvent<OpenQ, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setCorrectAnswer(event.getNewValue());
			}
		});
		
		americanQuestionCol.setCellValueFactory(new PropertyValueFactory<AmericanQ, String>("question"));
		
		americanQuestionTable.setEditable(true);
		americanQuestionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		americanQuestionCol.setOnEditCommit(new EventHandler<CellEditEvent<AmericanQ, String>>() {

			@Override
			public void handle(CellEditEvent<AmericanQ, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuestion(event.getNewValue());
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
		
		americanQuestionTable.resizeColumn(americanQuestionCol, 170);
		americanQuestionTable.setLayoutX(1000);
		americanQuestionCol.setMinWidth(300);
		americanQuestionCol.setMaxWidth(width);
		americanQuestionTable.setPrefHeight(height);
		
		openQuestionTable.getColumns().addAll(questionCol, answerCol);
		americanQuestionTable.getColumns().addAll(americanQuestionCol);

	}
	
	public Button addQuestionButton() {
		Button addQuestionClick = new Button();
		addQuestionClick.setText("Add");
		addQuestionClick.setMinSize(50, 25);
		addQuestionClick.setPrefSize(50, 25);
		addQuestionClick.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				if (openQuestionTextField.getText().isEmpty() || openQuestionTextField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No question entered!");
				}
				if (openAnswerTextField.getText().isEmpty() || openAnswerTextField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No answer entered!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						l.addOpenQuestionToUi(openQuestionTextField.getText(), openAnswerTextField.getText());
					}
				}
			}
		});
		return addQuestionClick;
	}
	
	public Button addAmericanQuestionButton() {
		Button addAmericanQuestionClick = new Button();
		addAmericanQuestionClick.setText("Add");
		addAmericanQuestionClick.setMinSize(50, 25);
		addAmericanQuestionClick.setPrefSize(50, 25);
		addAmericanQuestionClick.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (americanQuestionTextField.getText().isEmpty() || americanQuestionTextField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No question entered!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						AmericanQ aW = new AmericanQ(americanQuestionTextField.getText());
						l.addAmericanQuestionToUi(aW);
					}
				}
			}
		});
		return addAmericanQuestionClick;
	}
	
	public Button addAmericanAnswersToQuestionButton() {
		Button addAmericanAnswersClick = new Button();
		addAmericanAnswersClick.setText("Add");
		addAmericanAnswersClick.setMinSize(50, 25);
		addAmericanAnswersClick.setPrefSize(50, 25);
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
				if (americanQuestionTable.getSelectionModel().getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "No question selected");
				}
				if (americanAnswerTextField.getText().isEmpty() || americanAnswerTextField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No answer entered!");
				}
				else {
					AmericanQ aW = americanQuestionTable.getSelectionModel().getSelectedItem();
					for (MainUiListener l : questUiListeners) {
						l.addAmericanAnswerToAmericanQuestion(aW, americanAnswerTextField.getText(), buttonState);
					}
				}
			}
		});
		return addAmericanAnswersClick;
	}
	
	public Button importFromBinaryFileButton() {
		Button importFromBinaryButton = new Button();
		importFromBinaryButton.setText("Import from a binary file");
		importFromBinaryButton.setPrefSize(200, 25);
		importFromBinaryButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (fileName.getText().isEmpty() || fileName.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No filename entered!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						try {
							l.importFromBinaryFile(fileName.getText());
						} catch (ClassNotFoundException | IOException e) {
							errorMessageUi(e.getMessage());
						}
					}
				}
			}
		});
		return importFromBinaryButton;	
	}
	
	public Button importPreMadeQuestions() {
		Button importPreMade = new Button();
		importPreMade.setText("Import from a Premade Questions list");
		importPreMade.setPrefSize(250, 25);
		importPreMade.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.importPreMadeQuestionsList();
					} catch (Exception e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return importPreMade;	
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
					try {
						l.saveToBinaryFileOnExit();
					} catch (IOException e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return closeWindow;	
	}
	
	public Button openAnswers() {
		Button openAnswersWindow = new Button();
		openAnswersWindow.setText("Open answers for selected Question");
		openAnswersWindow.setMinSize(100, 25);
		openAnswersWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (americanQuestionTable.getSelectionModel().getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select a question in the table!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						AmericanQ aW = americanQuestionTable.getSelectionModel().getSelectedItem();
						l.openAnswersWindow(aW, stage);
					}
				}
			}
		});
		return openAnswersWindow;	
	}
	
	public Button createAndOpenExam() {
		Button createAndOpenExamWindow = new Button();
		createAndOpenExamWindow.setText("Create & show exam");
		createAndOpenExamWindow.setMinSize(100, 25);
		createAndOpenExamWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (amountOfQuestionsField.getText().isEmpty() || amountOfQuestionsField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "No amount entered!");
				}
				else {
					String m = amountOfQuestionsField.getText();
					if (checkAmountOfQuestions(m)) {
						for (MainUiListener l : questUiListeners) {
							savedValue = Integer.parseInt(m);
							try {
								l.createAndOpenExam(savedValue);
							} catch (IOException e) {
								errorMessageUi(e.getMessage());
							}
						}
					}
				}
			}
		});
		return createAndOpenExamWindow;	
	}
	
	public boolean checkAmountOfQuestions(String m) {
		try {
			savedValue = Integer.parseInt(m);
			return true;
		} catch (NumberFormatException e) {
			errorMessageUi("Please enter a numerical value!");
			return false;
		}
	}
	
	public Button saveToFileButton() {
		Button saveToFile = new Button();
		saveToFile.setText("Save");
		saveToFile.setMinSize(100, 25);
		saveToFile.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (saveToFileField.getText().isEmpty() || saveToFileField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Please enter a filename!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						try {
							l.saveToFile(saveToFileField.getText());
						} catch (IOException e) {
							errorMessageUi(e.getMessage());
						}
					}
				}
			}
		});
		return saveToFile;	
	}
	
	public Button saveToBinaryFileButton() {
		Button saveToFile = new Button();
		saveToFile.setText("Save");
		saveToFile.setMinSize(100, 25);
		saveToFile.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (saveToBinaryFileField.getText().isEmpty() || saveToBinaryFileField.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Please enter a filename!");
				}
				else {
					for (MainUiListener l : questUiListeners) {
						try {
							l.saveToBinaryFile(saveToBinaryFileField.getText());
						} catch (IOException e) {
							errorMessageUi(e.getMessage());
						}
					}
				}
			}
		});
		return saveToFile;	
	}
	
	public Button showAllExistingExams() {
		Button openExistingExams = new Button();
		openExistingExams.setText("Open existing exams");
		openExistingExams.setMinSize(100, 25);
		openExistingExams.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.openExistingExamsWithLoadIntoTable(stage);
					} catch (Exception e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return openExistingExams;	
	}
	
	public Button createManualExamButton() {
		Button createManualExam = new Button();
		createManualExam.setText("Open manual exam creator");
		createManualExam.setMinSize(130, 25);
		createManualExam.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.openManualExamCreationWindow(stage);
					} catch (Exception e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return createManualExam;	
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
	public void addQuestionsToTable(List<Question> questions) {
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) questions.get(i);
				openQuestionTable.getItems().add(oQ);
			}
			if (questions.get(i) instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) questions.get(i);
				americanQuestionTable.getItems().add(aQ);
			}
		}
	}

	@Override
	public void savedAllQuestionsMessageBox(String fileName, int amountOfQuestions) {
		JOptionPane.showMessageDialog(null, "Saved all questions to: " + fileName+".txt"+"\nContains: "+amountOfQuestions+" Questions");
	}

	@Override
	public void addedAmericanAnswerPopUpDialog(AmericanQ question) {
		
	}
}
