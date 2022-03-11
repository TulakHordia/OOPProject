package id319520425;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class AmericanQ extends Question {

	protected AmericanAnswers[] Answers;
	
	public AmericanQ(String question, String answer) {
		super(question, answer);
		Answers = new AmericanAnswers[10];
	}

	@Override
	public String toString() {
		return "AmericanQ [possibleAnswers=" + Arrays.toString(Answers) + "]";
	}

	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
}
