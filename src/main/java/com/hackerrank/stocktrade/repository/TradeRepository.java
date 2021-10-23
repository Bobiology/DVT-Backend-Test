package com.hackerrank.stocktrade.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.stocktrade.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>{

	//public Trade findById(Long tradeId);
	
	public List<Trade> findAll();
	
	//public List<Trade> findByUser();

	//@Query("select p from Trade p where p.symbol =:symbol and p.type=:type and p.timestamp between :startDate and :enddate")
	//public List<Trade> findTradeRecords(String symbol, String type, Timestamp startDate, Timestamp endDate);

	public List<Trade> findBySymbolAndTypeAndTimestampBetween(String symbol, String type, Timestamp startDate, Timestamp endDate);

	public List<Trade> findBySymbolAndTimestampBetween(String stockSymbol, Timestamp startDate,
			Timestamp endDate);
	
}
