package manager;

import model.Task;

import java.util.*;

public class TaskManager {

    private List<Task> taskList = new ArrayList<>();
    private PriorityQueue<Task> priorityQueue = new PriorityQueue<>();
    private Set<String> taskTitles = new HashSet<>();
    private Stack<Task> deletedTasks = new Stack<>();

    public boolean addTask(Task task) {
        if (taskTitles.contains(task.getTitle())) {
            return false; // prevent duplicate title
        }
        taskList.add(task);
        priorityQueue.offer(task);
        taskTitles.add(task.getTitle());
        return true;
    }

    public void removeTask(Task task) {
        taskList.remove(task);
        priorityQueue.remove(task);
        taskTitles.remove(task.getTitle());
        deletedTasks.push(task);
    }

    public Task undoLastDelete() {
        if (!deletedTasks.isEmpty()) {
            Task task = deletedTasks.pop();
            addTask(task);
            return task;
        }
        return null;
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public int getTotalTaskCount() {
        return taskList.size();
    }

    public int getCompletedCount() {
        int count = 0;
        for (Task task : taskList) {
            if (task.isCompleted()) count++;
        }
        return count;
    }

    public int countOverdueRecursive(List<Task> tasks, int index) {
        if (index >= tasks.size()) return 0;
        Task t = tasks.get(index);
        boolean isOverdue = !t.isCompleted() && t.getDueDate().isBefore(java.time.LocalDate.now());
        return (isOverdue ? 1 : 0) + countOverdueRecursive(tasks, index + 1);
    }
}
