package Model;

import java.io.Serializable;

public class OpenQ extends Question implements Serializable {
	
	private String answer;
	
	public OpenQ(String question, String answer) {
		super(question);
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return getQuestionNumber() + ") Open Questions: \n" + getQuestion() + "\nThe answer: " + getAnswer() + "\n";
	}

	public String setAnswer() {
		return answer;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
	
	public void setCorrectAnswer(String newAnswer) {
		this.answer = newAnswer;
	}
}
