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
    private Answer mustMatchRightAnswer;
    private Answer mustMatchWrongAnswer;
    private Answer matchRightAnswer;
    private Answer matchWrongAnswer;
    private Answer notMatchRightAnswer1;
    private Answer notMatchWrongAnswer1;
    private Answer notMatchRightAnswer2;
    private Answer notMatchWrongAnswer2;

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

    @Before
    public void init_common_variables() {
        BooleanQuestion matchQuestion = new BooleanQuestion(1, "match question");
        matchRightAnswer = new Answer(matchQuestion, 1);
        matchWrongAnswer = new Answer(matchQuestion, 2);

        BooleanQuestion notMatchQuestion1 = new BooleanQuestion(1, "not match question");
        notMatchRightAnswer1 = new Answer(notMatchQuestion1, 1);
        notMatchWrongAnswer1 = new Answer(notMatchQuestion1, 2);

        BooleanQuestion notMatchQuestion2 = new BooleanQuestion(1, "not match question");
        notMatchRightAnswer2 = new Answer(notMatchQuestion2, 1);
        notMatchWrongAnswer2 = new Answer(notMatchQuestion2, 2);
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

    @Test
    public void should_return_false_when_give_must_match_and_not_match_criteria() {
        Criterion criterion2 = new Criterion(notMatchRightAnswer1, Weight.MustMatch);
        Criterion criterion1 = new Criterion(notMatchRightAnswer2, Weight.Important);

        Criteria criteria1 = new Criteria();
        criteria1.add(criterion1);
        criteria1.add(criterion2);

        profile = new Profile("profile");
        profile.add(notMatchWrongAnswer1);
        profile.add(notMatchWrongAnswer2);

        boolean result = profile.matches(criteria1);
        assertThat(result, is(false));
        assertThat(profile.score(), is(0));
    }

    @Test
    public void should_return_false_when_give_must_match_and_one_match_criteria() {
        Criterion mustMatchCriterion = new Criterion(notMatchRightAnswer1, Weight.MustMatch);
        Criterion matchCriterion = new Criterion(matchRightAnswer, Weight.Important);

        Criteria criteria1 = new Criteria();
        criteria1.add(matchCriterion);
        criteria1.add(mustMatchCriterion);

        profile = new Profile("profile");
        profile.add(notMatchWrongAnswer1);
        profile.add(matchRightAnswer);

        boolean result = profile.matches(criteria1);
        assertThat(result, is(false));
        assertThat(profile.score(), is(1000));
    }
}