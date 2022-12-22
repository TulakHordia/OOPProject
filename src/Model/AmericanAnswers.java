package Model;

import java.io.Serializable;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AmericanAnswers implements Serializable {

	private boolean isTrue;
	private String answer;
	private String booleanAnswer;
	
	public AmericanAnswers(String answer, boolean isTrue) {
		setIsTrue(isTrue);
		setAnswer(answer);
		if (isTrue) {
			booleanAnswer = "true";
		}
		else {
			booleanAnswer = "false";
		}
	}
	
	public AmericanAnswers(AmericanAnswers other) {
		setIsTrue(other.isTrue);
		setAnswer(other.answer);
	}

	public boolean getIsTrue() {
		return isTrue;
	}
	
	public String setBooleanAnswer(String booleanAns) {
		booleanAnswer = booleanAns;
		return booleanAnswer;
	}
	public String getBooleanAnswer() {
		return booleanAnswer;
	}

	public void setIsTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}
	
	public Boolean setTrueString(String isTrue) {
		if (isTrue.equalsIgnoreCase("true")) {
			booleanAnswer = "true";
			setIsTrue(true);
			return true;
		}
		if (isTrue.equalsIgnoreCase("false")) {
			booleanAnswer = "false";
			setIsTrue(false);
			return false;
		}
		else {
			String m = JOptionPane.showInputDialog("true/false");
			setTrueString(m);
			return true;
		}
	}
	
	public String errorMessage() {
		return "Invalid input, true/false only";
	}

	public String getAnswer() {
		return answer;
	}

	public boolean setAnswer(String answer) {
		this.answer = answer;
		return true;
	}
	
	public String toString() {
		return isTrue+": "+answer;
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if ( !(obj instanceof AmericanAnswers) ) {
			return false;
		}
		if (obj == null) {
			return false;
		}
		AmericanAnswers other = (AmericanAnswers) obj;
		return other.getAnswer().equals(this.answer);
	}
}
