package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ProfileTest {

    private Profile profile;
    private Criteria criteria, emptyCriteria;
    private Criterion criterion;
    private Answer answerRight, answerWrong;
    private Question question;

    @Before
    public void init() {
        question = new BooleanQuestion(1, "question");
        answerRight = new Answer(question, 1);
        answerWrong = new Answer(question, 2);
        criterion = new Criterion(answerRight, Weight.Important);
        emptyCriteria = new Criteria();
        criteria = new Criteria();
        criteria.add(criterion);
        profile = new Profile("profile");
    }

    @Test
    public void should_return_false_when_give_empty_criteria() {
        boolean result = profile.matches(emptyCriteria);
        assertThat(result, is(false));
    }

    @Test
    public void should_get_zero_when_give_empty_criteria() {
        profile.matches(emptyCriteria);
        assertThat(profile.score(), is(0));
    }

    @Test
    public void should_return_false_when_give_not_match_criteria() {
        profile.add(answerWrong);
        boolean result = profile.matches(criteria);
        assertThat(result, is(false));
        assertThat(profile.score(), is(0));
    }

    @Test
    public void should_return_true_when_give_all_answers_match_with_criteria() {
        profile.add(answerRight);
        boolean result = profile.matches(criteria);
        assertThat(result, is(true));
        assertThat(profile.score(), is(1000));
    }
}