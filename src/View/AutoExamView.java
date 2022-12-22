package View;

import java.util.List;
import java.util.Vector;

import Listeners.MainUiListener;
import Model.Question;
import Model.UiElements;
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

public class AutoExamView implements AbstractAutoExamView, UiElements {
	
	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	private Scene scene;
	private GridPane gp;
	private Label titleText;
	private Label createdText;
	private TableView<Question> questionsTable;
	private TableColumn<Question, String> questionsColumn;

	
	@Override
	public void registerListener(MainUiListener listener) {
		questUiListeners.add(listener);
	}

	public AutoExamView(Stage stage) {
		stage.setTitle("Exam");
		stage.setWidth(340);
		stage.setHeight(600);
		stage.setResizable(false);
		this.gp = new GridPane();
		this.scene = new Scene(gp, 600, 600);
		closeStage(stage);
		createTable();
		componentsInit();
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
	private void createTable() {
		this.questionsTable = new TableView<Question>();
		this.questionsColumn = new TableColumn<Question, String>("Questions");

		questionsTable.setMinSize(300, 300);
		questionsTable.setEditable(true);
		
		questionsColumn.setSortable(false);
		questionsColumn.setMinWidth(300);
		questionsColumn.setMaxWidth(300);
		questionsColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
		questionsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		questionsColumn.setOnEditCommit(new EventHandler<CellEditEvent<Question, String>>() {

			@Override
			public void handle(CellEditEvent<Question, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuestion(event.getNewValue());
			}
		});
		questionsTable.getColumns().addAll(questionsColumn);
	}

	@Override
	public void sceneInit() {
		this.gp = new GridPane();
		this.scene = new Scene(gp, 600, 600);
	}
	

	@SuppressWarnings("static-access")
	@Override
	public void gridPane() {
		gp.setHgap(2);
		gp.setVgap(10);
		gp.setPadding(new Insets(10));
		gp.add(titleText, 0, 0);
		gp.setHalignment(titleText, HPos.CENTER);
		gp.add(questionsTable, 0, 2);
		gp.add(createdText, 0, 3);
		gp.setHalignment(createdText, HPos.CENTER);
	}

	@Override
	public void componentsInit() {
		titleText = new Label("Exam");
		titleText.setFont(new Font("Arial",30));
		titleText.setTextFill(Color.DARKBLUE);
		titleText.setUnderline(true);
		createdText = new Label("Created");
		createdText.setFont(new Font("Arial",30));
		createdText.setTextFill(Color.RED);
		createdText.setUnderline(true);
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
	public void loadExamIntoTable(int amount, List<Question> exam) {
		questionsTable.getItems().addAll(exam);
	}

}
