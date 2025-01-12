package com.lia.alp_vp_2.model

import java.util.Date

data class ReservationModel(
    val parkingLotId: Int,
    val licensePlate: String = "",
    val checkInAt: Date,
    val checkOutAt: Date
)