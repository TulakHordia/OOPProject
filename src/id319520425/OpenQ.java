package id319520425;

public class OpenQ extends Question {
	
	private String answer;
	
	public OpenQ(String question, String answer) {
		super(question);
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return "[" + getQuestionNumber() + "] Open Questions: " + getQuestion() + " The answer: " + getAnswer();
	}

	public String setAnswer() {
		return answer;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
	
	public void setCorrectAnswer(String newAnswer) {
		this.answer = newAnswer;
	}
}
