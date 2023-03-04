package com.onextwonetwork.betdataservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bets {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("numbets")
    public Long numbets;
    @JsonProperty("game")
    public String game;
    @JsonProperty("stake")
    public BigDecimal stake;
    @JsonProperty("returns")
    public BigDecimal returns;
    @JsonProperty("clientid")
    public Long clientId;
    @JsonProperty("date")
    public String date;

    public Bets() {
    }
//    public Bets(Long id, Long numbets, String game, BigDecimal stake, BigDecimal returns, Long clientid, String date) {
//        this.id = id;
//        this.numbets = numbets;
//        this.game = game;
//        this.stake = stake;
//        this.returns = returns;
//        this.clientid = clientid;
//        this.date = date;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumbets() {
        return numbets;
    }

    public void setNumbets(Long numbets) {
        this.numbets = numbets;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getReturns() {
        return returns;
    }

    public void setReturns(BigDecimal returns) {
        this.returns = returns;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
