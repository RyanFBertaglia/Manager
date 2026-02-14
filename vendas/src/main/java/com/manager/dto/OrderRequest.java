package com.manager.dto;

import java.sql.Date;
import java.util.List;

public record OrderRequest(String customerId, int employeeId, Date orderDate, Date requiredDate, Date shippedDate,
                           int shipVia, double freight, String shipName, String shipAddress, String shipCity,
                           String shipRegion, String shipPostalCode, String shipCountry, OrderDetailsRequestDTO orderDetails) {
}
