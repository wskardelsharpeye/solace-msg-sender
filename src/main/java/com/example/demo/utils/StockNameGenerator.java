package com.example.demo.utils;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

public class StockNameGenerator {

    private static final String[] STOCK_NAMES = {
            "AAPL", "GOOGL", "AMZN", "FB", "TSLA", "NFLX", "NVDA", "MSFT", "ADBE", "PYPL"
    };

    public static String generateRandomStockName() {
        Random random = new Random();
        int index = random.nextInt(STOCK_NAMES.length);
        return STOCK_NAMES[index];
    }
}