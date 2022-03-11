package id319520425;

import java.util.Scanner;
import java.util.ArrayList;

public class OpenQ extends Question {
	
	public OpenQ(String question, String answer) {
		super(question, answer);
	}
	
	
	@Override
	public String toString() {
		return "OpenQ []";
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
}
