package com.industri.transportqa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE ticketCode = :ticketCode")
    fun getTicketByCode(ticketCode: String): TicketEntity?
}
