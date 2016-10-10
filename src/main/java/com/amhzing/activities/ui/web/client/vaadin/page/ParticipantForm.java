package com.amhzing.activities.ui.web.client.vaadin.page;


import com.amhzing.activities.ui.web.client.model.AddressModel;
import com.amhzing.activities.ui.web.client.model.NameModel;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantForm extends FormLayout {

    private static final int WIDTH = 22;

    private TextField nameText = new TextField();
    private TextField addressText = new TextField();
    private TextField contactNumberText = new TextField();
    private TextField emailText = new TextField();
    private TextField idText = new TextField();

    public void participantDetails(final ParticipantModel participant) {
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

    private String name(final ParticipantModel participant) {
        final NameModel name = participant.getName();

        return defaultValue(name.getFirstName(), " ")
                + defaultValue(name.getMiddleName(), " ")
                + defaultValue(name.getLastName(), " ")
                + defaultValue(name.getSuffix());
    }

    private String address(final ParticipantModel participant) {
        final AddressModel address = participant.getAddress();

        return defaultValue(address.getAddressLine1(), ", ")
                + defaultValue(address.getAddressLine2(), ", ")
                + defaultValue(address.getCity(), ", ")
                + defaultValue(address.getCountry(), ", ")
                + defaultValue(address.getPostalCode());
    }

    private String contactNumber(final ParticipantModel participant) {
        return defaultValue(participant.getContactNumber());
    }

    private String email(final ParticipantModel participant) {
        return defaultValue(participant.getEmail());
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
