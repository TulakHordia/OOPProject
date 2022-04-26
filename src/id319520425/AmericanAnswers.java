package id319520425;

import java.io.Serializable;

public class AmericanAnswers implements Serializable {

	private boolean isTrue;
	private String answer;
	
	public AmericanAnswers(String answer, boolean isTrue) {
		setIsTrue(isTrue);
		setAnswer(answer);
	}
	
	public AmericanAnswers(AmericanAnswers other) {
		setIsTrue(other.isTrue);
		setAnswer(other.answer);
	}

	public boolean IsTrue() {
		return isTrue;
	}

	public void setIsTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public String getAnswer() {
		return answer;
	}

	public boolean setAnswer(String answer) {
		this.answer = answer;
		return true;
	}
	
	public String toString() {
		return answer;
	}
}
