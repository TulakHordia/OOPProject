package Model;

import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable {
	
	private static int questionNumCounter = 1;
	private int questionNumber;
	protected String question;

	public Question(String question) {
		this.question = question;
		questionNumber = questionNumCounter++;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public void setQuestionNumber(List<Question> allQuestions) {
		questionNumCounter = (allQuestions.size()+1);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String printQuestionNumber() {
		return questionNumber + ")" + toString();
	}
	
	@Override
	public String toString() {
		return question + "\n";
	}
	
}
