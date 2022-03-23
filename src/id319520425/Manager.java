package id319520425;

import java.util.Arrays;
import java.util.Random;
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
		if (questionNumber == allQuestions.length) {
			return null;
		}
		if (allQuestions[questionNumber] == null) {
			getQuestionById(questionNumber+1);
		}
		for (int i = 0; i < allQuestions.length; i++) {
			while (allQuestions[i] != null) {
				if (allQuestions[i].getQuestionNumber() == questionNumber) {
					return allQuestions[i];
				}
				break;
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
				System.out.println(allQuestions[i]);
			}
		}
	}
	
	public void printOpen() {
		System.err.println("-----Open Questions-----");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions[i];
				System.out.println(allQuestions[i]);
			}
		}
	}
	
	public void sortAndPrintAutoExamArray(Question[] array) {
		Question temp;
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if (array[i].getQuestion().compareTo(array[j].getQuestion())> 0) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				System.out.println(array[i]);
			}
		}
	}
	
	public boolean checkQuestionIsInArray(Question[] array, Question quest) {
		if (quest == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				if (array[i].getQuestion().equals(quest.getQuestion())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Question generateNewQuestion(Question[] array) {
		Random rand = new Random();
		Question quest = null;
		while (quest == null) {
			int n = rand.nextInt(allQuestions.length);
			quest = getQuestionById(n);	
			if(checkQuestionIsInArray(array, quest)) {
				quest = null;
			}
			
		}
		return quest;
	}
	
	public void autoCreateExam(int amount) {
		Question[] autoExamArray = new Question[amount];
		for (int i = 0; i < amount; i++) {
			Question quest = generateNewQuestion(autoExamArray);
			if (quest instanceof OpenQ) {
				OpenQ open = (OpenQ) quest;
				autoExamArray[i] = open;
			}
			if (quest instanceof AmericanQ) {
				AmericanQ ameriQ = (AmericanQ) quest;
				autoExamArray[i] = ameriQ;
				int trueCounter = 0;
				int falseCounter = 0;
				for (int j = 0; j < 4 ; j++) {
					if (ameriQ.getAnswers(j).IsTrue()) {
						trueCounter++;
					}
					else {
						falseCounter++;
					}
				}
				if (trueCounter == 1) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", false));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", false));
				}
				if (trueCounter > 1) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", false));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", true));
				}
				if (falseCounter == 4 && trueCounter == 0) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", true));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", false));
				}
			}
		}
		sortAndPrintAutoExamArray(autoExamArray);
	}
	
	public boolean checkInstanceOfQuestion(int questionNum) {
		Question quest = getQuestionById(questionNum);
		if (quest instanceof OpenQ) {
			return true;
		}
		if (quest instanceof AmericanQ) {
			return false;
		}
		return false;
	}
	
	public void addAmericanAnswersToManualExam(int questionNum, int answerNum) {
		
	}
	
	public void createManualExam(int amountOfQuestions, int questionNum) {
		Question[] manualExamArray = new Question[amountOfQuestions];
		if (amountOfQuestions == manualExamArray.length) {
			manualExamArray = Arrays.copyOf(manualExamArray, manualExamArray.length*2);
		}
		for (int i = 0; i < manualExamArray.length; i++) {
			Question quest = getQuestionById(questionNum);
			if (quest instanceof OpenQ) {
				OpenQ open = (OpenQ) quest;
				manualExamArray[i] = open;
				System.out.println("Question added succesfully, new question num: " + (i+1));
			}
			if (quest instanceof AmericanQ) {
				AmericanQ ameriQ = (AmericanQ) quest;
				manualExamArray[i] = ameriQ;
				System.out.println("Question added succesfully, new question num: " + (i+1));
				
				
				int trueCounter = 0;
				int falseCounter = 0;
				for (int j = 0; j < 4 ; j++) {
					if (ameriQ.getAnswers(j).IsTrue()) {
						trueCounter++;
					}
					else {
						falseCounter++;
					}
				}
				if (trueCounter == 1) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", false));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", false));
				}
				if (trueCounter > 1) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", false));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", true));
				}
				if (falseCounter == 4 && trueCounter == 0) {
					ameriQ.addAnswer(new AmericanAnswers("Nothing is correct", true));
					ameriQ.addAnswer(new AmericanAnswers("More than one answer is correct", false));
				}
			}
		}
	}
}
