package com.example.demo.service

import com.example.demo.data.CreateTradeSample
import com.example.demo.model.Trades
import com.example.demo.queue.producer.TradeSyncProducer
import com.example.demo.repo.TradeRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class TradeService (
    val tradeRepo: TradeRepo,
    val tradeSyncProducer: TradeSyncProducer
){

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun createTrade(createTradeSample: CreateTradeSample): Boolean {
        log.info("Create Trade - data: ${createTradeSample.orderId}, ${createTradeSample.exchangeOrderId}")

        log.info("Create Trade - insert data in postgres")

        val trade = Trades(id = 0, orderId =createTradeSample.orderId, exchangeOrderId = createTradeSample.exchangeOrderId,
            exchangeOrderTime = createTradeSample.exchangeOrderTime, price=createTradeSample.price,
            quantity = createTradeSample.quantity, createdOn = LocalDateTime.now(),  createdBy = createTradeSample.createdBy)

        log.info("Data saving in progress ...")
        tradeRepo.save(trade)

        log.info("PRODUCER | KAFKA > Sending trade")
        tradeSyncProducer.tradeProducer(createTradeSample)
        Thread.sleep(5000)

        return true
    }

    fun showTrades(): List<Trades>{
        return tradeRepo.findAll()
    }

    fun orderTradeConsumer(tradesData: Trades){

        log.info("CONSUMER | KAFKA > Received trade from kafka. Data - $tradesData ")
    }
}