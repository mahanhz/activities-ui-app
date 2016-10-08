package com.amhzing.activities.ui.web.controller;

import com.amhzing.activities.ui.annotation.LogExecutionTime;
import com.amhzing.activities.ui.participant.QueryCriteria;
import com.amhzing.activities.ui.participant.mapping.Participants;
import io.atlassian.fugue.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.amhzing.activities.ui.web.MediaType.APPLICATION_JSON_V1;


@RestController
@RequestMapping(path = "/query", produces = APPLICATION_JSON_V1)
public class ParticipantQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantQueryController.class);

    private final ParticipantService<Failure, Participants> participantService;

    @Autowired
    public ParticipantQueryController(final ParticipantService<Failure, Participants> participantService) {
        this.participantService = participantService;
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public @Valid String queryByCountry(@PathVariable final String country) {

        final QueryCriteria queryCriteria = QueryCriteria.create(country, "", "", "", "");
        final Either<Failure, Participants> response = participantService.participantsByCriteria(queryCriteria);

        return "Something";
    }
}
