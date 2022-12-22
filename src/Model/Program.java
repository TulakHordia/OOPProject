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
import javax.swing.*;
import java.sql.*;

public class Program extends Application implements ProgramMethods  {
	
	private Scanner input;
	private Manager manage;
	private DatabaseIntegration DBIntegration;
	private int choice;

	
	@Override
	public void mainMenu() throws IOException, ClassNotFoundException, SQLException {
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
				
				questionsList();
				break;
				
			case 13:
				
				manage.saveToBinaryFileAutomatically();
				System.out.println("Saving and exiting...");
				System.exit(0);
				break;

				case 14:
					System.out.println("Enter 1 to sort with duplicates.\n");
					System.out.println("Enter 2 to copy the previous collection to a new one.\n");
					int sortingChoice = input.nextInt();
					if (sortingChoice == 1) {
						manage.copyArrayToANewCollectionAndSortWithDupes();
						break;
					}
					else if (sortingChoice == 2) {
						manage.copyArrayToANewCollectionAndSortNoDupes();
						break;
					}
					else {
						System.out.println("Invalid choice.");
						System.exit(0);
					}
				case 15:
					System.out.println("Please enter your new Question below:\n");
					String newStr = input.nextLine();
					manage.addNewStringToHashSet(newStr);
					break;

				case 16:
					manage.printEverything();
					System.out.println("\n\nPlease select the question number you want to delete:\n");
					int questNum = input.nextInt();
					manage.deleteQuestion(questNum);
					break;

			default:
				System.out.println("Invalid option, please choose again.");
			}
			System.out.println("\nDo you want to go back to the main menu? (y/n)");

