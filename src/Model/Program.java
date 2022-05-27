package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Controllers.MainController;
import View.AbstractMainView;
import View.QuestionView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application implements ProgramMethods  {
	
	private Scanner input;
	private Manager manage;
	private int choice;
	
	@Override
	public void mainMenu() throws IOException, ClassNotFoundException {
		
		do {
			menuOptions();
			
			choice = manage.safeNextInt(input);

			switch (choice) {

			case 1:

				System.out.println("-----Show all questions menu-----");
				System.out.println("[1] - Show all questions.");
				System.out.println("[2] - Show only American-type questions (With their correct answers).");
				System.out.println("[3] - Show only Open-type questions.");
				System.out.println("[4] - Show available questions only.");
				int showChoice = manage.safeNextInt(input);
				if (showChoice == 1) {
					manage.printEverything();	
				}
				if (showChoice == 2) {
					manage.printAmerican();
				}
				if (showChoice == 3) {
					manage.printOpen();
				}
				if (showChoice == 4) {
					manage.printQuestionsOnly();
				}
				break;

			case 2:

				System.out.println("----Add a question-----");
				System.out.println("[1] - Add an american-type question.");
				System.out.println("[2] - Add an open-type question.");
				int addQuestionChoice = manage.safeNextInt(input);
				while (addQuestionChoice == 1 || addQuestionChoice == 2) {
					if (addQuestionChoice == 1) {
						System.out.println("Please enter your question below: ");
						String ameriQuestion = input.nextLine();
						AmericanQ ameriC = new AmericanQ(ameriQuestion);
						if (!manage.addAmericanQuestion(ameriC)) {
							break;
						}
						else {
							System.out.println("How many answers do you want to add? (2 to 6) ");
							int answersAmount = manage.safeNextInt(input);
							for (int i = 0; i < answersAmount; i++) {
								System.out.println("Please enter answer number " + (i+1) + ": ");
								String answer = input.nextLine();
								System.out.println("Is it a correct answer or not? (true/false): ");
								boolean isTrue = manage.safeNextBoolean(input);
								System.out.println(ameriC.addAnswer(new AmericanAnswers(answer, isTrue)));
							}
						}
						break;
					}
					else if (addQuestionChoice == 2) {
						System.out.println("Please enter your question below: ");
						String question = input.nextLine();
						System.out.println("Please enter an answer: ");
						String answer = input.nextLine();
						if (!manage.addOpenQuestions(question, answer)) {
							break;
						}
						break;
					}
				}
				break;

			case 3:

				System.out.println("See the full list of questions&answers?? y/Y");
				char newChoice = input.next().charAt(0);
				if (newChoice == 'y' || newChoice == 'Y') {
					manage.printQuestionsOnly();
				}
				System.out.println("Please choose the question number you want to update: ");
				int updateChoice = manage.safeNextInt(input);
				System.out.println("Enter new text below: ");
				String newQuestion = input.nextLine();
				System.out.println(manage.updateQuestion(updateChoice, newQuestion));
				break;

			case 4:
				
				System.out.println("Would you like to update an American answer or Open answer?");
				System.out.println("Type 1 for American, 2 for Open.");
				int updateAnswerChoice =  manage.safeNextInt(input);
				if (updateAnswerChoice == 1) {
					System.out.println("See the full list of questions&answers?? y/Y");
					char newChoice1 = input.next().charAt(0);
					if (newChoice1 == 'y' || newChoice1 == 'Y') {
						manage.printAmerican();
					}
					System.out.println("Please select from which question you want to update an answer: ");
					int questionNum = manage.safeNextInt(input);
					manage.getAmericanAnswer(questionNum);
					System.out.println("Please select which answer: ");
					int answerNum =  manage.safeNextInt(input);
					System.out.println("Please enter the new answer: ");
					String newAnswer = input.nextLine();
					System.out.println(manage.updateAnswer(questionNum, answerNum, newAnswer));
				}
				if (updateAnswerChoice == 2) {
					System.out.println("See the full list of questions&answers?? y/Y");
					char newChoice1 = input.next().charAt(0);
					if (newChoice1 == 'y' || newChoice1 == 'Y') {
						manage.printOpen();
					}
					System.out.println("Please select from which question you want to update an answer: ");
					int questionNum =  manage.safeNextInt(input);
					System.out.println("Please enter the new answer: ");
					String newAnswer = input.nextLine();
					System.out.println(manage.updateAnswer(questionNum, questionNum, newAnswer));
				}
				break;

			case 5:

				System.out.println("See the full list of questions&answers? y/Y");
				char newChoice2 = input.next().charAt(0);
				if (newChoice2 == 'y' || newChoice2 == 'Y') {
					System.out.println("Please note");
					System.out.println("You can only delete from American-type questions.");
					manage.printAmericanQuestionsOnly();
				}
				System.out.println("Please select from which question you want to delete an answer: ");
				int questionNum1 =  manage.safeNextInt(input);
				while (!manage.checkInstanceOfQuestion(questionNum1)) {
					System.out.println("Cannot delete from an Open Question, select a different one: ");
					questionNum1 =  manage.safeNextInt(input);
				}
				manage.getAmericanAnswer(questionNum1);
				System.out.println("Please select which answer: ");
				int answerNum1 =  manage.safeNextInt(input);
				System.out.println(manage.deleteAnswer(questionNum1, answerNum1));
				break;
				
			case 6:
				
				System.out.println("How many questions do you want in your exam? ");
				int amountOfQuestions =  manage.safeNextInt(input);
				manage.setSize(amountOfQuestions);
				manage.printQuestionsOnly();
				int answersAmount = 0;
				List<Integer> answersArray = new ArrayList<>();
				System.out.println("Please choose the question you wish to add: ");
				for (int i = 0; i < amountOfQuestions; i++) {
					int questionNum =  manage.safeNextInt(input);
						if (!manage.checkInstanceOfQuestion(questionNum)) {
							manage.addQuestionToManualExam(questionNum, null);
						}
						else {
							System.out.println("Please choose the amount of answers you want for the question: ");
							answersAmount =  manage.safeNextInt(input);
							System.out.println("These are the answers for question #"+questionNum);
							manage.getAmericanAnswer(questionNum);
							System.out.println("Please select the answers you want: ");
							for (int j = 0; j < answersAmount; j++) {
								answersArray.add(manage.safeNextInt(input));
							}
							manage.addQuestionToManualExam(questionNum, answersArray);
						}
				}
				manage.sortAndPrintManualExamArray();
				break;
				
			case 7:
				
				System.out.println("How many questions would you like in your exam? ");
				System.out.println("There are only: " + manage.checkAllQuestionsLength() + " Questions available.");
				int questAmount =  manage.safeNextInt(input);
				while (questAmount <= 0 || questAmount > manage.checkAllQuestionsLength()) {
					System.out.println("Invalid amount of question, please select again: ");
					questAmount =  manage.safeNextInt(input);
				}
				manage.autoCreateExam(questAmount);
				break;
				
			case 8:
				
				manage.sortAllQuestions();
				break;
				
			case 9:
				
				System.out.println("Please enter the name of the file you want to import: ");
				System.out.println("--Make sure the name ends with the correct ending (Ex - .txt, .dat, .ser)--");
				String fileName = input.nextLine();
				manage.readFromBinaryFile(fileName);
				break;
				
			case 10:
				
				System.out.println("Please enter the name of the file you wish to save to: ");
				System.out.println("--Make sure the name ends with the correct ending (Ex - .txt, .dat, .ser)--");
				String fileName2 = input.nextLine();
				manage.writeAllExternally(fileName2);
				break;
				
			case 11:
				
				System.out.println("All existing exams: ");
				manage.showAllExistingExamsInDirectory();
				System.out.println("Please choose which one you wish to copy: ");
				int copyChoice = manage.safeNextInt(input);
				manage.copyExistingExam(copyChoice);
				break;	
				
			case 12:
				
				manage.questionsList();
				break;
				
			case 13:
				
				manage.saveToBinaryFile();
				System.out.println("Saving and exiting...");
				System.exit(0);
				break;
				
			default:
				System.out.println("Invalid option, please choose again.");
			}
			System.out.println("\nDo you want to go back to the main menu? (y/n)");

			char resume = input.next().charAt(0);
			if(resume=='y'||resume=='Y') {
			}else
			{
				manage.saveToBinaryFile();
				System.out.println("Exiting program...");
				System.exit(0);
			}
		}
		while(choice != 13);
		manage.saveToBinaryFile();
		System.out.println("Exiting program...Thank you!");
		input.close();
		}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Program p = new Program();
		p.managingMethod();
		launch(args);
		if (!p.importAndSaveQuestionsList()) {
			p.autoImportQuestions();
		}

		p.mainMenu();
		}
	
	@Override
	public boolean importAndSaveQuestionsList() throws FileNotFoundException, IOException {
		System.out.println("Would you like to import the pre-made 'questions list'? ");
		System.out.println("Type 1 if yes, 2 if no.");
		int qChoice = manage.safeNextInt(input);
		if (qChoice == 1) {
			manage.questionsList();
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void managingMethod() {
		this.input = new Scanner(System.in);
		this.manage = new Manager();
		this.choice = 0;
	}
	

	@Override
	public void autoImportQuestions() throws ClassNotFoundException, IOException {
		try {
			manage.autoImportOnLaunch();
		} catch (FileNotFoundException e) {
			System.out.println("questions.ser file does not exist.");
			System.out.println("Importing pre-made questions list.");
			manage.questionsList();
		}
	}
	

	@Override
	public void menuOptions() {
		System.out.println("\n--------Exam creator--------");
		System.out.println("--------Select option:--------\n");
		System.out.println("[1] - Show all questions/answers that exist.");
		System.out.println("[2] - Add a new question/answer.");
		System.out.println("[3] - Update an existing question.");
		System.out.println("[4] - Update an existing answer.");
		System.out.println("[5] - Delete an existing answer.");
		System.out.println("[6] - Create an exam manually.");
		System.out.println("[7] - Create an exam automatically.");
		System.out.println("[8] - Sort all the questions by answer length.");
		System.out.println("[9] - Import binary data from a file.");
		System.out.println("[10] - Save all Questions&Answers into a .txt file.");
		System.out.println("[11] - Create a copy of an existing exam.");
		System.out.println("[12] - Import Pre-made 'questions list'.");
		System.out.println("[13] - Save and exit program. (Saving to a binary file)");
		
		System.out.println("\nEnter your choice: ");
	}

	public void start(Stage primaryStage) throws Exception {
		this.manage = new Manager();
		AbstractMainView aev = new QuestionView(primaryStage);
		MainController ec = new MainController(manage, aev);
		QuestionView menuNew = new QuestionView(ec);
	}
}
