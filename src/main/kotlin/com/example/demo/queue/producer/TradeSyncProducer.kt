package com.example.demo.queue.producer

import com.example.demo.data.CreateTradeSample
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service

@Service
class TradeSyncProducer(
     val streamBridge: StreamBridge
) {

    companion object{
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun tradeProducer(message: CreateTradeSample){
        streamBridge.send("tradeProducer-out-0", message);
    }
}