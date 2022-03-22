package id319520425;

import java.util.Objects;

public class AmericanAnswers {

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
		AmericanAnswers other = (AmericanAnswers) obj;
		return Objects.equals(answer, other.answer);
	}
	
	public String toString() {
		return answer;
	}
}
