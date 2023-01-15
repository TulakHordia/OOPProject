package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import Listeners.MainUiListener;
import Model.AmericanAnswers;
import Model.AmericanQ;
import Model.DatabaseIntegration;
import Model.UiElements;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnswersView extends GridPane implements AbstractAnswersView, UiElements {

	private DatabaseIntegration DBIntegration;
	private Scene scene;
	private GridPane gp;
	private AmericanQ aQuest;
	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	ObservableList<AmericanAnswers> answers;
	private Label doubleClickValueToEdit;
	private Label titleText;
	private Label selectToDelete;
	private TableView<AmericanAnswers> answersTable;
	private TableColumn<AmericanAnswers, String> answersColumn;
	private TableColumn<AmericanAnswers, String> answersBooleanColumn; 

	
	public AnswersView(Stage stage) throws SQLException, ClassNotFoundException {
		DBIntegration = new DatabaseIntegration();

		stage.setTitle("Answers");
		stage.setWidth(340);
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
	
	public void closeStage(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	        if (KeyCode.ESCAPE == event.getCode()) {
	            stage.close();
	        }
	    });
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() throws SQLException {
		Connection connectSQL = DBIntegration.getConnection();
		ResultSet resultSet1 = DBIntegration.getResultSet();
		String AATable = "SELECT * from americananswers";
		resultSet1 = connectSQL.createStatement().executeQuery(AATable);
		int width = 150;

		this.answersTable = new TableView<AmericanAnswers>();
		this.answersColumn = new TableColumn<AmericanAnswers, String>("Answers");
		this.answersBooleanColumn = new TableColumn<AmericanAnswers, String>("True/False");
		
		answersTable.setMinSize(300, 300);
		answersTable.setEditable(true);
		
		answersColumn.setSortable(false);
		answersColumn.setMinWidth(width);
		answersColumn.setMaxWidth(width);
		answersColumn.setCellValueFactory(new PropertyValueFactory<AmericanAnswers, String>("answer"));
		answersColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		answersColumn.setOnEditCommit(new EventHandler<CellEditEvent<AmericanAnswers, String>>() {

			@Override
			public void handle(CellEditEvent<AmericanAnswers, String> event) {
				int ansNum = 0;
				AmericanAnswers aW = answersTable.getSelectionModel().getSelectedItem();
				for (int i = 0; i < aQuest.getAnswersNum(); i++) {
					if (aQuest.getAnswers(i).getAnswer().equals(aW)) {
						ansNum = i;
					}
				}
				//String oldAnswer = event.getTableView().getItems().get(event.getTablePosition().getRow()).getAnswer();
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setAnswer(event.getNewValue());
				for (MainUiListener l : questUiListeners) {
					String newAnswer = event.getTableView().getItems().get(event.getTablePosition().getRow()).getAnswer();
					try {
						l.updateAmericanAnswerFromUi(newAnswer, ansNum);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
					}
				}
			}
		});
		
		answersBooleanColumn.setSortable(false);
		answersBooleanColumn.setMinWidth(width);
		answersBooleanColumn.setMaxWidth(width);
		answersBooleanColumn.setCellValueFactory(new PropertyValueFactory<AmericanAnswers, String>("booleanAnswer"));
		answersBooleanColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		answersBooleanColumn.setOnEditCommit(new EventHandler<CellEditEvent<AmericanAnswers, String>>() {

			@Override
			public void handle(CellEditEvent<AmericanAnswers, String> event) {
				if (event.getTableView().getItems().get(event.getTablePosition().getRow()).setTrueString(event.getNewValue())) {
					JOptionPane.showMessageDialog(null, "Changed"+"\nRefresh the table to see changes");
				}
			}
		});

		answersTable.getColumns().addAll(answersColumn,answersBooleanColumn);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void gridPane() {
		gp.setHgap(2);
		gp.setVgap(10);
		gp.setPadding(new Insets(10));
		gp.add(titleText, 0, 0);
		gp.setHalignment(titleText, HPos.CENTER);
		gp.add(doubleClickValueToEdit, 0, 1);
		gp.setHalignment(doubleClickValueToEdit, HPos.CENTER);
		gp.add(answersTable, 0, 2);
		gp.add(selectToDelete, 0, 3);
		gp.add(deleteAnswerButton(), 0, 4);
	}
	
	public Button deleteAnswerButton() {
		Button deleteAnswer = new Button();
		deleteAnswer.setText("Delete");
		deleteAnswer.setPrefSize(60, 34);
		deleteAnswer.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					try {
						l.deleteAnswer(answersTable.getSelectionModel().getSelectedItem(), aQuest);
						answers.remove(answersTable.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
					}
				}
			}
		});
		return deleteAnswer;	
	}
	
	public void loadAnswersIntoTable(AmericanQ aW) {
		for (int i = 0; i < aW.getAnswersNum(); i++) {
			answersTable.getItems().add(aW.getAnswers(i));
		}
		aQuest = aW;
	}

	public void loadAnswersFromDatabaseToUi(AmericanQ aQ) throws SQLException {
		aQuest = aQ;
		Connection connectSQL = DBIntegration.getConnection();
		PreparedStatement pState = connectSQL.prepareStatement("use questions;");
		//answers = FXCollections.observableArrayList();
		String getAnswers = "SELECT americananswers.answer, americananswers.isTrue " +
				"FROM americanquestions_americananswers, americananswers " +
				"WHERE americanquestions_americananswers.answerID = americananswers.answerID " +
				"AND americanquestions_americananswers.questionID = '" + aQuest.getQuestionNumber() + "';";
		try (ResultSet resultSet1 = pState.executeQuery(getAnswers)) {
			while (resultSet1.next()) {
				AmericanAnswers aW = new AmericanAnswers(resultSet1.getString("answer"), resultSet1.getBoolean("isTrue"));
				//answers.add(aW);
				answersTable.getItems().add(aW);
			}
		}

		//answersTable.setItems(answers);
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
	public void deleteAmericanAnswerFromTable(AmericanAnswers aN) throws SQLException {
		answersTable.getItems().remove(aN);
	}

	@Override
	public void sceneInit() {
		this.gp = new GridPane();
		this.scene = new Scene(gp, 600, 600);
	}

	@Override
	public void componentsInit() {
		doubleClickValueToEdit = new Label(" Double click value to edit.");
		doubleClickValueToEdit.setFont(new Font("Arial",15));
		doubleClickValueToEdit.setTextFill(Color.RED);
		titleText = new Label("Answers");
		titleText.setFont(new Font("Arial", 30));
		titleText.setTextFill(Color.DARKBLUE);
		titleText.setUnderline(true);
		selectToDelete = new Label("Select a value for deletion");
		selectToDelete.setTextFill(Color.RED);
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
					try {
						l.handleCloseButtonAction(event, closeWindow);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
					}
				}
			}
		});
		return closeWindow;	
	}
}
