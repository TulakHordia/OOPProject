package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

interface ProgramMethods {

	void managingMethod() throws SQLException, ClassNotFoundException;
	void menuOptions();
	void mainMenu() throws IOException, ClassNotFoundException, SQLException;
	void autoImportQuestions() throws ClassNotFoundException, IOException, SQLException;
	boolean importAndSaveQuestionsList() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
}
