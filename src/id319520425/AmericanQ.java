package id319520425;

public class AmericanQ extends Question {

	public static final int MAX_ANS = 10;
	private AmericanAnswers[] Answers;
	private int answersNum;
	
	public AmericanQ(String question) {
		super(question);
		Answers = new AmericanAnswers[MAX_ANS];
		answersNum = 0;
	}
	
	public String addAnswer(AmericanAnswers answer) {
		if (answersNum == MAX_ANS) {
			return "The list is full";
		}
		for (int i = 0; i <= answersNum; i++) {
			if (Answers[i] != null) {
				if (Answers[i].getAnswer().equals(answer.getAnswer())) {
					return "Answer already exists";
				}
			}
		}
		for (int i = 0; i < Answers.length; i++) {
			if (Answers[i] == null) {
				Answers[i] = answer;
				break;
			}
		}
		answersNum++;
		return "Answer added successfully";
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
		sb.append(getQuestionNumber() + ") American Question: \n" + getQuestion() + "\n");
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

	public int getAnswersNum() {
		return answersNum;
	}
	
	public AmericanAnswers getAnswers(int index) {
		return Answers[index];
	}

}
