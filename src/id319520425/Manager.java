package id319520425;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Manager {
	
	private Question[] allQuestions;
	private final int ENLARGE_FACTOR=2;
	private int in;
	
	Scanner input = new Scanner(System.in);
	
	public Manager() {
		allQuestions = new Question[ENLARGE_FACTOR];
		in = 0;
	}
	
	public String toString() {
		StringBuffer back = new StringBuffer();
		for (int i = 0; i < in; i++) {
			back.append(allQuestions[i].toString() + " ");
		}
		return back.toString();
	}
	
	public Question getDataIn(int place) {
		return allQuestions[place];
	}
	
	public void addQuestions(String question, String answer) {
		if (in == allQuestions.length) {
			enlargeArray();
		}
		OpenQ open = new OpenQ(question, answer);
		allQuestions[in++] = open;
	}
	
	public void addAmericanQuestion(String question, String answer, boolean isTrue) {
		AmericanAnswers americanRelay = new AmericanAnswers(answer, isTrue);
		AmericanQ ameriQ = new AmericanQ(question, americanRelay);
		if (in == allQuestions.length) {
			enlargeArray();
		}
		allQuestions[in++] = ameriQ;
	}
	
	private void enlargeArray() {
		System.out.println("Enlarging");
		Question[] temp = new Question[allQuestions.length*ENLARGE_FACTOR];
		for (int i = 0; i < allQuestions.length; i++) {
			temp[i] = allQuestions[i];
		}
		allQuestions = temp;
	}
	
}
