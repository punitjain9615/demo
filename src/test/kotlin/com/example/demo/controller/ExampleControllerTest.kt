package com.example.demo.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
internal class ExampleControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc
    @Test
    fun `should check a list of trades `(){
        mockMvc.get("/api/trade/all")
            .andDo { print() }
            .andExpect { status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].id"){
                    value("1")
                }
                jsonPath("$[0].orderId"){
                    value("1")
                }
                jsonPath("$[0].quantity"){
                    value("100.1")
                }
            }
    }

    @Test
    fun `should check the `{

    }
}