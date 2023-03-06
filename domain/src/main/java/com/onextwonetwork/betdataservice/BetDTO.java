package com.onextwonetwork.betdataservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BetDTO {
    private final Long id;
    private final Long numbets;
    private final String game;
    private final BigDecimal stake;
    private final BigDecimal returns;
    private final Long clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate betDate;

    public BetDTO(Builder builder){
        this.id = builder.id;
        this.numbets = builder.numbets;
        this.game = builder.game;
        this.stake = builder.stake;
        this.returns = builder.returns;
        this.clientId = builder.clientId;
        this.betDate = builder.betDate;
    }
    public BetDTO(@JsonProperty("id")Long id,
                  @JsonProperty("numbets")Long numbets,
                  @JsonProperty("game")String game,
                  @JsonProperty("stake")BigDecimal stake, @JsonProperty("returns")BigDecimal returns, @JsonProperty("clientId")Long clientId, @JsonProperty("betDate")LocalDate betDate) {
        this.id = id;
        this.numbets = numbets;
        this.game = game;
        this.stake = stake;
        this.returns = returns;
        this.clientId = clientId;
        this.betDate = betDate;
    }

    public Long getId() {
        return id;
    }

    public Long getNumbets() {
        return numbets;
    }

    public String getGame() {
        return game;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public BigDecimal getReturns() {
        return returns;
    }

    public Long getClientId() {
        return clientId;
    }

    public LocalDate getBetDate() {
        return betDate;
    }
    public static Builder aBetDTO() {
        return new Builder();
    }
    public static final class Builder {
        private Long id;
        private Long numbets;
        private String game;
        private BigDecimal stake;
        private BigDecimal returns;
        private Long clientId;
        private LocalDate betDate;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder numbets(Long numbets) {
            this.numbets = numbets;
            return this;
        }
        public Builder game(String game) {
            this.game = game;
            return this;
        }
        public Builder stake(BigDecimal stake) {
            this.stake = stake;
            return this;
        }
        public Builder returns(BigDecimal returns) {
            this.returns = returns;
            return this;
        }

        public Builder clientId(Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder betDate(LocalDate betDate) {
            this.betDate = betDate;
            return this;
        }
        public BetDTO build() {
            return new BetDTO(this);
        }
    }
}
