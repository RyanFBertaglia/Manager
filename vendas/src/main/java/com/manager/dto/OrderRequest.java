package com.manager.dto;

import java.sql.Date;

public record OrderRequest(String customerId, int employeeId, Date orderDate, Date requiredDate, Date shippedDate,
                           int shipVia, double freight, String shipName, String shipAddress, String shipCity,
                           String shipRegion, String shipPostalCode, String shipCountry) {
}
