package com.example.demo.indicators;


import com.example.demo.event.*;

public record IndicatorChange(Indicator indicator, int change) implements Effect {

    @Override
    public String toString() {
        return indicator + ": " + change;
    }
}
