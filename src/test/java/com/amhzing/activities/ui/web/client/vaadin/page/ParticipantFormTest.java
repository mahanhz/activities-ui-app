package com.amhzing.activities.ui.web.client.vaadin.page;

import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import org.junit.Before;
import org.junit.Test;

import static com.amhzing.activities.ui.web.client.model.ParticipantModelHelper.*;
import static org.junit.Assert.fail;

public class ParticipantFormTest {

    private ParticipantForm participantForm;

    @Before
    public void setUp() {
        participantForm = new ParticipantForm();
    }

    @Test
    public void should_create_form() throws Exception {
        try {
            participantForm.participantDetails(participantModel());
        } catch(Exception ex) {
            fail("Should have created the form");
        }
    }

    private ParticipantModel participantModel() {
        return ParticipantModel.create("pId", name(), address(), contactNumber(), email());
    }
}