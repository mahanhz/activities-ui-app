package com.amhzing.activities.ui.web.client.vaadin.page;

import com.amhzing.activities.ui.participant.mapping.Address;
import com.amhzing.activities.ui.participant.mapping.Name;
import com.amhzing.activities.ui.participant.mapping.Participant;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.util.Assert.notNull;

public class ParticipantForm extends FormLayout {

    private static final int WIDTH = 22;

    private TextField nameText = new TextField();
    private TextField addressText = new TextField();
    private TextField contactNumberText = new TextField();
    private TextField emailText = new TextField();
    private TextField idText = new TextField();

    public void participantDetails(final Participant participant) {
        notNull(participant);

        nameText.setCaption("Name");
        nameText.setValue(name(participant));
        setWidth(nameText);
        setReadOnly(nameText);

        addressText.setCaption("Address");
        addressText.setValue(address(participant));
        setWidth(addressText);
        setReadOnly(addressText);

        contactNumberText.setCaption("Contact number");
        contactNumberText.setValue(contactNumber(participant));
        setWidth(contactNumberText);
        setReadOnly(contactNumberText);

        emailText.setCaption("Email");
        emailText.setValue(email(participant));
        setWidth(emailText);
        setReadOnly(emailText);

        idText.setCaption("Id");
        idText.setValue(participant.getParticipantId());
        setWidth(idText);
        setReadOnly(idText);

        addComponents(nameText, addressText, contactNumberText, emailText, idText);
        setVisible(true);
    }

    private void setWidth(final TextField field) {
        field.setWidth(WIDTH, Unit.EM);
    }

    private void setReadOnly(final TextField field) {
        field.setEnabled(false);
    }

    private String name(final Participant participant) {
        final Name name = participant.getName();

        return defaultValue(name.getFirstName(), " ")
                + defaultValue(name.getMiddleName(), " ")
                + defaultValue(name.getLastName(), " ")
                + defaultValue(name.getSuffix());
    }

    private String address(final Participant participant) {
        final Address address = participant.getAddress();

        return defaultValue(address.getAddressLine1(), ", ")
                + defaultValue(address.getAddressLine2(), ", ")
                + defaultValue(address.getCity(), ", ")
                + defaultValue(address.getCountry().getCode(), ", ")
                + defaultValue(address.getPostalCode());
    }

    private String contactNumber(final Participant participant) {
        if (participant.getContactNumber() != null) {
            return defaultValue(participant.getContactNumber().getValue());
        }

        return "";
    }

    private String email(final Participant participant) {
        if (participant.getEmail() != null) {
            return defaultValue(participant.getEmail().getValue());
        }

        return "";
    }

    private String defaultValue(final String value, final String concat) {
        if (isNotBlank(value)) {
            return value + concat;
        }
        return "";
    }

    private String defaultValue(final String value) {
        return defaultValue(value, "");
    }
}
