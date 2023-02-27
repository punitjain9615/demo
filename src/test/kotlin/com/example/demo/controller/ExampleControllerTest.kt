package com.example.demo.controller

import com.example.demo.model.Trades
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
internal class ExampleControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {

    fun `should check a list of trades `() {
        mockMvc.get("/api/trade/all")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].id") {
                    value("1")
                }
                jsonPath("$[0].orderId") {
                    value("1")
                }
                jsonPath("$[0].quantity") {
                    value("100.1")
                }
            }
    }

    @Test
    fun `should check the get method using id`() {
        var id = 2
        mockMvc.get("/api/trade/$id")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id") {
                    value("2")
                }
            }
    }

    @Test
    fun `should check the paging method`() {
        var page = 1
        var size = 2
        mockMvc.get("/api/trade/page?page=$page&size=$size")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$") {
                    value("1")
                }
            }
    }

    @Test
    fun `should add the trade in DataBase`() {
        var trade = Trades()
        trade.orderId = "100"
        trade.createdBy = "Punit"
        trade.exchangeOrderId = "900"
        trade.price = 100.0
        trade.quantity = 100.0
        trade.exchangeOrderTime="9030303"

        var performPost = mockMvc.post("/api/trade/add") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(trade)
        }
        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
            }
    }
}