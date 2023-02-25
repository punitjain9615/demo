package com.example.demo.model

import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Trades")
data class Trades(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    
    @Column(name = "orderId")
    var orderId: String,
    
    @Column(name = "exchangeOrderId")
    var exchangeOrderId: String,
    
    @Column(name = "exchangeOrderTime")
    var exchangeOrderTime: String,
    
    @Column(name = "price")
    var price: Double,
    
    @Column(name = "quantity")
    var quantity: Double,
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "created_on")
    var createdOn: LocalDateTime?,
    
    @Column(name = "createdBy")
    var createdBy: String
){
    init{
        createdOn = LocalDateTime.now()
    }
    constructor() : this( 0, "", "","",0.0,0.0, LocalDateTime.now(), "")
}