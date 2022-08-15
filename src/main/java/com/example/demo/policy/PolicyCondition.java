package com.example.demo.policy;

import com.example.demo.event.Condition;

public record PolicyCondition(int id, int option) implements Condition {

}
