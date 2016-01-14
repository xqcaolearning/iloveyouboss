package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ProfileTest {

    private Profile profile;
    private Criteria emptyCriteria;

    @Before
    public void init_profile() {
        profile = new Profile("profile");
    }

    @Before
    public void init_empty_criteria() {
        emptyCriteria = new Criteria();
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
}