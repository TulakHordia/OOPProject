package View;

import java.io.File;
import java.util.List;

import Listeners.MainUiListener;

public interface AbstractExistingExams {

	void registerListener(MainUiListener listener);
	void loadExamsIntoTable(List<File> list);
	void copiedAnExistingExamPopUpDialog(File fileName);
	
}
