package id319520425;

import java.util.Objects;

public abstract class Question {
	
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
	
	public String getQuestion() {
		return question;
	}
	
	public boolean setQuestion(String question) {
		this.question = question;
		return true;
	}
	
	public String printQuestionNumber() {
		return "[" + questionNumber + "]" + toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Question other = (Question) obj;
		return Objects.equals(question, other.question);
	}
	
	@Override
	public String toString() {
		return question + "\n";
	}
}
