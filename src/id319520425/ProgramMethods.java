package id319520425;

import java.io.FileNotFoundException;
import java.io.IOException;

interface ProgramMethods {

	void questionsList();
	void managingMethod();
	void menuOptions();
	void mainMenu() throws IOException, ClassNotFoundException;
	void autoImportQuestions() throws ClassNotFoundException, IOException;
	boolean importAndSaveQuestionsList() throws FileNotFoundException, IOException;

}
