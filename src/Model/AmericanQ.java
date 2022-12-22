package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class AmericanQ extends Question implements Serializable {

	NewSet<AmericanAnswers> Answers = new NewSet<AmericanAnswers>();
	private int answersNum;
	
	public AmericanQ(String question) {
		super(question);
		answersNum = 0;
	}
	
	public String addAnswer(AmericanAnswers answer) {		
		for (int i = 0; i <= answersNum; i++) {
			if (Answers.contains(answer)) {
				return "Answer already exists";
			}
		}
		if (answersNum == 9) {
			return "Reached answers limit";
		}
		answersNum++;
		if (answersNum >= Answers.size()) {
			Answers.add(answer);
		}
		return "Answer added successfully";
	}
	
	public void deleteAnswer(int index) {
		if (Answers.isEmpty()) {
			System.out.println("No answers currently in database.");
		}
		else {
			Answers.remove(Answers.get(index-1));
			answersNum--;
		}
	}
	
	public boolean deleteAmericanAnswer(AmericanAnswers aN) {
		if (Answers.remove(aN)) {
			answersNum--;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateAnswer(int index, String answer) {
		Answers.get(index-1).setAnswer(answer);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getQuestionNumber() + ") American Question: \n" + getQuestion() + "\n");
		for (int i = 0; i < Answers.size(); i++) {
			if (Answers.get(i) != null) {
				sb.append(" Answer num: [" + (i+1) + "] " + Answers.get(i).toString());
				sb.append(" [Correct or not]: " + Answers.get(i).getIsTrue() + "\n");
			}
		}
		return sb.toString();
	}

	public String getQuestion() {
		return question;
	}

	public int getAnswersNum() {
		return answersNum;
	}
	
	public AmericanAnswers getAnswers(int index) {
		return Answers.get(index);
	}
}
