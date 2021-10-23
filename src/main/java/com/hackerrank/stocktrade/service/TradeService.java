package com.hackerrank.stocktrade.service;

import java.util.TimeZone;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hackerrank.stocktrade.model.Trade;
import com.hackerrank.stocktrade.repository.TradeRepository;

@Service
public class TradeService {
	@Autowired
	TradeRepository tradeRepository;
	// create new trade
	public ResponseEntity<String> createTrade(Trade request) {
		Optional<Trade> trade = Optional.of(new Trade());
		trade = tradeRepository.findById(request.getId());
		if(trade !=null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}else {
			Trade newTrade = new Trade();
			newTrade = tradeRepository.save(request);
			if(newTrade != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(null);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
		}

	}
	// erase all trades
	public ResponseEntity<String> eraseAllTrades(){
		tradeRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	// return trades with given id
	public ResponseEntity<Optional<Trade>> findByTradeId(Long tradeID){
		Optional<Trade> trade = Optional.of(new Trade());
		trade = tradeRepository.findById(tradeID);
		return ResponseEntity.status(HttpStatus.OK).body(trade);
	}
	
	public ResponseEntity<List<Trade>> findAll(){
		List<Trade> trade = new ArrayList<>();
		trade = tradeRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(trade);
	}
	public ResponseEntity<List<Trade>> findTradesByUserID(Long userID) {
		List<Trade> trade = new ArrayList<>();
		trade = tradeRepository.findAll();
		trade.stream().filter(tradeByUserId ->tradeByUserId.getUser().getId().equals(userID)).collect(Collectors.toList());
		if(trade.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(trade);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(trade);
		}
		
	}
	public ResponseEntity<List<Trade>> tradeRecords(String stockSymbol, String tradeType, Timestamp startDate, Timestamp endDate) {
		List<Trade> trade = new ArrayList<>();
		trade = tradeRepository.findBySymbolAndTypeAndTimestampBetween(stockSymbol, tradeType, startDate, endDate);
		if(trade.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(trade);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(trade);
		}
	}
	public ResponseEntity<List<Trade>> highestAndLowestPrices(String stockSymbol, Timestamp startDate,
			Timestamp endDate) {
		List<Trade> trade = new ArrayList<>();
		List<Trade> tradePrice = new ArrayList<>(); 
		trade = tradeRepository.findBySymbolAndTimestampBetween(stockSymbol, startDate, endDate);
		
		tradePrice.add(trade.stream().min(Comparator.comparing(Trade::getPrice)).orElseThrow(null));
		tradePrice.add(trade.stream().max(Comparator.comparing(Trade::getPrice)).orElseThrow(null));
		
		if(trade.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(tradePrice);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tradePrice);
		}
	}
	
	
}
