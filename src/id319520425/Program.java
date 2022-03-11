package id319520425;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		
		Manager test = new Manager();
		Scanner input = new Scanner(System.in);
        int choice = 0;
        
        do {

            System.out.println("-----Exam creator-----");
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

                	System.out.println("-----Show all questions menu-----");
                	System.out.println("[1] - Show all questions.");
                	System.out.println("[2] - Show only American-type questions (With their correct answers).");
                	System.out.println("[3] - Show only Open-type questions.");
                	System.out.println("[4] - Print all American possible answers.");
                	System.out.println("[5] - Print all questions.");
                	
                	int showChoice = input.nextInt();
                	while (showChoice > 0 && showChoice < 4) {
                		if (showChoice == 1) {
                			
                		}
                		else if (showChoice == 2) {
                			
                			
                			break;
                		}
                		else {

                		}
                		break;
                	}
        
                    break;
                    
                case 2:
                	
                	System.out.println("----Add a question-----");
                	System.out.println("[1] - Add an american-type question.");
                	System.out.println("[2] - Add an open-type question.");
                	int addQuestionChoice = input.nextInt();
                	input.nextLine();
                	while (addQuestionChoice == 1 || addQuestionChoice == 2) {
                		
                    	if (addQuestionChoice == 1) {
                    		System.out.println("Please enter your question below: ");
                    		String ameriQuestion = input.nextLine();
                    		for (int i = 0; i < 10; i++) {
                        		System.out.println("Please enter an answer: ");
                        		String answer = input.nextLine();
                        		System.out.println("Is it a correct answer or not? (true/false): ");
                        		boolean isTrue = input.nextBoolean();
                        		
                        		System.out.println("\nDo you want to add another answer? (y/n)");
                                char resume = input.next().charAt(0);
                                if (resume != 'y' && resume != 'Y') {
                                	break;
                                }
							}

                    		
                    		break;
                    	}
                    	else if (addQuestionChoice == 2) {
                    		System.out.println("Please enter your question below: ");
                    		String question = input.nextLine();
                    		System.out.println("Please enter an answer: ");
                    		String answer = input.nextLine();
                    		
                    		break;
                    	}
                	}

                    break;
                    
                case 3:
                	
                	System.out.println("Please choose the question you want to update: ");
                	int updateChoice = input.nextInt();
                	input.nextLine();
                	System.out.println("Enter new text below: ");
                	String newQuestion = input.nextLine();
                	//test.american[updateChoice] = test.addAmericanQuestion(newQuestion, test.american[updateChoice].getAnswer());
                	break;
                    
                case 4:


                default:
                    System.out.println("Invalid option, please choose again.");

            }
            System.out.println("\nDo you want to go back to the main menu? (y/n)");
            char resume = input.next().charAt(0);
            if (resume == 'y' || resume == 'Y') {
            } else {
                System.out.println("Exiting program...");
                System.exit(0);
            }
        } while (choice != 11);
        System.out.println("Exiting program...Thank you!");
        input.close();
    }

}
