package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Task implements Comparable<Task>, Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private LocalDate dueDate;
    private int priority; // 1 = High, 2 = Medium, 3 = Low
    private boolean isCompleted;

    public Task(String title, LocalDate dueDate, int priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    public String getTitle() { return title; }
    public LocalDate getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }

    public void markCompleted() { isCompleted = true; }

    public abstract String getCategory();

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "[" + getCategory() + "] " + title + " - Due: " + dueDate + " - Priority: " + priority;
    }
}
