package com.amhzing.activities.ui.participant.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

@JsonInclude
public class ErrorResponse {

    private final String code;
    private final String message;
    private final String correlationId;

    private ErrorResponse(final String code, final String message, final String correlationId) {
        this.code = code;
        this.message = message;
        this.correlationId = correlationId;
    }

    @JsonCreator
    public static ErrorResponse create(@JsonProperty("code") final String code,
                                       @JsonProperty("message") final String message,
                                       @JsonProperty("correlationId") final String correlationId) {
        return new ErrorResponse(code, message, correlationId);
    }

    public boolean isEmpty () {
        return isBlank(code) && isBlank(message) && isBlank(correlationId);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, correlationId);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
