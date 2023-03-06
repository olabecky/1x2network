package com.onextwonetwork.betdataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;

@JsonDeserialize(builder = BetResponse.Builder.class)
public class BetResponse {

    private final List<BetDTO> bets;
    private final int responseCode;
    private final String responseMessage;

    public BetResponse(Builder builder) {
        this.bets = builder.bets;
        this.responseCode = builder.responseCode;
        this.responseMessage = builder.responseMessage;
    }

    public List<BetDTO> getBets() {
        return bets;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public static Builder aBetResponse() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private List<BetDTO> bets;
        private int responseCode;
        private String responseMessage;

        private Builder() {
        }

        public Builder bets(List<BetDTO> bets) {
            this.bets = bets;
            return this;
        }

        public Builder responseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder responseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this;
        }
        public BetResponse build() {
            return new BetResponse(this);
        }
    }
}
