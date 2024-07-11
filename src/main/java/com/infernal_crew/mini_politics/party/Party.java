package com.infernal_crew.mini_politics.party;

import javafx.scene.paint.Color;

import java.util.List;

public record Party(String id, String name, String description, List<Ideology> ideologies, Color color, int seats) {
}
