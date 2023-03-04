package com.onextwonetwork.betdataservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/bet")
public class BetController {
    private static final Logger LOGGER = Logger.getLogger(BetController.class.getName());

    private final int SUCCESS_RESPONSE_CODE = 0;
    private final String SUCCESS_RESPONSE_MESSAGE = "%d records found";
    private final int NO_RECORDS_RESPONSE_CODE = 1;
    private final String NO_RECORDS_RESPONSE_MESSAGE = "search did not return any results";

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @GetMapping(produces = {"text/plain", "application/json"})
    public ResponseEntity<BetResponse> searchBets(
            @RequestParam(name = "game", required = false) String game,
            @RequestParam(name = "clientId", required = false) Long clientId,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size){
        List<BetDTO> response = betService.getBetsByParams(game, clientId, startDate, endDate, page, size);
        if(response.isEmpty()){
            return ResponseEntity.ok(new BetResponse(Collections.emptyList(), NO_RECORDS_RESPONSE_CODE, NO_RECORDS_RESPONSE_MESSAGE));
        }
        return ResponseEntity.ok(new BetResponse(response, SUCCESS_RESPONSE_CODE, String.format(SUCCESS_RESPONSE_MESSAGE, response.size())));
    }

    @GetMapping(value="/load")
    public ResponseEntity<String> loadBets(){
        betService.loadBets();
        return ResponseEntity.ok("Bets loaded");
    }

}
