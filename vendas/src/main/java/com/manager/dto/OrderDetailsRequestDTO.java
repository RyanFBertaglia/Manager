package com.manager.dto;

public record OrderDetailsRequestDTO (int productId, int quantity, double unitPrice, double discount) {
}
