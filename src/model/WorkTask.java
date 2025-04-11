package model;

import java.io.Serializable;
import java.time.LocalDate;

public class WorkTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    public WorkTask(String title, LocalDate dueDate, int priority) {
        super(title, dueDate, priority);
    }

    @Override
    public String getCategory() {
        return "Work";
    }
}
