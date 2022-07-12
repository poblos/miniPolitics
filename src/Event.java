import java.util.List;

public class Event {
    private String title;
    private String description;
    private List<Option> options;

    public Event(String title, String description, List<Option> options) {
        this.title = title;
        this.description = description;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return  title + '\n' + description + '\n' + options;
    }
}
