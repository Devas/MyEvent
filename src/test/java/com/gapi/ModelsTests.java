package com.gapi;

import com.myevent.helpers.PersonGenerator;
import org.junit.Assert;
import org.junit.Test;

public class ModelsTests {

    private PersonGenerator personGenerator = new PersonGenerator();

    @Test
    public void shouldReturnFirstSpeaker() {
        Assert.assertEquals("speaker-A", personGenerator.getSpeaker(0).getName());
    }
}
