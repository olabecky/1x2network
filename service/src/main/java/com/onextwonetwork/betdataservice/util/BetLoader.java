package com.onextwonetwork.betdataservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onextwonetwork.betdataservice.BetDTO;
import jodd.json.JsonObject;
import jodd.json.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class BetLoader {
    private static final Logger LOGGER = Logger.getLogger(BetLoader.class.getName());

    @Value("classpath:bets.json")
    Resource resourceFile;

    public List<BetDTO> loadBets() {
        ObjectMapper mapper = new ObjectMapper();
        List<Bets> betData;
        try {
            String content = resourceFile.getContentAsString(StandardCharsets.UTF_8);

            JsonNode map = mapper.readTree(content);
            JsonNode bets = map.get("bets");
            betData = Arrays.asList(mapper.readValue(bets.toPrettyString(), Bets[].class));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            return betData.stream()
                    .map(b-> {
                        try {
                            return BetDTO.aBetDTO()
                                    .id(b.getId())
                                    .numbets(b.getNumbets())
                                    .game(b.getGame())
                                    .stake(b.getStake())
                                    .returns(b.getReturns())
                                    .clientId(b.getClientId())
                                    .betDate(sdf.parse(b.getDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                    .build();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to read bets from json file due to error ", e);
        }
        return Collections.emptyList();
    }
}
