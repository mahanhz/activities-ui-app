package com.amhzing.activities.ui.web.client.adapter;

import com.amhzing.activities.ui.domain.participant.model.Address;
import com.amhzing.activities.ui.domain.participant.model.Name;
import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.web.client.model.AddressModel;
import com.amhzing.activities.ui.web.client.model.NameModel;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.noNullElements;

public class ParticipantFactory {

    private ParticipantFactory() {
    }

    public static QueryCriteria queryCriteria(final SearchSpecification searchSpec) {
        return QueryCriteria.create(searchSpec.getCountry(),
                                    searchSpec.getCity(),
                                    searchSpec.getAddressLine1(),
                                    searchSpec.getLastName(),
                                    searchSpec.getParticipantId());
    }

    public static List<ParticipantModel> adaptParticipant(final List<Participant> participants) {
        noNullElements(participants);

        return participants.stream()
                           .map(participant -> ParticipantModel.create(participant.getParticipantId(),
                                                                       name(participant),
                                                                       address(participant),
                                                                       contactNumber(participant),
                                                                       email(participant)))
                           .collect(toList());
    }

    private static NameModel name(final Participant participant) {
        final Name name = participant.getName();
        return NameModel.create(name.getFirstName(), name.getMiddleName(), name.getLastName(), name.getSuffix());
    }

    private static AddressModel address(final Participant participant) {
        final Address address = participant.getAddress();
        return AddressModel.create(address.getAddressLine1(),
                                   address.getAddressLine2(),
                                   address.getCity(),
                                   address.getPostalCode(),
                                   address.getCountry().getCode());
    }

    private static String contactNumber(final Participant participant) {
        if (participant.getContactNumber() != null && isNotBlank(participant.getContactNumber().getValue())) {
            return participant.getContactNumber().getValue();
        }

        return "";
    }

    private static String email(final Participant participant) {
        if (participant.getEmail() != null && isNotBlank(participant.getEmail().getValue())) {
            return participant.getEmail().getValue();
        }

        return "";
    }
}
