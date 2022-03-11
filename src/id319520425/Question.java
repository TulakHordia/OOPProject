package id319520425;

import java.util.ArrayList;
import java.util.Collections;
//
public abstract class Question {
	
	protected static int questionNumCounter = 0;
	protected int questionNumber;
	protected String question;
	protected String answer;
	
	Manager da = new Manager();
	
	public Question(String question, String answer) {
		questionNumCounter++;
		this.questionNumber = questionNumCounter;
		this.question = question;
		this.answer = answer;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
}
