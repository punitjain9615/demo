package com.example.demo.controller

import com.example.demo.data.CreateTradeSample
import com.example.demo.model.Trades
import com.example.demo.repo.TradeRepo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.example.demo.service.TradeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
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
    @ResponseStatus(HttpStatus.OK)
    fun send(@RequestBody trade: CreateTradeSample): CreateTradeSample{
        var createTradeSample= CreateTradeSample(trade.orderId, trade.exchangeOrderId, trade.price,
            trade.quantity, trade.exchangeOrderTime, trade.createdBy)
        tradeService.createTrade(createTradeSample)



        log.info(trade.orderId)
        return trade
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun findAll()= tradeService.showTrade()

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
}