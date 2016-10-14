package com.amhzing.activities.ui.web.client.adapter;

import com.amhzing.activities.ui.application.exception.FailureException;
import com.amhzing.activities.ui.application.participant.ParticipantService;
import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.amhzing.activities.ui.web.client.exception.UIFriendlyException;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amhzing.activities.ui.web.client.adapter.ParticipantFactory.adaptParticipant;
import static com.amhzing.activities.ui.web.client.adapter.ParticipantFactory.queryCriteria;
import static com.amhzing.activities.ui.web.client.exception.UIFriendlyException.HTML_ERROR_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
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
}
