package model;

import java.io.Serializable;
import java.time.LocalDate;

public class StudyTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    public StudyTask(String title, LocalDate dueDate, int priority) {
        super(title, dueDate, priority);
    }

    @Override
    public String getCategory() {
        return "Study";
    }
}
