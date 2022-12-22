package Model;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Question> {

	@Override
	public int compare(Question o1, Question o2) {
		int Length1 = 0;
		int Length2 = 0;
		
		if (o1 != null) {
			if (o1 instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) o1;
				for (int i = 0; i < aQ.getAnswersNum(); i++) {
					if (aQ.getAnswers(i).getAnswer() != null) {
						Length1 += aQ.getAnswers(i).getAnswer().length();
					}
				}
			}
			if (o1 instanceof OpenQ) {
				OpenQ oQ = (OpenQ) o1;
				Length1 += oQ.getAnswer().length();
			}
		}
		
		if (o2 != null) {
			if (o2 instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) o2;
				for (int i = 0; i < aQ.getAnswersNum(); i++) {
					if (aQ.getAnswers(i).getAnswer() != null) {
						Length2 += aQ.getAnswers(i).getAnswer().length();
					}
				}
			}
			if (o2 instanceof OpenQ) {
				OpenQ oQ = (OpenQ) o2;
				Length2 += oQ.getAnswer().length();
			}
		}
		
		if (Length1 > Length2) {
			return 1;
		}
		else if (Length1 < Length2) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
