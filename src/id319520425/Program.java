package id319520425;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		
		Manager manage = new Manager();
		
		String quest1 = "First question";
		String quest2 = "Second question";
		String quest3 = "Third question";
		String quest4 = "Fourth question";
		String quest5 = "Fifth question";
		
		String ans1 = "First answer";
		String ans2 = "Second answer";
		String ans3 = "Another answer";
		String ans4 = "And another one";
		String ans5 = "Fifth answer";
		String ans6 = "Sixth answer";
		String ans7 = "Seventh answer";
		String ans8 = "Eighth answer";
		
		AmericanQ ameriTest1 = new AmericanQ(quest1);
		AmericanQ ameriTest2 = new AmericanQ(quest2);
		AmericanQ ameriTest3 = new AmericanQ(quest5);
		
		manage.addAmericanQuestion(ameriTest1);
		manage.addAmericanQuestion(ameriTest2);
		manage.addAmericanQuestion(ameriTest3);
		
		AmericanAnswers ameriAns = new AmericanAnswers(ans1, true);
		AmericanAnswers ameriAns2 = new AmericanAnswers(ans2, true);
		AmericanAnswers ameriAns3 = new AmericanAnswers(ans3, true);
		AmericanAnswers ameriAns4 = new AmericanAnswers(ans4, true);
		AmericanAnswers ameriAns5 = new AmericanAnswers(ans5, true);
		AmericanAnswers ameriAns6 = new AmericanAnswers(ans6, true);
		AmericanAnswers ameriAns7 = new AmericanAnswers(ans7, true);
		AmericanAnswers ameriAns8 = new AmericanAnswers(ans8, true);
		
		ameriTest1.addAnswer(ameriAns);
		ameriTest1.addAnswer(ameriAns2);
		ameriTest1.addAnswer(ameriAns3);
		ameriTest1.addAnswer(ameriAns4);
		ameriTest1.addAnswer(ameriAns5);
		ameriTest1.addAnswer(ameriAns6);
		
		ameriTest2.addAnswer(ameriAns6);
		ameriTest2.addAnswer(ameriAns4);
		
		ameriTest3.addAnswer(ameriAns7);
		ameriTest3.addAnswer(ameriAns8);

		manage.addOpenQuestions(quest3, ans5);
		manage.addOpenQuestions(quest4, ans6);
		
		Scanner input = new Scanner(System.in);
		int choice = 0;

		do {

			System.err.println("-----Exam creator-----");
			System.out.println("-----Select option:-----");
			System.out.println("[1] - Show all questions/answers that exist.");
			System.out.println("[2] - Add a new question/answer.");
			System.out.println("[3] - Update an existing question.");
			System.out.println("[4] - Update an existing answer.");
			System.out.println("[5] - Delete an existing answer.");
			System.out.println("[6] - Create an exam manually.");
			System.out.println("[7] - Create an exam automatically.");

			System.out.println("[11] - Exit.");

			System.out.println("Enter your choice: ");
			choice = input.nextInt();

			switch (choice) {

			case 1:

				System.err.println("-----Show all questions menu-----");
				System.out.println("[1] - Show all questions.");
				System.out.println("[2] - Show only American-type questions (With their correct answers).");
				System.out.println("[3] - Show only Open-type questions.");

				int showChoice = input.nextInt();
				while (showChoice > 0 && showChoice <= 5) {
					if (showChoice == 1) {
						manage.printEverything();				
						break;
					}
					if (showChoice == 2) {
						manage.printAmerican();
						break;
					}
					if (showChoice == 3) {
						manage.printOpen();
						break;
					}
					break;
				}
				break;

			case 2:

				System.err.println("----Add a question-----");
				System.out.println("[1] - Add an american-type question.");
				System.out.println("[2] - Add an open-type question.");
				int addQuestionChoice = input.nextInt();
				input.nextLine();
				while (addQuestionChoice == 1 || addQuestionChoice == 2) {
					if (addQuestionChoice == 1) {
						System.out.println("Please enter your question below: ");
						String ameriQuestion = input.nextLine();
						AmericanQ ameriC = new AmericanQ(ameriQuestion);
						manage.addAmericanQuestion(ameriC);
						System.out.println("How many answers do you want to add? (2 to 6) ");
						int answersAmount = input.nextInt();
						input.nextLine();
						for (int i = 0; i < answersAmount; i++) {
							System.out.println("Please enter answer number " + (i+1) + ": ");
							String answer = input.nextLine();
							System.out.println("Is it a correct answer or not? (true/false): ");
							boolean isTrue = input.nextBoolean();
							ameriC.addAnswer(new AmericanAnswers(answer, isTrue));
							input.nextLine();
						}
						break;
					}
					else if (addQuestionChoice == 2) {
						System.out.println("Please enter your question below: ");
						String question = input.nextLine();
						System.out.println("Please enter an answer: ");
						String answer = input.nextLine();
						manage.addOpenQuestions(question, answer);
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
				int updateChoice = input.nextInt();
				input.nextLine();
				System.out.println("Enter new text below: ");
				String newQuestion = input.nextLine();
				manage.updateQuestion(updateChoice, newQuestion);
				break;

			case 4:
				
				System.out.println("Would you like to update an American answer or Open answer?");
				System.out.println("Type 1 for American, 2 for Open.");
				int updateAnswerChoice = input.nextInt();
				if (updateAnswerChoice == 1) {
					System.out.println("See the full list of questions&answers?? y/Y");
					char newChoice1 = input.next().charAt(0);
					if (newChoice1 == 'y' || newChoice1 == 'Y') {
						manage.printAmerican();
					}
					System.out.println("Please select from which question you want to update an answer: ");
					int questionNum = input.nextInt();
					input.nextLine();
					System.out.println("Please select which answer: ");
					int answerNum = input.nextInt();
					input.nextLine();
					System.out.println("Please enter the new answer: ");
					String newAnswer = input.nextLine();
					manage.updateAnswer(questionNum, answerNum, newAnswer);
				}
				if (updateAnswerChoice == 2) {
					System.out.println("See the full list of questions&answers?? y/Y");
					char newChoice1 = input.next().charAt(0);
					if (newChoice1 == 'y' || newChoice1 == 'Y') {
						manage.printOpen();
					}
					System.out.println("Please select from which question you want to update an answer: ");
					int questionNum = input.nextInt();
					input.nextLine();
					System.out.println("Please enter the new answer: ");
					String newAnswer = input.nextLine();
					manage.updateAnswer(questionNum, questionNum, newAnswer);
				}
				break;

			case 5:

				System.out.println("See the full list of questions&answers? y/Y");
				char newChoice2 = input.next().charAt(0);
				if (newChoice2 == 'y' || newChoice2 == 'Y') {
					System.err.println("Can only delete from American-type questions.");
					manage.printAmerican();
				}
				System.out.println("Please select from which question you want to delete an answer: ");
				int questionNum1 = input.nextInt();
				input.nextLine();
				System.out.println("Please select which answer: ");
				int answerNum1 = input.nextInt();
				input.nextLine();
				manage.deleteAnswer(questionNum1, answerNum1);
				break;
				
			case 6:
				
				System.out.println("See the full list of questions&answers? y/Y");
				char newChoice3 = input.next().charAt(0);
				if (newChoice3 == 'y' || newChoice3 == 'Y') {
					manage.printEverything();
				}
				
				break;
				
				
			case 7:
				
				System.out.println("See the full list of questions&answers? y/Y");
				char newChoice4 = input.next().charAt(0);
				if (newChoice4 == 'y' || newChoice4 == 'Y') {
					manage.printEverything();
				}
				System.out.println("How many questions would you like in your exam? (Max 5)");
				int questAmount = input.nextInt();
				manage.autoCreateExam(questAmount);
				break;
				
			default:
				System.out.println("Invalid option, please choose again.");

			}System.out.println("\nDo you want to go back to the main menu? (y/n)");

			char resume = input.next().charAt(0);if(resume=='y'||resume=='Y')
			{
			}else
			{
				System.out.println("Exiting program...");
				System.exit(0);
			}
		}while(choice!=11);System.out.println("Exiting program...Thank you!");input.close();}

}