			char resume = input.next().charAt(0);
			if(resume=='y'||resume=='Y') {
			}else
			{
				manage.saveToBinaryFileAutomatically();
				System.out.println("Exiting program...");
				System.exit(0);
			}
		}
		while(choice != 13);
		manage.saveToBinaryFileAutomatically();
		System.out.println("Exiting program...Thank you!");
		input.close();
		}

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		Program p = new Program();
		p.managingMethod();

		if (!p.importAndSaveQuestionsList()) {
			p.autoImportQuestions();
		}

		p.mainMenu();
		launch(args);
		}
	
	@Override
	public boolean importAndSaveQuestionsList() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
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
	public void managingMethod() throws SQLException, ClassNotFoundException {
		this.input = new Scanner(System.in);
		this.manage = new Manager();
		this.choice = 0;
		DBIntegration = new DatabaseIntegration();
		DBIntegration.connectToSql();
	}
	

	@Override
	public void autoImportQuestions() throws ClassNotFoundException, IOException, SQLException {
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
		System.out.println("[14] - Copy and sort allQuestions list to a collection.");
		System.out.println("[15] - Add a new Question to the 'HashSet'.");
		System.out.println("[16] - Delete a question.");

		System.out.println("\nEnter your choice: ");
	}
	
	public void questionsList() throws SQLException {
		//General questions
		String quest1 = "First question";
		String quest2 = "Second question";
		String quest3 = "Third question";
		String quest4 = "Fourth question";
		String quest5 = "Fifth question";
		String quest6 = "Sixth question";
		String quest7 = "Seventh question";
		String quest8 = "Eighth question";
		
		//Just answers
		String ans1 = "First answer";
		String ans2 = "Second answer";
		String ans3 = "Another answer";
		String ans4 = "And another one";
		String ans5 = "Fifth answer";
		String ans6 = "Sixth answer";
		String ans7 = "Seventh answer";
		String ans8 = "Eighth answer";
		
		//False answers
		String ansF1 = "False answer #1";
		String ansF2 = "False answer #2";
		String ansF3 = "False answer #3";
		String ansF4 = "False answer #4";
		String ansF5 = "False answer #5";
		
		//Open answers
		String openAns = "First open answer";
		String openAns2 = "Second open answer has many characters aswell";
		String openAns3 = "Third open answer is a very very very very very very very long one";
		String openAns4 = "Fourth open answer is pretty short";
		
		//Create new american question objects
		AmericanQ ameriTest1 = new AmericanQ(quest1);
		AmericanQ ameriTest2 = new AmericanQ(quest2);
		AmericanQ ameriTest3 = new AmericanQ(quest3);
		AmericanQ ameriTest4 = new AmericanQ(quest4);
		
		//Add american questions
		manage.addAmericanQuestion(ameriTest1);
		manage.addAmericanQuestion(ameriTest2);
		manage.addAmericanQuestion(ameriTest3);
		manage.addAmericanQuestion(ameriTest4);
		
		//American answers
		AmericanAnswers ameriAns = new AmericanAnswers(ans1, true);
		AmericanAnswers ameriAns2 = new AmericanAnswers(ans2, true);
		AmericanAnswers ameriAns3 = new AmericanAnswers(ans3, true);
		AmericanAnswers ameriAns4 = new AmericanAnswers(ans4, true);
		AmericanAnswers ameriAns5 = new AmericanAnswers(ans5, true);
		AmericanAnswers ameriAns6 = new AmericanAnswers(ans6, true);
		AmericanAnswers ameriAns7 = new AmericanAnswers(ans7, true);
		AmericanAnswers ameriAns8 = new AmericanAnswers(ans8, true);
		AmericanAnswers ameriAnsF1 = new AmericanAnswers(ansF1, false);
		AmericanAnswers ameriAnsF2 = new AmericanAnswers(ansF2, false);
		AmericanAnswers ameriAnsF3 = new AmericanAnswers(ansF3, false);
		AmericanAnswers ameriAnsF4 = new AmericanAnswers(ansF4, false);
		AmericanAnswers ameriAnsF5 = new AmericanAnswers(ansF5, false);
		
		//More than one is correct:
		ameriTest1.addAnswer(ameriAns);
		ameriTest1.addAnswer(ameriAns2);
		ameriTest1.addAnswer(ameriAns3);
		ameriTest1.addAnswer(ameriAns4);
		ameriTest1.addAnswer(ameriAnsF1);
		ameriTest1.addAnswer(ameriAnsF4);
		manage.addBuiltInAnswers(ameriTest1);
		
		//All false
		ameriTest2.addAnswer(ameriAnsF1);
		ameriTest2.addAnswer(ameriAnsF2);
		ameriTest2.addAnswer(ameriAnsF3);
		ameriTest2.addAnswer(ameriAnsF4);
		ameriTest2.addAnswer(ameriAnsF5);
		manage.addBuiltInAnswers(ameriTest2);

		
		//Only one question is true
		ameriTest3.addAnswer(ameriAns5);
		ameriTest3.addAnswer(ameriAnsF1);
		ameriTest3.addAnswer(ameriAnsF2);
		ameriTest3.addAnswer(ameriAnsF3);
		ameriTest3.addAnswer(ameriAnsF4);
		manage.addBuiltInAnswers(ameriTest3);

		
		//more than one is correct #2
		ameriTest4.addAnswer(ameriAns6);
		ameriTest4.addAnswer(ameriAns7);
		ameriTest4.addAnswer(ameriAns8);
		ameriTest4.addAnswer(ameriAnsF3);
		ameriTest4.addAnswer(ameriAnsF4);
		manage.addBuiltInAnswers(ameriTest4);

		
		//Open questions + Answers
		manage.addOpenQuestions(quest5, openAns);
		manage.addOpenQuestions(quest6, openAns2);
		manage.addOpenQuestions(quest7, openAns3);
		manage.addOpenQuestions(quest8, openAns4);
	}

	@SuppressWarnings("unused")
	public void start(Stage primaryStage) throws Exception {
		this.manage = new Manager();
		AbstractMainView aev = new QuestionView(primaryStage);
		MainController ec = new MainController(manage, aev);
		QuestionView menuNew = new QuestionView(ec);
	}
}
