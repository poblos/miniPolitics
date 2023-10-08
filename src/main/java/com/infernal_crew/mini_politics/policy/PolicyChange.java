package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.event.Effect;

public record PolicyChange(int id, int option) implements Effect {
}
