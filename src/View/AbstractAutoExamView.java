package View;

import java.util.List;

import Listeners.MainUiListener;
import Model.Question;

public interface AbstractAutoExamView {

	void registerListener(MainUiListener listener);
	void loadExamIntoTable(int amount, List<Question> exam);
}
