package com.example.demo.queue.consumer

import com.example.demo.model.Trades
import com.example.demo.service.TradeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import java.util.function.Consumer

@Component
class TradeSyncConsumer(
    private val tradeService: TradeService
) {
    companion object{
        private val logger = LoggerFactory.getLogger(this.javaClass)
    }

    @Bean
    fun tradeConsumer(): Consumer<Message<Trades>> {
        return Consumer<Message<Trades>> {
                value -> tradeService.orderTradeConsumer(value.payload)
        }
    }
}
