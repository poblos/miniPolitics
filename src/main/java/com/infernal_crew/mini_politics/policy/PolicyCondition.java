package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.event.Condition;

public record PolicyCondition(int id, int option) implements Condition {

}
