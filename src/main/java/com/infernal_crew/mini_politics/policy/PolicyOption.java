package com.infernal_crew.mini_politics.policy;

public class PolicyOption {
    private String name;

    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
