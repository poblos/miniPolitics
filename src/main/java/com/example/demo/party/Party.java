package com.example.demo.party;

import java.util.List;

public record Party(String name, String description, List<Ideology> ideologies) {
}