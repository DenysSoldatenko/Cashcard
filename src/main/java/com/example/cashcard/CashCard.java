package com.example.cashcard;

import org.springframework.data.annotation.Id;

/**
 *  A record representing a CashCard entity.
 *
 *  @param id The ID of the CashCard.
 *  @param amount The amount of money in the CashCard.
 *  @param owner The owner of the CashCard.
 */
public record CashCard(@Id Long id, Double amount, String owner) { }
