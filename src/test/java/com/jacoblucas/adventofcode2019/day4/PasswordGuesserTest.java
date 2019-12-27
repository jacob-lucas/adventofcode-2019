package com.jacoblucas.adventofcode2019.day4;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PasswordGuesserTest {

    private PasswordGuesser passwordGuesser;

    @Before
    public void setUp() {
        passwordGuesser = new PasswordGuesser(111111, 999999);
    }

    @Test
    public void testMeetsCriteria() {
        assertThat(passwordGuesser.meetsCriteria(122345), is(true));
        assertThat(passwordGuesser.meetsCriteria(111111), is(true));
        assertThat(passwordGuesser.meetsCriteria(111123), is(true));
        assertThat(passwordGuesser.meetsCriteria(999999), is(true));

        assertThat(passwordGuesser.meetsCriteria(135679), is(false));
        assertThat(passwordGuesser.meetsCriteria(223450), is(false));
        assertThat(passwordGuesser.meetsCriteria(123789), is(false));
    }

}
