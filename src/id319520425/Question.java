package id319520425;

public abstract class Question {
	
	private static int questionNumCounter = 1;
	private int questionNumber;
	protected String question;

	public Question(String question) {
		this.question = question;
		questionNumber = questionNumCounter++;
		
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String printQuestionNumber() {
		return questionNumber + ")" + toString();
	}
	
	@Override
	public String toString() {
		return question + "\n";
	}
}
