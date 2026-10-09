package com.industri.transportqa

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey
    val ticketCode: String,
    val passengerName: String,
    val destination: String
)
