package com.onextwonetwork.betdataservice.converter;

import com.onextwonetwork.betdataservice.BetDTO;
import com.onextwonetwork.betdataservice.dao.BetEntity;
import org.springframework.stereotype.Component;

@Component
public class BetConverter {
    public BetDTO convert(BetEntity entity){
        return BetDTO.aBetDTO()
                .id(entity.getId())
                .numbets(entity.getNumbets())
                .game(entity.getGame())
                .stake(entity.getStake())
                .returns(entity.getReturns())
                .clientId(entity.getClientId())
                .betDate(entity.getBetDate())
                .build();
    }

    public BetEntity convert(BetDTO betDTO){
        BetEntity betEntity = new BetEntity();
        betEntity.setId(betDTO.getId());
        betEntity.setNumbets(betDTO.getNumbets());
        betEntity.setGame(betDTO.getGame());
        betEntity.setStake(betDTO.getStake());
        betEntity.setReturns(betDTO.getReturns());
        betEntity.setClientId(betDTO.getClientId());
        betEntity.setBetDate(betDTO.getBetDate());

        return betEntity;
    }

}
