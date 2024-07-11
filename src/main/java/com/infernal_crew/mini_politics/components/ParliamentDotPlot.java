package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.party.Party;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class ParliamentDotPlot extends Pane {
    private int totalSeats;
    private int inRowSpacing;
    private int betweenRowsSpacing;
    private int centerX;
    private int centerY;
    private int initialRadius;
    private int aislesNumber;
    private Consumer<Party> onSeatHover;

    private record DotInfo(double angle, double x, double y) implements Comparable<DotInfo> {
        @Override
        public int compareTo(DotInfo other) {
            return Double.compare(this.angle, other.angle);
        }
    }

    public ParliamentDotPlot(int totalSeats, int inRowSpacing, int betweenRowsSpacing, int centerX, int centerY, int initialRadius, int aislesNumber) {
        this.totalSeats = totalSeats;
        this.inRowSpacing = inRowSpacing;
        this.betweenRowsSpacing = betweenRowsSpacing;
        this.centerX = centerX;
        this.centerY = centerY;
        this.initialRadius = initialRadius;
        this.aislesNumber = aislesNumber;
    }

    public void setOnSeatHover(Consumer<Party> onSeatHover) {
        this.onSeatHover = onSeatHover;
    }

    public void updatePlot(List<Party> parties) {
        getChildren().clear();

        List<DotInfo> dotInfos = createParliamentDots();
        dotInfos = dotInfos.stream().sorted().collect(Collectors.toList());
        assignSeats(dotInfos, parties);
    }

    private List<DotInfo> createParliamentDots() {
        List<DotInfo> dotInfos = new ArrayList<>();
        int seatsLeft = totalSeats;
        int row = 0;
        while (seatsLeft > 0) {
            int radius = initialRadius + row * betweenRowsSpacing;
            double circumference = Math.PI * radius;
            double aisleSpacing = Math.PI / (aislesNumber + 1);

            int seatsInThisRow = (int) (circumference / inRowSpacing);
            double angleStep = Math.PI / seatsInThisRow;

            for (int seat = 0; seat <= seatsInThisRow; seat++) {
                double angle = seat * angleStep;
                // Skip seats to create concentric spaces (aisles)
                double aisle = round(angle / aisleSpacing);
                double aisleDistance = abs(angle - aisle * aisleSpacing);
                if (aisle > 0 && aisle <= aislesNumber && aisleDistance < 0.07 && row > 0) continue;

                double x = centerX + radius * cos(angle);
                double y = centerY - radius * sin(angle);

                dotInfos.add(new DotInfo(angle, x, y));
                seatsLeft--;
            }

            if (seatsLeft <= 0) break;

            row++;
        }

        return dotInfos;
    }

    private void assignSeats(List<DotInfo> dotInfos, List<Party> parties) {
        int partyIndex = 0;
        int seatsLeft = parties.get(partyIndex).seats();
        Color currentColor = parties.get(partyIndex).color();

        for (DotInfo dotInfo : dotInfos) {
            if (seatsLeft == 0) {
                partyIndex++;
                if (partyIndex >= parties.size()) {
                    seatsLeft = totalSeats;
                    currentColor = Color.GRAY;
                } else {
                    seatsLeft = parties.get(partyIndex).seats();
                    currentColor = parties.get(partyIndex).color();
                }
            }
            Circle circle = new Circle(dotInfo.x, dotInfo.y, 5, currentColor);
            if(partyIndex < parties.size()) {
                Party party = parties.get(partyIndex);

                circle.setOnMouseEntered(event -> {
                    if (onSeatHover != null) {
                        onSeatHover.accept(party);
                    }
                });
            }
            getChildren().add(circle);
            seatsLeft--;
        }
    }
}
