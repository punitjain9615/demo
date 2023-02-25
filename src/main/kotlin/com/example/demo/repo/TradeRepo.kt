package com.example.demo.repo

import com.example.demo.model.Trades
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeRepo: JpaRepository<Trades, Long>, PagingAndSortingRepository<Trades, Long> {

}