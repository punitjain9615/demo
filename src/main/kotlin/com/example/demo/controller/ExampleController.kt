package com.example.demo.controller

import com.example.demo.data.CreateTradeSample
import com.example.demo.model.Trades
import com.example.demo.repo.TradeRepo
import com.example.demo.service.TradeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/api/trade")
class ExampleController() {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Autowired
    lateinit var tradeService: TradeService
    @Autowired
    lateinit var tradeRepo: TradeRepo

    @ExceptionHandler(NoSuchFieldException::class)
    fun handleNotFound(e: NoSuchFieldException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @PostMapping("/add")
    fun createTrade(@RequestBody createTradeSample: CreateTradeSample): ResponseEntity<Any>{
        return ResponseEntity(tradeService.createTrade(createTradeSample), HttpStatus.OK)
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun findAll()= tradeService.showTrades()

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    fun showTradesBysizeAndPage(@RequestParam page: Int, @RequestParam size: Int): Page<Trades> {
        var pr: PageRequest = PageRequest.of(page, size)
        return tradeRepo.findAll(pr)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun tradeById(@PathVariable id: Long): Optional<Trades> {
        return tradeRepo.findById(id)
    }

    @PostMapping("/consume")
    fun kafka(@RequestBody createTradeSample: CreateTradeSample): ResponseEntity<Any>{

        val trades = Trades(id = 0, orderId =createTradeSample.orderId, exchangeOrderId = createTradeSample.exchangeOrderId,
            exchangeOrderTime = createTradeSample.exchangeOrderTime, price=createTradeSample.price,
            quantity = createTradeSample.quantity, createdOn = LocalDateTime.now(),  createdBy = createTradeSample.createdBy)

        log.info("Data Consume for orderId", createTradeSample.orderId)
        tradeService.orderTradeConsumer(trades)
        log.info("Data Saving in DataBase!")

        return ResponseEntity(tradeService.createTrade(createTradeSample), HttpStatus.OK)
    }
}