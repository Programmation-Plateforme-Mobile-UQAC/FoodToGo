package com.example.foodtogoapp.entities

import java.util.Date
import java.util.UUID

class Product(val id: UUID, val user_id: UUID, val category_id: UUID, val title: String, val summary: String, val image: String, val created_at: Date, val updated_at: Date) {

}