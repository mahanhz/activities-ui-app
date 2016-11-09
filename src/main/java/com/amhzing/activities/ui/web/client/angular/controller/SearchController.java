package com.amhzing.activities.ui.web.client.angular.controller;

import com.amhzing.activities.ui.web.client.adapter.ParticipantAdapter;
import com.amhzing.activities.ui.web.client.angular.model.Participants;
import com.amhzing.activities.ui.web.client.exception.UIFriendlyException;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.amhzing.activities.ui.web.client.exception.UIFriendlyException.HTML_ERROR_MESSAGE;

@RestController
public class SearchController extends AbstractController {

    @Autowired
    private ParticipantAdapter participantAdapter;

    @GetMapping(path = {"/search"})
    public @Valid Participants searchParticipants(@RequestParam("country") final String country,
                                                  @RequestParam(value = "city", required = false) final String city,
                                                  @RequestParam(value = "addressLine1", required = false) final String addressLine1,
                                                  @RequestParam(value = "lastName", required = false) final String lastName,
                                                  @RequestParam(value = "participantId", required = false) final String participantId) {
        try {
            final SearchSpecification searchSpec = SearchSpecification.create(country, city, addressLine1, lastName, participantId);
            final List<ParticipantModel> participantModels = participantAdapter.participants(searchSpec);

            return Participants.createErrorFree(participantModels);
        } catch (final UIFriendlyException ex) {
            return Participants.createWithError(ex.getMessage());
        }  catch (final Exception ex) {
            return Participants.createWithError(HTML_ERROR_MESSAGE);
        }
    }
}
