package com.amhzing.activities.ui.web.client.adapter;

import com.amhzing.activities.ui.application.exception.FailureException;
import com.amhzing.activities.ui.application.participant.ParticipantService;
import com.amhzing.activities.ui.domain.participant.model.Address;
import com.amhzing.activities.ui.domain.participant.model.Name;
import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.web.client.exception.UIFriendlyException;
import com.amhzing.activities.ui.web.client.model.AddressModel;
import com.amhzing.activities.ui.web.client.model.NameModel;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amhzing.activities.ui.web.client.exception.UIFriendlyException.HTML_ERROR_MESSAGE;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ParticipantAdapter {

    private ParticipantService participantService;

    @Autowired
    public ParticipantAdapter(final ParticipantService participantService) {
        this.participantService = notNull(participantService);
    }

    public List<ParticipantModel> participants(final SearchSpecification searchSpec) {
        notNull(searchSpec);

        try {
            final List<Participant> participantList = participantService.getParticipants(queryCriteria(searchSpec));
            return adaptParticipant(participantList);
        } catch (FailureException ex) {
            String message = HTML_ERROR_MESSAGE;
            if (isNotBlank(ex.getCorrelatedErrorId())) {
                message += "<br/>To follow up please reference this error id: " + ex.getCorrelatedErrorId();
            }
            throw new UIFriendlyException(message);
        } catch (Exception ex) {
            throw new UIFriendlyException(HTML_ERROR_MESSAGE);
        }
    }

    private static QueryCriteria queryCriteria(final SearchSpecification searchSpec) {
        return QueryCriteria.create(searchSpec.getCountry(),
                                    searchSpec.getCity(),
                                    searchSpec.getAddressLine1(),
                                    searchSpec.getLastName(),
                                    searchSpec.getParticipantId());
    }

    private static List<ParticipantModel> adaptParticipant(final List<Participant> participants) {
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
