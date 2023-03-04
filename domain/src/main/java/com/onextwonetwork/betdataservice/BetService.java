package com.onextwonetwork.betdataservice;

import java.util.List;

public interface BetService {

    List<BetDTO> getBetsByParams(String game, Long clientId, String startDateStr, String endDateStr, int page, int pageSize);

    BetDTO saveBet(BetDTO bet);

    BetDTO updateBet(BetDTO betDto);

    void saveBets(List<BetDTO> bets);

    void delete(long id);

    void loadBets();
}
