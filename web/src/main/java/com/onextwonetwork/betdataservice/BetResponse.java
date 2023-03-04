package com.onextwonetwork.betdataservice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BetResponse {

    private List<BetDTO> bets;
    private int responseCode;
    private String responseMessage;

    public BetResponse(List<BetDTO> bets, int responseCode, String responseMessage) {
        this.bets = bets;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
