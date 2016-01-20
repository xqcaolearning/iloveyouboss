package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ProfileTest {

    private Profile profile;
    private Criteria criteria, emptyCriteria;
    private Answer mustMatchRightAnswer;
    private Answer mustMatchWrongAnswer;
    private Answer matchRightAnswer;
    private Answer matchWrongAnswer;
    private Answer notMatchRightAnswer1;
    private Answer notMatchWrongAnswer1;
    private Answer notMatchRightAnswer2;
    private Answer notMatchWrongAnswer2;
    private Criterion matchCriterion;
    private Criterion notMatchCriterion1;
    private Criterion notMatchCriterion2;
    private Criterion mustMatchCriterion;

    @Before
    public void init_common_variables() {
        emptyCriteria = new Criteria();
        criteria = new Criteria();
        profile = new Profile("profile");

        BooleanQuestion matchQuestion = new BooleanQuestion(1, "match question");
        matchRightAnswer = new Answer(matchQuestion, 1);
        matchWrongAnswer = new Answer(matchQuestion, 2);

        BooleanQuestion notMatchQuestion1 = new BooleanQuestion(1, "not match question");
        notMatchRightAnswer1 = new Answer(notMatchQuestion1, 1);
        notMatchWrongAnswer1 = new Answer(notMatchQuestion1, 2);

        BooleanQuestion notMatchQuestion2 = new BooleanQuestion(1, "not match question");
        notMatchRightAnswer2 = new Answer(notMatchQuestion2, 1);
        notMatchWrongAnswer2 = new Answer(notMatchQuestion2, 2);

        BooleanQuestion mustMatchQuestion = new BooleanQuestion(1, "must match question");
        mustMatchRightAnswer = new Answer(mustMatchQuestion, 1);
        mustMatchWrongAnswer = new Answer(mustMatchQuestion, 2);

        matchCriterion = new Criterion(matchRightAnswer, Weight.Important);
        notMatchCriterion1 = new Criterion(notMatchRightAnswer1, Weight.Important);
        notMatchCriterion2 = new Criterion(notMatchRightAnswer2, Weight.Important);

        mustMatchCriterion = new Criterion(mustMatchRightAnswer, Weight.MustMatch);
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
        criteria.add(notMatchCriterion1);
        profile.add(notMatchWrongAnswer1);
        boolean result = profile.matches(criteria);
        assertThat(result, is(false));
        assertThat(profile.score(), is(0));
    }

    @Test
    public void should_return_true_when_give_all_answers_match_with_criteria() {
        criteria.add(matchCriterion);
        profile.add(matchRightAnswer);
        boolean result = profile.matches(criteria);
        assertThat(result, is(true));
        assertThat(profile.score(), is(1000));
    }

    @Test
    public void should_return_false_when_give_must_match_and_not_match_criteria() {
        criteria.add(mustMatchCriterion);
        criteria.add(notMatchCriterion1);

        profile.add(mustMatchWrongAnswer);
        profile.add(notMatchWrongAnswer1);

        boolean result = profile.matches(criteria);
        assertThat(result, is(false));
        assertThat(profile.score(), is(0));
    }

    @Test
    public void should_return_false_when_give_must_match_and_one_match_criteria() {
        criteria.add(mustMatchCriterion);
        criteria.add(matchCriterion);

        profile.add(mustMatchWrongAnswer);
        profile.add(matchRightAnswer);

        boolean result = profile.matches(criteria);
        assertThat(result, is(false));
        assertThat(profile.score(), is(1000));
    }
}