package id319520425;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Manager {

	private Question[] allQuestions;
	private int amountOfQuestions;
	private Question[] manualExamArray;
	private int size;

	Scanner input = new Scanner(System.in);

	public Manager() {
		allQuestions = new Question[1];
		amountOfQuestions = 0;
		size = 0;
	}

	public void setSize(int size) {
		this.size = size;
		manualExamArray = new Question[size];
	}

	public boolean addOpenQuestions(String question, String answer) {
		for (int i = 0; i < amountOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question)) {
				System.out.println("Question already exists.");
				return false;
			}
		}
		if (amountOfQuestions == allQuestions.length) {
			allQuestions = Arrays.copyOf(allQuestions, allQuestions.length * 2);
		}
		allQuestions[amountOfQuestions] = new OpenQ(question, answer);
		System.out.println("Created question #" + (amountOfQuestions + 1));
		amountOfQuestions++;
		return true;
	}

	public boolean addAmericanQuestion(AmericanQ question) {
		for (int i = 0; i < amountOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question.getQuestion())) {
				System.out.println("Question already exists.");
				return false;
			}
		}
		if (amountOfQuestions == allQuestions.length) {
			allQuestions = Arrays.copyOf(allQuestions, allQuestions.length * 2);
		}
		allQuestions[amountOfQuestions] = question;
		System.out.println("Created question #" + (amountOfQuestions + 1));
		amountOfQuestions++;
		return true;
	}

	private Question getQuestionById(int questionNumber) {
		if (questionNumber == allQuestions.length) {
			return null;
		}
		if (allQuestions[questionNumber] == null) {
			getQuestionById(questionNumber + 1);
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
		System.out.println("Printing all questions");
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
					System.out.println(allQuestions[i].getQuestionNumber() + ") " + allQuestions[i].getQuestion());
				}
			}
		}
		System.out.println("\nOpen questions: ");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof OpenQ) {
				System.out.println(allQuestions[i].getQuestionNumber() + ") " + allQuestions[i].getQuestion());
			}
		}
	}

	public void printAmerican() {
		System.out.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof AmericanQ) {
				System.out.println(allQuestions[i]);
			}
		}
	}

	public void printOpen() {
		System.out.println("-----Open Questions-----");
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] instanceof OpenQ) {
				System.out.println(allQuestions[i]);
			}
		}
	}

	public void printAmericanAnswers(int questionNum) {
		System.out.println("American Answers for questions number: " + questionNum);
		Question quest = getQuestionById(questionNum);
		if (quest instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) quest;
			for (int i = 0; i < 10; i++) {
				if (aQ.getAnswers(i) != null) {
					System.out.println("[" + (i + 1) + "] " + aQ.getAnswers(i));
				}
			}
		}
	}
	
	public void sortAndPrintAutoExamArray(Question[] array) {
		Question temp;
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].getQuestion().compareTo(array[j].getQuestion()) > 0) {
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
		System.out.println("Exam created, contains: " + array.length + " questions");
	}

	public void sortAndPrintManualExamArray(int[] answersArray) {
		for (int i = 0; i < manualExamArray.length; i++) {
			if (manualExamArray[i] != null) {
				if (manualExamArray[i] instanceof OpenQ) {
					System.out.println(manualExamArray[i].toString());
				}
				if (manualExamArray[i] instanceof AmericanQ) {
					AmericanQ aQ = (AmericanQ) manualExamArray[i];
					addBuiltInAnswers(aQ);
					System.out.println(manualExamArray[i].toString());
				}
			}
		}
		int counter = 0;
		for (int i = 0; i < manualExamArray.length; i++) {
			if (manualExamArray[i] != null) {
				counter++;
			}
		}
		System.out.println("Exam created, contains: " + counter + " questions");
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
			if (checkQuestionIsInArray(array, quest)) {
				quest = null;
			}

		}
		return quest;
	}

	public int checkAllQuestionsLength() {
		int counter = 0;
		for (int i = 0; i < allQuestions.length; i++) {
			if (allQuestions[i] != null) {
				counter++;
			}
		}
		return counter;
	}

	public void addBuiltInAnswers(AmericanQ answers) {
		int trueCounter = 0;
		int falseCounter = 0;
		for (int j = 0; j < 10; j++) {
			if (answers.getAnswers(j) != null) {
				if (answers.getAnswers(j).IsTrue()) {
					trueCounter++;
				} else {
					falseCounter++;
				}
			}
		}
		if (trueCounter == 1) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", false));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", false));
		}
		if (trueCounter > 1) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", false));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", true));
		}
		if (falseCounter > 0 && trueCounter == 0) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", true));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", false));
		}
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
				addBuiltInAnswers(ameriQ);
			}
		}
		sortAndPrintAutoExamArray(autoExamArray);
	}

	public boolean checkInstanceOfQuestion(int questionNum) {
		Question quest = getQuestionById(questionNum);
		if (quest instanceof OpenQ) {
			return false;
		}
		if (quest instanceof AmericanQ) {
			return true;
		}
		return false;
	}

	public void getAmericanAnswer(int questionNum) {
		Question quest = getQuestionById(questionNum);
		if (quest instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) quest;
			for (int i = 0; i < 10; i++) {
				if (aQ.getAnswers(i) != null) {
					System.out.println("[" + (i + 1) + "] " + aQ.getAnswers(i));
				}
			}
		}
	}

	public boolean addAmericanQuestionToManualExam(AmericanQ question, int[] answersArray) {
		for (int i = 0; i < size; i++) {
			if (manualExamArray[i] != null) {
				if (manualExamArray[i].getQuestion().equals(question.getQuestion())) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		for (int i = 0; i < manualExamArray.length; i++) {
			if (manualExamArray[i] == null) {
				AmericanQ test = new AmericanQ(question.getQuestion());
				manualExamArray[i] = test;
				for (int j = 0; j < answersArray.length; j++) {
					if (answersArray[j] != 0) {
						int temp = answersArray[j];				
						test.addAnswer(new AmericanAnswers(question.getAnswers(temp-1)));
					}
				}
				addBuiltInAnswers(test);
				System.out.println("Added question #" + (i + 1));
				return true;
			}
		}
		return false;
	}

	public boolean addOpenQuestionToManualExam(String question, String answer) {
		for (int i = 0; i < size; i++) {
			if (manualExamArray[i] != null) {
				if (manualExamArray[i].getQuestion().equals(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		for (int i = 0; i < manualExamArray.length; i++) {
			if (manualExamArray[i] == null) {
				manualExamArray[i] = new OpenQ(question, answer);
				System.out.println("Added question #" + (i + 1));
				return true;
			}
		}
		return false;
	}

	public boolean addQuestionToManualExam(int questionNum, int[] answersArray) {
		Question question = getQuestionById(questionNum);
		if (question instanceof OpenQ) {
			OpenQ open = (OpenQ) question;
			addOpenQuestionToManualExam(open.getQuestion(), open.getAnswer());
			return true;
		}
		if (question instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) question;
			addAmericanQuestionToManualExam(aQ, answersArray);
			return true;
		}
		return false;
	}
	
	public void createManualExamAndSendToPrint(int[] answersArray, int amountOfQuestions) {
		if (amountOfQuestions == manualExamArray.length) {
			sortAndPrintManualExamArray(answersArray);
		}
	}
	
	public void menuEndMessage() {
		System.out.println("\nDo you want to go back to the main menu? (y/n)");

		char resume = input.next().charAt(0);
		if(resume=='y'||resume=='Y') {
		}else
		{
			System.out.println("Exiting program...");
			System.exit(0);
		}
	}
	
	public int safeNextInt(Scanner e) {
		int num = 0;
		boolean invalid = false;
		do {
			invalid = false;
			try {
				num = e.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Expected numerical value.");
				invalid = true;
			}
			e.nextLine();
		} while (invalid);	
		return num;
	}
	
	public boolean safeNextBoolean(Scanner e) {
		boolean crembo = false;
		boolean invalid = false;
		do {
			invalid = false;
			try {
				crembo = e.nextBoolean();
			} catch (InputMismatchException exception) {
				System.out.println("Expected boolean value (true/false).");
				invalid = true;
			}
			e.nextLine();
		} while (invalid);
		return crembo;
	}
}
