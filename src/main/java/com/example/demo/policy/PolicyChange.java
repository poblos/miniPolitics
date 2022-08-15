package com.example.demo.policy;

import com.example.demo.event.Effect;

public record PolicyChange(int id, int option) implements Effect {
}
