package id319520425;

import java.util.Arrays;
import java.util.Objects;

public class AmericanQ extends Question {

	public enum eAddAns {AlreadyExists, ListFull, AddedSuccessfully};
	public static final int MAX_ANS = 10;
	private AmericanAnswers[] Answers;
	private int answersNum;
	
	public AmericanQ(String question) {
		super(question);
		Answers = new AmericanAnswers[MAX_ANS];
		answersNum = 0;
	}
	
	public eAddAns addAnswer(AmericanAnswers answer) {
		if (answersNum == MAX_ANS) {
			return eAddAns.ListFull;
		}
		for (int i = 0; i <= answersNum; i++) {
			if (Answers[i] != null) {
				if (Answers[i].getAnswer().equals(answer.getAnswer())) {
					return eAddAns.AlreadyExists;
				}
			}
		}
		for (int i = 0; i < Answers.length; i++) {
			if (Answers[i] == null) {
				Answers[i] = answer;
				break;
			}
		}
		//System.out.println(Answers[answersNum].toString());
		//System.out.println("Added aye");
//		System.out.println("ADDED ANSWERED, LOOK: q:" + this.question + "; a:" + Arrays.toString(Answers) + Answers[answersNum].IsTrue());
		answersNum++;
		return eAddAns.AddedSuccessfully;
	}
	
	public void deleteAnswer(int index) {
		Answers[index-1] = null;
		answersNum--;
	}
	
	public void updateAnswer(int index, String answer) {
		Answers[index-1].setAnswer(answer);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[" + getQuestionNumber() + "] American Question: \n" + getQuestion() + "\n");
		for (int i = 0; i < Answers.length; i++) {
			if (Answers[i] != null) {
				sb.append(" Answer num: [" + (i+1) + "] " + Answers[i].toString());
				sb.append(" [Correct or not]: " + Answers[i].IsTrue() + "\n");
			}
		}
		return sb.toString();
	}

	public String getQuestion() {
		return question;
	}
	
//	public AmericanAnswers getCorrectAnswer() {
//		for (int i = 0; i < Answers.length; i++) {
//			if (Answers[i].IsTrue()) {
//				return Answers[i];
//			}
//			else {
//				return null;
//			}
//		}
//		throw new RuntimeException("No correct answers.");
//	}

	public int getAnswersNum() {
		return answersNum;
	}
	
	public AmericanAnswers getAnswers(int index) {
		return Answers[index];
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		return !(getClass() != obj.getClass());
	}

}
