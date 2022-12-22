package Model;

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
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import Listeners.MainEventsListener;
import javafx.beans.property.SimpleStringProperty;

public class Manager {

	List<Question> allQuestions;
	List<Question> manualExamArray;
	List<Question> autoExamArray;
	List<File> allExistingExams;
	List<File> examsNoDuplicatesList;

	//Collections.
	HashMap<String, Integer> hashMapSortedList;
	Map<String, Integer> treeMap;
	HashSet<String> hashSetList = new HashSet<String>();;

	private Vector<MainEventsListener> mainListener = new Vector<MainEventsListener>();

	@SuppressWarnings("unused")
	private int size;
	private int examNum;
	private Iterator mainIterator;
	private SimpleStringProperty name;
	private DatabaseIntegration DBIntegration;

	Scanner input = new Scanner(System.in);

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void registerListener(MainEventsListener listener) {
		mainListener.add(listener);
	}

	private void fireAddOpenQuestion(OpenQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedOpenQuestionToModelEventObject(question);
		}
	}

	private void fireAddAmericanQuestion(AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedAmericanQuestionToModelEventObject(question);
		}
	}

	private void fireImportFromBinaryFile(List<Question> questions) {
		for (MainEventsListener l : mainListener) {
			l.importedFromBinaryFile(questions);
		}
	}

	private void fireSavedAllQuestionsToFile(String fileName, int amountOfQuestions) {
		for (MainEventsListener l : mainListener) {
			l.savedAllQuestionsToFile(fileName, amountOfQuestions);
		}
	}

	private void fireDeletedAmericanAnswer(AmericanAnswers aN, AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.deletedAmericanAnswer(aN, question);
		}
	}

	private void fireCreateAutoExam(int amount, List<Question> exam) {
		for (MainEventsListener l : mainListener) {
			l.createdAutoExam(amount, exam);
		}
	}

	private void fireAddAmericanAnswerToQuestion(AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedAmericanAnswerToQuestion(question);
		}
	}

	private void fireCopiedAnExam(File fileName) {
		for (MainEventsListener l : mainListener) {
			l.copiedAnExistingExam(fileName);
		}
	}

	private void fireSavedToBinaryOnExit() {
		for (MainEventsListener l : mainListener) {
			l.savedToBinaryFileOnExit();
		}
	}

	public void addAmericanAnswerToQuestion(AmericanQ question, String answer, boolean isTrue) {
		AmericanAnswers aN = new AmericanAnswers(answer, isTrue);
		System.out.println(question.addAnswer(aN));
		fireAddAmericanAnswerToQuestion(question);
	}

	public Manager() throws SQLException, ClassNotFoundException {
		allQuestions = new ArrayList<Question>();
		manualExamArray = new ArrayList<Question>();
		autoExamArray = new ArrayList<Question>();
		allExistingExams = new ArrayList<File>();
		examsNoDuplicatesList = new ArrayList<File>();
		DBIntegration = new DatabaseIntegration();

		//Sorting.
		hashMapSortedList = new HashMap<String, Integer>();

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

	public boolean addOpenQuestions(String question, String answer) throws SQLException {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions.get(i);
				if (oQ.getQuestion().equals(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		OpenQ theQuestion = new OpenQ(question, answer);
		allQuestions.add(theQuestion);
		fireAddOpenQuestion(theQuestion);
		if (DBIntegration.checkIfConnectionIsUp()) {
			DBIntegration.addOpenQuestionToDatabase(theQuestion);
		}
		System.out.println("Created question #" + (allQuestions.size()));
		return true;
	}

	public boolean addAmericanQuestion(AmericanQ question) throws SQLException {
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
		fireAddAmericanQuestion(question);
		if (DBIntegration.checkIfConnectionIsUp()) {
			DBIntegration.addAmericanQuestionToDatabase(question);
			System.out.println("Question added maybe?");
		}
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

	public void deleteQuestion(int questionNumber) {
		allQuestions.remove(questionNumber-1);
	}

	public void deleteAmericanAnswer(AmericanAnswers aN, AmericanQ question) {
		if (question.deleteAmericanAnswer(aN)) {
			fireDeletedAmericanAnswer(aN, question);
			System.out.println("Deleted answer " + aN.getAnswer());
		}
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
						fwA.write(aQ.getAnswers(j).getAnswer()+" - "+aQ.getAnswers(j).getIsTrue()+"\n");
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
			if (allQuestions.size() == 0) {
				JOptionPane.showMessageDialog(null, "No questions exist, cannot create an empty file");
			}
			else {
				FileWriter fwAll = new FileWriter("Exams/"+fileName+".txt");
				System.out.println("Created a new " + fileName +".txt"+ " File in Exams folder.");
				for (int i = 0; i < allQuestions.size(); i++) {
					fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
				}
				fireSavedAllQuestionsToFile(fileName, getAllQuestionSize());
				fwAll.flush();
				fwAll.close();
			}
		}
		else {
			if (allQuestions.size() == 0) {
				JOptionPane.showMessageDialog(null, "No questions exist, cannot create an empty file");
			}
			else {
				System.out.println("A 'questions' file already exists.");
				System.out.println("Added all ");
				FileWriter fwAll = new FileWriter("Exams/"+fileName+".txt", true);
				for (int i = 0; i < allQuestions.size(); i++) {
					fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
				}
				fireSavedAllQuestionsToFile(fileName, getAllQuestionSize());
				fwAll.flush();
				fwAll.close();
			}
		}
	}

	public void saveToBinaryFile(String fileName) throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("Data/"));
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Data/"+fileName+".ser"));
		for (int i = 0; i < allQuestions.size(); i++) {
			outFile.writeObject(allQuestions.get(i));
		}
		outFile.close();
		JOptionPane.showMessageDialog(null, "Saved to: Data/"+fileName+".ser");
		System.out.println("Saved to: Data/"+fileName+".ser");

	}

	public void saveToBinaryFileAutomatically() throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("Data/"));
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Data/questions.ser"));
		if (allQuestions.size() > 0) {
			for (int i = 0; i < allQuestions.size(); i++) {
				outFile.writeObject(allQuestions.get(i));
			}
			outFile.close();
			fireSavedToBinaryOnExit();
			System.out.println("Saved to: Data/questions.ser");
		}
		else {
			JOptionPane.showMessageDialog(null, "Did not save as there were no questions in the data");
		}
	}

	public void readFromBinaryFile(String fileName) throws IOException, FileNotFoundException, ClassNotFoundException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Data/"+fileName))){
			while (true) {
				allQuestions.add((Question) inFile.readObject());
			}
		} catch (EOFException e) {
			fireImportFromBinaryFile(allQuestions);
			JOptionPane.showMessageDialog(null, "Imported from: "+fileName);
			System.out.println("Imported data from " + fileName);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			int input = JOptionPane.showConfirmDialog(null, "File not found"+"\nWould you like to type again?"+"\nMake sure name ends with .ser");
			if (input == 0) {
				String m = JOptionPane.showInputDialog("Enter filename:(ends with .ser)");
				readFromBinaryFile(m);
			}
			else {
				JOptionPane.showMessageDialog(null, "No file imported");
			}
			System.out.println("File " + fileName + " does not exist in the directory.");
		}

	}

	public void autoImportOnLaunch() throws ClassNotFoundException, IOException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Data/questions.ser"))){
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

	public List<File> showAllExistingExamsInDirectory() {
		File[] files = new File("Exams/").listFiles();
		name = new SimpleStringProperty();
		for (File l : files) {
			if (l.isFile()) {
				allExistingExams.add(l);
				name.set(l.getName());
			}
		}
		removeDuplicates();
		return examsNoDuplicatesList;
	}

	public void removeDuplicates() {
		for (File l : allExistingExams) {
			if (!examsNoDuplicatesList.contains(l)) {
				examsNoDuplicatesList.add(l);
			}
		}
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

	public void copyExistingExamByFileName(File fileName) throws IOException {
		for (int i = 0; i < examsNoDuplicatesList.size(); i++) {
			if (examsNoDuplicatesList.get(i).getName().equals(fileName.getName())) {
				BufferedReader inputS = new BufferedReader(new FileReader(fileName));
				FileWriter output = new FileWriter("Exams/"+"copy_of_"+examsNoDuplicatesList.get(i).getName());
				String count;
				while ( (count = inputS.readLine()) != null ) {
					output.write(count+"\n");
				}
				JOptionPane.showMessageDialog(null, "Created a copy of: " + examsNoDuplicatesList.get(i).getName());
				System.out.println("Created a copy of: " + examsNoDuplicatesList.get(i).getName());
				inputS.close();

				output.flush();
				output.close();
			}
		}
	}

	public void sortByAnswerLength(List<Question> array) {
		QuestionComparator qC = new QuestionComparator();
		array.sort(qC);
	}

	public void sortAndPrintAutoExamArray() throws IOException {
		sortByAnswerLength(autoExamArray);
		write(getDateTime(), autoExamArray);
		fireCreateAutoExam(autoExamArray.size(), autoExamArray);

		for (int i = 0; i < autoExamArray.size(); i++) {
			System.out.println(autoExamArray.get(i));
		}
		System.out.println("Exam created on the: " + getDateTime() + ", contains: " + autoExamArray.size() + " questions.");
		autoExamArray.clear();
	}

	public void sortAndPrintManualExamArray() throws IOException {
		if (manualExamArray.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No questions added to the exam");
		}
		else {
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
			JOptionPane.showMessageDialog(null, "Exam created on the: " + getDateTime() + ", contains: " + counter + " questions.");
			System.out.println("Exam created on the: " + getDateTime() + ", contains: " + counter + " questions.");
			manualExamArray.clear();
		}
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
			if (answers.getAnswers(j).getIsTrue()) {
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

	public void autoCreateExam(int amount) throws IOException, NumberFormatException {
		try {
			if (amount > 0 && amount <= allQuestions.size()) {
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
			else {
				String m = JOptionPane.showInputDialog("Only "+allQuestions.size()+" Questions available.");
				int newAmount = Integer.parseInt(m);
				autoCreateExam(newAmount);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter a numerical value!");
		}

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
		if (!manualExamArray.contains(question)) {
			manualExamArray.add(question);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addOpenQuestionToManualExam(OpenQ oQ) {
		if (!(manualExamArray.contains(oQ))) {
			manualExamArray.add(oQ);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addQuestionToManualExam(int questionNum, List<Integer> answersArray) {
		Question question = getQuestionById(questionNum);
		if (question instanceof OpenQ) {
			OpenQ open = (OpenQ) question;
			addOpenQuestionToManualExam(open);
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

	private static String getDateTime()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		return df.format(new Date());
	}

	public void sortAllQuestions() {
		QuestionComparator qC = new QuestionComparator();
		allQuestions.sort(qC);
		System.out.println("Sorted all questions array.");
	}

	public OpenQ getOpenQuestions() {
		for (Question l : allQuestions) {
			if (l instanceof OpenQ) {
				OpenQ oQ = (OpenQ) l;
				return oQ;
			}
		}
		return null;
	}

	public int getAllQuestionSize() {
		return allQuestions.size();
	}

	public Question getAllQuestions(int index) {
		return allQuestions.get(index);
	}

	public void questionsList() throws SQLException {
		//General questions
		String quest1 = "First question";
		String quest2 = "Second question";
		String quest3 = "ThirdWithExtraSteps question";
		String quest4 = "Fourth question";
		String quest5 = "FifthWith question";
		String quest6 = "SixthWithExtra question";
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
		addAmericanQuestion(ameriTest1);
		addAmericanQuestion(ameriTest2);
		addAmericanQuestion(ameriTest3);
		addAmericanQuestion(ameriTest4);

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
		addBuiltInAnswers(ameriTest1);

		//All false
		ameriTest2.addAnswer(ameriAnsF1);
		ameriTest2.addAnswer(ameriAnsF2);
		ameriTest2.addAnswer(ameriAnsF3);
		ameriTest2.addAnswer(ameriAnsF4);
		ameriTest2.addAnswer(ameriAnsF5);
		addBuiltInAnswers(ameriTest2);

		//Only one question is true
		ameriTest3.addAnswer(ameriAns5);
		ameriTest3.addAnswer(ameriAnsF1);
		ameriTest3.addAnswer(ameriAnsF2);
		//ameriTest3.addAnswer(ameriAnsF3);
		//ameriTest3.addAnswer(ameriAnsF4);
		addBuiltInAnswers(ameriTest3);

		//more than one is correct #2
		ameriTest4.addAnswer(ameriAns6);
		ameriTest4.addAnswer(ameriAns7);
		//ameriTest4.addAnswer(ameriAns8);
		//ameriTest4.addAnswer(ameriAnsF3);
		//ameriTest4.addAnswer(ameriAnsF4);
		addBuiltInAnswers(ameriTest4);

		//Open questions + Answers
		addOpenQuestions(quest5, openAns);
		addOpenQuestions(quest6, openAns2);
		addOpenQuestions(quest7, openAns3);
		addOpenQuestions(quest8, openAns4);
	}

	public void copyArrayToANewCollectionAndSortWithDupes() throws SQLException {
		questionsList();
		//Places allQuestions values into hashMapSortedList
		for (int i = 0; i < getAllQuestionSize(); i++) {
			hashMapSortedList.put(allQuestions.get(i).getQuestion(), i);
		}
		//Creates a TreeMap that sorts by the question length.
		treeMap = new TreeMap<String, Integer>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() > o2.length()) {
					return -1;
				} else if (o1.length() < o2.length()) {
					return 1;
				} else if (o1.length() == o2.length()){
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		treeMap.putAll(hashMapSortedList);
		printTreeMapCollection((TreeMap<String, Integer>) treeMap);
	}

	public void printTreeMapCollection(TreeMap<String, Integer> treeMap) {
		System.out.println("This is the sorted TreeMap collection: \n");
		Iterator<Entry<String, Integer>> entryIt = treeMap.entrySet().iterator();
		while (entryIt.hasNext()) {
			System.out.println(entryIt.next());
		}
	}

	public HashSet<String> copyToHashSet(HashMap<String, Integer> hashMapSortedList, HashSet<String> hashSet) {
		for (Map.Entry<String, Integer> entry : hashMapSortedList.entrySet()) {
			String key = entry.getKey();
			hashSet.add(key);
		}
		return hashSet;
	}

	public void addNewStringToHashSet(String theStr) {
		mainIterator = hashSetList.iterator();
		StringComparator sComp = new StringComparator();
		while (mainIterator.hasNext()) {
			int boolVal = sComp.compare((String) mainIterator.next(), theStr);
			if (boolVal == 0) {
				System.out.println("Question already exists in the 'HashSet'.");
				break;
			}
		}
		hashSetList.add(theStr);
	}

	public void copyArrayToANewCollectionAndSortNoDupes() throws SQLException {
		questionsList();
		hashSetList = copyToHashSet(hashMapSortedList, hashSetList);
		printHashSet();
	}

	public void printHashSet() {
		mainIterator = hashSetList.iterator();
		while (mainIterator.hasNext()) {
			System.out.println(mainIterator.next());
		}
	}
}

