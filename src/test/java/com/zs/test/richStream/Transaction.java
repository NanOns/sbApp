package com.zs.test.richStream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

    private final Trader trader;

    private final int year;

    private final int value;

}