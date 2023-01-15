package View;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import Listeners.MainUiListener;
import Model.UiElements;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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

public class ExistingExamsView implements AbstractExistingExams, UiElements {

	private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
	private Scene scene;
	private GridPane gp;
	private Label titleText;
	private Label selectExamToCopy;
	private TableView<File> existingExamsTable;
	private TableColumn<File, String> existingExamsColumn;
	
	public ExistingExamsView(Stage stage) {
		stage.setTitle("Existing Exams");
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
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		this.existingExamsTable = new TableView<File>();
		this.existingExamsColumn = new TableColumn<File, String>("Exams");

		existingExamsTable.setMinSize(300, 300);
		existingExamsTable.setEditable(true);
		
		existingExamsColumn.setSortable(false);
		existingExamsColumn.setMinWidth(300);
		existingExamsColumn.setMaxWidth(300);
		existingExamsColumn.setCellValueFactory(new PropertyValueFactory<File, String>("name"));

		existingExamsTable.getColumns().addAll(existingExamsColumn);
	}
	
	@Override
	public void registerListener(MainUiListener listener) {
		questUiListeners.add(listener);
	}
	
	
	@Override
	public void sceneInit() {
		this.gp = new GridPane();
		this.scene = new Scene(gp, 600, 600);
	}
	@Override
	public void componentsInit() {
		titleText = new Label("Existing Exams");
		titleText.setFont(new Font("Arial",30));
		titleText.setTextFill(Color.DARKBLUE);
		titleText.setUnderline(true);
		selectExamToCopy = new Label("Select an exam to copy");
		selectExamToCopy.setTextFill(Color.RED);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void gridPane() {
		gp.setHgap(2);
		gp.setVgap(10);
		gp.setPadding(new Insets(10));
		gp.setAlignment(Pos.CENTER);
		gp.add(titleText, 0, 0);
		gp.setHalignment(titleText, HPos.CENTER);
		gp.add(existingExamsTable, 0, 2);
		gp.add(selectExamToCopy, 0, 3);
		gp.add(copyExamButton(), 0, 4);
		gp.setHalignment(copyExamButton(), HPos.CENTER);
		
	}
	@Override
	public void closeStage(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	        if (KeyCode.ESCAPE == event.getCode()) {
	            stage.close();
	        }
	    });
	}
	

	public Button copyExamButton() {
		Button copyExam = new Button();
		copyExam.setText("Copy Exam");
		copyExam.setPrefSize(140, 25);
		copyExam.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				for (MainUiListener l : questUiListeners) {
					File f = existingExamsTable.getSelectionModel().getSelectedItem();
					try {
						l.copyExam(f);
					} catch (IOException e) {
						errorMessageUi(e.getMessage());
					}
				}
			}
		});
		return copyExam;	
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
	
	public void errorMessageUi(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void loadExamsIntoTable(List<File> exams) {
		existingExamsTable.getItems().addAll(exams);
	}

	@Override
	public void copiedAnExistingExamPopUpDialog(File fileName) {
		JOptionPane.showMessageDialog(null, "Copied exam: "+"\n"+fileName.getName());
	}
}
