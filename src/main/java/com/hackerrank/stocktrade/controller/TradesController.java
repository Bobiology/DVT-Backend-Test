package com.hackerrank.stocktrade.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.service.TradeService;

@RestController
@RequestMapping(value = "")
public class TradesController {
	
	@Autowired
	TradeService tradeService;
	
	@PostMapping(value="/trades", consumes = {"application/json"}, produces= {"application/json"})
    public ResponseEntity<String> createTrade(@RequestBody Trade trade) {
		return tradeService.createTrade(trade);
	}
	
	@DeleteMapping("/erase")
	public ResponseEntity<String> deleteAll(){
		return tradeService.eraseAllTrades();
	}
	
	@GetMapping(value="/trades/{id}", produces = {"application/json"})
	public ResponseEntity<Optional<Trade>> findByTradeId(@RequestParam Long tradeId){
		return tradeService.findByTradeId(tradeId);
	}
	
	@GetMapping(value="/trades",produces = {"application/json"})
	public ResponseEntity<List<Trade>> findAll(){
		return tradeService.findAll();
	}
	
	@GetMapping(value="/trades/users/{userID}", produces = {"application/json"})
	public ResponseEntity<List<Trade>> findTradesByUserID(@RequestParam Long userID){
		return tradeService.findTradesByUserID(userID);
	}
	
	@GetMapping(value="/trades/stocks/{stockSymbol}?type={tradeType}&start={startDate}&end={endDate}",
			produces = {"application/json"})
	public ResponseEntity<List<Trade>> tradeRecords(@RequestParam String stockSymbol,
			@RequestParam String tradeType,@RequestParam Timestamp startDate, @RequestParam Timestamp endDate){
	
		return tradeService.tradeRecords(stockSymbol,tradeType,startDate,endDate);
		
	}
	
	@GetMapping(value="/stocks/{stockSymbol}/price?start={startDate}&end={endDate}", produces= {"application/json"})
	public ResponseEntity<List<Trade>> highestAndLowestPrices(@RequestParam String stockSymbol,
			@RequestParam Timestamp startDate, @RequestParam Timestamp endDate){
	
		return tradeService.highestAndLowestPrices(stockSymbol,startDate,endDate);
		
	} 
}
