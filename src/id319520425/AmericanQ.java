package id319520425;

import java.io.Serializable;
import java.util.ArrayList;

public class AmericanQ extends Question implements Serializable {

	public static final int MAX_ANS = 10;
	ArrayList<AmericanAnswers> Answers = new ArrayList<AmericanAnswers>();
	private int answersNum;
	
	public AmericanQ(String question) {
		super(question);
		answersNum = 0;
	}
	
	public String addAnswer(AmericanAnswers answer) {		
		for (int i = 0; i <= answersNum; i++) {
			if (Answers.contains(answer)) { // Need to change it to a Set method
				return "Answer already exists";
			}
		}
		answersNum++;
		if (answersNum >= Answers.size()) {
			Answers.add(answersNum-1, answer);
		}
		return "Answer added successfully";
	}
	
	public void deleteAnswer(int index) {
		Answers.set((index-1), null);
		answersNum--;
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
				sb.append(" [Correct or not]: " + Answers.get(i).IsTrue() + "\n");
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
