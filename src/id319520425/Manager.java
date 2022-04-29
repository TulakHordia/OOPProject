package id319520425;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Manager {

	List<Question> allQuestions = new ArrayList<Question>();
	List<Question> manualExamArray = new ArrayList<Question>();
	List<Question> autoExamArray = new ArrayList<Question>();
	List<File> allExistingExams = new ArrayList<File>();
	LinkedHashSet<File> set = new LinkedHashSet<File>();
	private int size;
	private int examNum;

	Scanner input = new Scanner(System.in);
	
	public Manager() {
		size = 0;
		examNum = 1;
	}

	public void setSize(int size) {
		this.size = size;
		manualExamArray = new ArrayList<Question>(size);
		for (int i = 0; i < size; i++) {
			manualExamArray.add(i, null);
		}
	}
	
	public boolean addOpenQuestions(String question, String answer) {		
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions.get(i);
				if (oQ.getQuestion().equals(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		allQuestions.add(new OpenQ(question, answer));
		System.out.println("Created question #" + (allQuestions.size()));
		return true;
	}

	public boolean addAmericanQuestion(AmericanQ question) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) allQuestions.get(i);
				if (aQ.getQuestion().equals(question.getQuestion())) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		allQuestions.add(question);
		System.out.println("Created question #" + (allQuestions.size()));
		return true;
	}

	private Question getQuestionById(int questionNumber) {
		return allQuestions.get(questionNumber);
	}

	public String updateQuestion(int questionNumber, String question) {
		Question quest = getQuestionById(questionNumber-1);
		if (quest != null) {
			quest.setQuestion(question);
			return "Updated successfully.";
		}
		return "Question does not exist.";
	}

	public String deleteAnswer(int questionNumber, int loc) {
		Question quest = getQuestionById(questionNumber-1);
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
		Question quest = getQuestionById(questionNumber-1);
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
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				System.out.println(allQuestions.get(i));
			}
		}
	}

	public void printQuestionsOnly() {
		System.out.println("-----All Questions-----");
		System.out.println("\nAmerican questions: ");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				if (allQuestions.get(i) instanceof AmericanQ) {
					System.out.println(allQuestions.get(i).getQuestionNumber() + ") " + allQuestions.get(i).getQuestion());
				}
			}
		}
		System.out.println("\nOpen questions: ");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				System.out.println(allQuestions.get(i).getQuestionNumber() + ") " + allQuestions.get(i).getQuestion());
			}
		}
	}

	public void printAmerican() {
		System.out.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				System.out.println(allQuestions.get(i));
			}
		}
	}
	
	public void printAmericanQuestionsOnly() {
		System.out.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				System.out.println((i+1)+") "+allQuestions.get(i).getQuestion());
			}
		}
	}

	public void printOpen() {
		System.out.println("-----Open Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				System.out.println(allQuestions.get(i));
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
	
	public void write(String filename, List<Question> manualExamArray) throws IOException {
		FileWriter fwQ = new FileWriter("Exams/"+"exam_"+examNum+"_"+filename+"_questions.txt");
		FileWriter fwA = new FileWriter("Exams/"+"exam_"+examNum+"_"+filename+"_solution.txt");
		examNum++;

		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) != null) {
				fwQ.write("Question number: " + (i+1) + "\n" + manualExamArray.get(i).getQuestion()+"\n");
			}
		}
		
		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) manualExamArray.get(i);
				fwA.write("\nQuestion: " + aQ.getQuestion() + "\n");
				fwA.write("Answers for question number: " + (i+1) + "\n");
				for (int j = 0; j < aQ.getAnswersNum(); j++) {
					if (aQ.getAnswers(j) != null) {
						fwA.write(aQ.getAnswers(j).getAnswer()+" - "+aQ.getAnswers(j).IsTrue()+"\n");
					}
				}
			}
			if (manualExamArray.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) manualExamArray.get(i);
				fwA.write("\nQuestion: " + oQ.getQuestion() + "\n");
				fwA.write("Answer for question number: " + (i+1) + "\n");
				fwA.write(oQ.getAnswer()+"\n");
			}
		}
		
		System.out.println("Writing successful.");
		fwA.flush();
		fwA.close();
		fwQ.flush();
		fwQ.close();
	}
	
	public void writeAllExternally(String fileName) throws IOException {
		Files.createDirectories(Paths.get("Exams/"));
		boolean check = new File(fileName).exists();
		
		if (!check) {
			FileWriter fwAll = new FileWriter("Exams/"+fileName);
			System.out.println("Created a new " + fileName + " File in Exams folder.");
			
			for (int i = 0; i < allQuestions.size(); i++) {
				fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
			}
			
			fwAll.flush();
			fwAll.close();
		}
		else {
			System.out.println("A 'questions.txt' file already exists.");
			System.out.println("Added all ");
			FileWriter fwAll = new FileWriter("Exams/"+fileName, true);

			for (int i = 0; i < allQuestions.size(); i++) {
				fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
			}
			
			fwAll.flush();
			fwAll.close();
		}
		System.out.println("-----Saved all questions & answers.-----");
	}

	
	public void saveToBinaryFile() throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("Exams/"));
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Exams/questions.ser"));
		for (int i = 0; i < allQuestions.size(); i++) {
			outFile.writeObject(allQuestions.get(i));
		}
		outFile.close();
		System.out.println("Saved to: Exams/questions.ser");
	}
	
	public void readFromBinaryFile(String fileName) throws IOException, FileNotFoundException, ClassNotFoundException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Exams/"+fileName))){
			while (true) {
				allQuestions.add((Question) inFile.readObject());
			}
		} catch (EOFException e) {
			System.out.println("Imported data from " + fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File " + fileName + " does not exist in the directory.");
			e.getMessage();
			e.printStackTrace();
		} 
	}
	
	public void autoImportOnLaunch() throws ClassNotFoundException, IOException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Exams/questions.ser"))){
			while (true) {
				allQuestions.add((Question) inFile.readObject());
			}
		} catch (EOFException e) {
			System.out.println("Automatically Imported data from questions.ser");
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.out.println("Please import questions list. (Option 12)");
		}
		
		for (int i = 0; i < allQuestions.size(); i++) {
			Question quest = null;
			if (i == 0) {
				quest = getQuestionById(i+1);
			}
			else {
				quest = getQuestionById(i);
			}
			if (quest != null) {
				quest.setQuestionNumber(allQuestions);
				break;
			}
		}
	}
	
	public void showAllExistingExamsInDirectory() {
		File[] files = new File("Exams/").listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				allExistingExams.add(files[i]);
			}
		}
		removeDuplicates(allExistingExams);
		Iterator<File> itr = set.iterator();
		int fNum = 1;
		while (itr.hasNext()) {
			System.out.println(fNum+") "+itr.next());
			fNum++;
		}
	}
	
	public LinkedHashSet<File> removeDuplicates(List<File> allExistingExams) {
		for (int i = 0; i < allExistingExams.size(); i++) {
			set.add(allExistingExams.get(i));
		}
		return set;
	}
	
	public void copyExistingExam(int copyChoice) throws IOException {
		BufferedReader inputS = new BufferedReader(new FileReader(allExistingExams.get(copyChoice-1)));
		FileWriter output = new FileWriter("Exams/"+"copy_of_"+allExistingExams.get(copyChoice-1).getName());
		String count;
		while ( (count = inputS.readLine()) != null ) {
			output.write(count+"\n");
		}
		System.out.println("Created a copy of: " + allExistingExams.get(copyChoice-1).getName());
		output.flush();
		output.close();
		inputS.close();
	}

	public void sortByAnswerLength(List<Question> array) {
		QuestionComparator qC = new QuestionComparator();
		array.sort(qC);
	}

	public void sortAndPrintAutoExamArray() throws IOException {
		sortByAnswerLength(autoExamArray);
		write(getDateTime(), autoExamArray);
		
		for (int i = 0; i < autoExamArray.size(); i++) {
			System.out.println(autoExamArray.get(i));
		}
		System.out.println("Exam created on the: " + getDateTime() + ", contains: " + autoExamArray.size() + " questions.");
		autoExamArray.clear();
	}

	public void sortAndPrintManualExamArray() throws IOException {
		sortByAnswerLength(manualExamArray);
		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) != null) {
				System.out.println(manualExamArray.get(i));
			}
		}
		write(getDateTime(), manualExamArray);
		int counter = 0;
		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) != null) {
				counter++;
			}
		}
		System.out.println("Exam created on the: " + getDateTime() + ", contains: " + counter + " questions.");
		manualExamArray.clear();
	}

	public boolean checkQuestionIsInArray(Question quest) {
		if (quest == null) {
			return false;
		}
		for (int i = 0; i < autoExamArray.size(); i++) {
			if (autoExamArray.get(i) != null) {
				if (autoExamArray.contains(quest)) {
					return true;
				}
			}
		}
		return false;
	}

	public Question generateNewQuestion(int amount) {
		Random rand = new Random();
		while (autoExamArray.size() != amount) {
			Question quest = getQuestionById(rand.nextInt(amount));
			while (checkQuestionIsInArray(quest)) {
				quest = getQuestionById(rand.nextInt(amount));
			}
			return quest;
		}
		return null;
	}

	public int checkAllQuestionsLength() {
		int counter = 0;
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				counter++;
			}
		}
		return counter;
	}

	public void addBuiltInAnswers(AmericanQ answers) {
		int trueCounter = 0;
		int falseCounter = 0;
		for (int j = 0; j < answers.getAnswersNum(); j++) {
			if (answers.getAnswers(j).IsTrue()) {
				trueCounter++;
			} else {
				falseCounter++;
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

	public void autoCreateExam(int amount) throws IOException {
		for (int i = 0; i < amount; i++) {
			Question quest = generateNewQuestion(amount);
			if (quest instanceof OpenQ) {
				OpenQ openQ = (OpenQ) quest;
				autoExamArray.add(openQ);
			}
			if (quest instanceof AmericanQ) {
				AmericanQ ameriQ = (AmericanQ) quest;
				autoExamArray.add(ameriQ);
				addBuiltInAnswers(ameriQ);
			}
		}
		sortAndPrintAutoExamArray();
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
			for (int i = 0; i < aQ.getAnswersNum(); i++) {
				if (!aQ.getAnswers(i).getAnswer().isEmpty()) {
					System.out.println("[" + (i + 1) + "] " + aQ.getAnswers(i));
				}
				else {
					System.out.println("No answers available.");
				}
			}
		}
	}

	public boolean addAmericanQuestionToManualExam(AmericanQ question) {
		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) != null) {
				if (manualExamArray.contains(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		if (manualExamArray.add(question)) {
			return true;
		}
		return false;
	}

	public boolean addOpenQuestionToManualExam(String question, String answer) {
		for (int i = 0; i < size; i++) {
			if (manualExamArray.get(i) != null) {
				if (manualExamArray.get(i).getQuestion().equals(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) == null || manualExamArray.get(i).getQuestion().isEmpty()) {
				manualExamArray.add(new OpenQ(question, answer));
//				manualExamArray.set(i, new OpenQ(question, answer));
				System.out.println("Added question #" + (i + 1));
				return true;
			}
		}
		return false;
	}

	public boolean addQuestionToManualExam(int questionNum, List<Integer> answersArray) {
		Question question = getQuestionById(questionNum);
		if (question instanceof OpenQ) {
			OpenQ open = (OpenQ) question;
			addOpenQuestionToManualExam(open.getQuestion(), open.getAnswer());
			return true;
		}
		if (question instanceof AmericanQ) {
			AmericanQ dQ = (AmericanQ) question;
			AmericanQ aQ = new AmericanQ(dQ.getQuestion());
			
			for (int i = 0; i < answersArray.size(); i++) {
				System.out.println(answersArray.get(i));
				aQ.addAnswer(new AmericanAnswers(dQ.getAnswers(answersArray.get(i)-1)));
			}
			addAmericanQuestionToManualExam(aQ);
			return true;
		}
		return false;
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
	
	private final static String getDateTime()  
	{  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");  
	    return df.format(new Date());  
	}
	

	public void sortAllQuestions() {
		QuestionComparator qC = new QuestionComparator();
		allQuestions.sort(qC);
		System.out.println("Sorted all questions array.");
	}  
}
