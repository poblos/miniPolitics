package gui;

import jobs.Person;

import javax.swing.*;

public class JobButton extends JButton {
    private final int id;
    private final Person person;

    public JobButton(int id, Person person) {
        this.id = id;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }
}
