package com.onextwonetwork.betdataservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onextwonetwork.betdataservice.converter.BetConverter;
import com.onextwonetwork.betdataservice.dao.BetCustomRepository;
import com.onextwonetwork.betdataservice.dao.BetEntity;
import com.onextwonetwork.betdataservice.dao.BetRepository;
import com.onextwonetwork.betdataservice.util.BetLoader;
import jodd.util.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BetServiceImpl implements BetService{

    private static final Logger LOGGER = Logger.getLogger(BetServiceImpl.class.getName());
    private final BetRepository betRepository;
    private final BetCustomRepository betCustomRepository;
    private final BetConverter betConverter;
    private final MessageSenderService messageSenderService;

    private final BetLoader betLoader;

    private final ObjectMapper mapper;

    public BetServiceImpl(BetRepository betRepository, BetCustomRepository betCustomRepository, BetConverter betConverter, MessageSenderService messageSenderService, BetLoader betLoader, ObjectMapper mapper) {
        this.betRepository = betRepository;
        this.betCustomRepository = betCustomRepository;
        this.betConverter = betConverter;
        this.messageSenderService = messageSenderService;
        this.betLoader = betLoader;
        this.mapper = mapper;
    }

    @Override
    public List<BetDTO> getBetsByParams(String game, Long clientId, String startDateStr, String endDateStr, int page, int pageSize) {
        Date startDate=null, endDate=null;
        if(StringUtil.isNotBlank(startDateStr) && StringUtil.isNotBlank(endDateStr)){
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse(startDateStr);
                endDate = sdf.parse(endDateStr);
            }catch (Exception ex){
                throw new IllegalArgumentException("Date format mismatch. Expected format: yyyy-MM-dd");
            }
        }

        return betCustomRepository.searchBets(PageRequest.of(page, pageSize), game, clientId, startDate, endDate);
    }

    @Override
    public void loadBets() {
        List<BetDTO> bets = betLoader.loadBets();
        bets.forEach(this::saveBet);
    }

    @Override
    public BetDTO saveBet(BetDTO bet) {
        BetEntity savedBet = betRepository.save(betConverter.convert(bet));
        BetDTO betResponse = betConverter.convert(savedBet);
        try {
//            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            messageSenderService.sendMessage(mapper.writeValueAsString(betResponse));
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Unable to parse message [%s] due to error", e);
        }

        return betResponse;
    }

    @Override
    public BetDTO updateBet(BetDTO betDto) {
        Optional<BetEntity> betEntity = betRepository.findById(betDto.getId());

        if(betEntity.isEmpty()) {
            throw new IllegalArgumentException("No Bet exists with id: " + betDto.getId());
        } else {
            BetEntity entityInDB = betEntity.get();
            entityInDB.setNumbets(betDto.getNumbets());
            entityInDB.setGame(betDto.getGame());
            entityInDB.setStake(betDto.getStake());
            entityInDB.setReturns(betDto.getReturns());
            entityInDB.setClientId(betDto.getClientId());

            entityInDB = betRepository.save(entityInDB);
            return betConverter.convert(entityInDB);
        }
    }

    @Override
    public void saveBets(List<BetDTO> bets) {
        List<BetEntity> betEntities = bets.stream()
                .map(betConverter::convert)
                .collect(Collectors.toList());

        betRepository.saveAll(betEntities);
    }

    @Override
    public void delete(long id) {
        betRepository.deleteById(id);
    }
}
