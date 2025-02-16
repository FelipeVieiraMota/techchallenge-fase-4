package com.motavieirafelipe.order_management_microservice.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record TrackingDto (
    UUID orderId,
    double latitude,
    double longitude,
    LocalDateTime timestamp
){}
