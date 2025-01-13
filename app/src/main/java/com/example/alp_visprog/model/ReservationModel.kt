package com.example.alp_visprog.model

import java.util.Date

data class ReservationModel(
    val reservationId: Int,                 // Maps to reservation_id (PK)
    val userId: Int,                        // Maps to user_id (FK -> Users)
    val lotId: Int,                         // Maps to lot_id (FK -> ParkingLots)
    val qrId: Int?,                         // Maps to qr_id (FK -> QrCodes), nullable
    val reservationStatus: String,          // Maps to reservation_status
    val checkInAt: Date,                    // Maps to in_at
    val checkOutAt: Date?,                  // Maps to out_at
    val parkingLot: ParkingLotModel?,       // Maps to related ParkingLots data
    val qrCode: QrCodeModel?                // Maps to related QrCodes data
)

//data class ParkingLotModel(
//    val lotId: Int,                         // Maps to lot_id (PK)
//    val lotFloor: Int,                      // Maps to lot_floor
//    val lotNumber: String,                  // Maps to lot_number
//    val lotStatus: String                   // Maps to lot_status
//)

data class QrCodeModel(
    val qrId: Int,                          // Maps to qr_id (PK)
    val qrIn: String,                       // Maps to qr_in
    val qrOut: String                       // Maps to qr_out
)

data class ReservationResponse(
    val isActive: Boolean,                  // Indicates if the reservation is active
    val spot: String?,                      // Maps to a readable parking spot name or code
    val reservationId: Int,                 // Maps to reservation_id (PK)
    val licensePlate: String?               // Optional for convenience
)