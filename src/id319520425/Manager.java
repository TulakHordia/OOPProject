package id319520425;

import java.util.Arrays;
import java.util.Scanner;

import id319520425.AmericanQ.eAddAns;

public class Manager {
	
	private Question[] allQuestions;
	private int amountOfQuestions;
	
	Scanner input = new Scanner(System.in);

	public Manager() {
		allQuestions = new Question[1];
		amountOfQuestions = 0;
	}
	
	public boolean addOpenQuestions(String question, String answer) {
		for (int i = 0; i < amountOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question)) {
				return false;
			}
		}
		if (amountOfQuestions == allQuestions.length) {
			allQuestions = Arrays.copyOf(allQuestions, allQuestions.length*2);
		}
		allQuestions[amountOfQuestions] = new OpenQ(question, answer);
		amountOfQuestions++;
		return true;
	}
	
	
	public boolean addAmericanQuestion(AmericanQ question) {
		for (int i = 0; i < amountOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question.getQuestion())) {
				return false;
			}
		}
		if (amountOfQuestions == allQuestions.length) {
			allQuestions = Arrays.copyOf(allQuestions, allQuestions.length*2);
		}
		allQuestions[amountOfQuestions] = question;
		amountOfQuestions++;
		return true;
	}
	
	private Question getQuestionById(int questionNumber) {
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i].getQuestionNumber() == questionNumber) {
				return allQuestions[i];
			}
		}
		return null;
	}
	
	public String updateQuestion(int questionNumber, String question) {
		Question quest = getQuestionById(questionNumber);
		if (quest != null) {
			quest.setQuestion(question);
			return "Updated successfully.";
		}
		return "Question does not exist.";
	}

	public String deleteAnswer(int questionNumber, int loc) {
		Question quest = getQuestionById(questionNumber);
		if (quest == null) {
			return "Question does not exist.";
		}
		if (quest instanceof OpenQ) {
			return "Cannot delete an answer from an open question.";
		}
		AmericanQ ameriQ = (AmericanQ) quest;
		if (loc > ameriQ.getAnswersNum() || loc < 1) {
			return "Answer does not exist.";
		}
		ameriQ.deleteAnswer(loc);
		return "Deleted successfully.";
	}
	
	public String updateAnswer(int questionNumber, int loc, String newAnswer) {
		Question quest = getQuestionById(questionNumber);
		if (quest == null) {
			return "Question does not exist.";
		}
		if (quest instanceof OpenQ) {
			OpenQ open = (OpenQ) quest;
			open.setCorrectAnswer(newAnswer);
		}
		if (quest instanceof AmericanQ) {
			AmericanQ ameriQ = (AmericanQ) quest;
			if (loc > ameriQ.getAnswersNum() || loc < 1) {
				return "Answer does not exist.";
			}
			ameriQ.updateAnswer(loc, newAnswer);
		}
		return "Updated successfully.";
	}
	
	public void printEverything() {
		System.err.println("Printing all questions");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] != null) {
				System.out.println(allQuestions[i]);
			}
		}
	}
	
	public void printQuestionsOnly() {
		System.out.println("-----All Questions-----");
		System.out.println("\nAmerican questions: ");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] != null) {
				if (allQuestions[i] instanceof AmericanQ) {
					AmericanQ aQ = (AmericanQ) allQuestions[i];
					System.out.println("[" + allQuestions[i].getQuestionNumber() + "] " + allQuestions[i].getQuestion());
				}
			}
		}
		System.out.println("\nOpen questions: ");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions[i];
				System.out.println("[" + allQuestions[i].getQuestionNumber() + "] " + allQuestions[i].getQuestion());
			}
		}
	}
	
	public void printAmerican() {
		System.err.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) allQuestions[i];
				System.out.println(allQuestions[i].toString());
			}
		}
	}
	
	public void printOpen() {
		System.err.println("-----Open Questions-----");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions[i];
				System.out.println(allQuestions[i].toString());
			}
		}
	}
	
	public void autoCreateExam(int amount) {
		
	}
}
