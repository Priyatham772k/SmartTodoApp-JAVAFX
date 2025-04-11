package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import manager.TaskIO;
import manager.TaskManager;
import model.*;

import java.time.LocalDate;
import java.util.List;

public class Main extends Application {

    private boolean darkMode = false;
    private Scene scene;

    private TaskManager taskManager = new TaskManager();
    private ListView<String> taskListView = new ListView<>();

    private ProgressBar progressBar = new ProgressBar(0);
    private Button completeButton = new Button("✅ Mark Done");
    private Button deleteButton = new Button("🗑 Delete");
    private Button undoButton = new Button("↩️ Undo");
    private Button countOverdueButton = new Button("📅 Overdue");
    private Button startTimerButton = new Button("⏱ 25-min Timer");
    private Button themeToggleButton = new Button("🌗 Theme");
    private Label timerLabel = new Label("⏱ No timer running");
    private Label statsLabel = new Label("📈 0 of 0 tasks completed");

    private Timeline timer;
    private int secondsRemaining;

    private ComboBox<String> filterBox = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {
        // Input Fields
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due Date");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Work", "Personal", "Study");
        categoryBox.setPromptText("Category");

        ComboBox<Integer> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(1, 2, 3);
        priorityBox.setPromptText("Priority");

        Button addButton = new Button("➕ Add Task");
        Button saveButton = new Button("💾 Save");
        Button loadButton = new Button("📂 Load");

        // Assign button IDs for CSS styling
        addButton.setId("add-btn");
        saveButton.setId("save-btn");
        loadButton.setId("load-btn");
        deleteButton.setId("delete-btn");
        undoButton.setId("undo-btn");
        countOverdueButton.setId("overdue-btn");
        completeButton.setId("done-btn");
        startTimerButton.setId("timer-btn");
        themeToggleButton.setId("theme-btn");

        // Filter setup
        filterBox.getItems().addAll("All", "Work", "Personal", "Study");
        filterBox.setValue("All");
        filterBox.setOnAction(_ -> refreshTaskList());

        // Add Task
        addButton.setOnAction(_ -> {
            String title = titleField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            String category = categoryBox.getValue();
            Integer priority = priorityBox.getValue();

            if (title != null && dueDate != null && category != null && priority != null) {
                Task task = switch (category) {
                    case "Work" -> new WorkTask(title, dueDate, priority);
                    case "Personal" -> new PersonalTask(title, dueDate, priority);
                    case "Study" -> new StudyTask(title, dueDate, priority);
                    default -> null;
                };
                if (task != null) {
                    boolean added = taskManager.addTask(task);
                    if (!added) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "⚠️ Task with this title already exists!", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    refreshTaskList();
                    updateProgress();
                    titleField.clear();
                    dueDatePicker.setValue(null);
                    categoryBox.setValue(null);
                    priorityBox.setValue(null);
                }
            }
        });

        // Mark as Complete
        completeButton.setOnAction(_ -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task selectedTask = taskManager.getAllTasks().get(selectedIndex);
                selectedTask.markCompleted();
                refreshTaskList();
                updateProgress();
            }
        });

        // Delete
        deleteButton.setOnAction(_ -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task selectedTask = taskManager.getAllTasks().get(selectedIndex);
                taskManager.removeTask(selectedTask);
                refreshTaskList();
                updateProgress();
                System.out.println("🗑 Deleted task: " + selectedTask);
            }
        });

        // Undo Delete
        undoButton.setOnAction(_ -> {
            Task restored = taskManager.undoLastDelete();
            if (restored != null) {
                refreshTaskList();
                updateProgress();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "✅ Restored: " + restored.getTitle());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "⚠️ No task to undo.");
                alert.showAndWait();
            }
        });

        // Count Overdue Tasks (Recursion)
        countOverdueButton.setOnAction(_ -> {
            int overdue = taskManager.countOverdueRecursive(taskManager.getAllTasks(), 0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "📅 Overdue tasks: " + overdue);
            alert.showAndWait();
        });

        // Save
        saveButton.setOnAction(_ -> {
            TaskIO.saveTasksToFile(taskManager.getAllTasks(), "tasks.dat");
        });

        // Load
        loadButton.setOnAction(_ -> {
            List<Task> loaded = TaskIO.loadTasksFromFile("tasks.dat");
            if (loaded != null) {
                taskManager = new TaskManager();
                for (Task task : loaded) {
                    taskManager.addTask(task);
                }
                refreshTaskList();
                updateProgress();
                System.out.println("✅ Loaded " + loaded.size() + " tasks and refreshed UI.");
            }
        });

        // Start Timer
        startTimerButton.setOnAction(_ -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                startCountdown(25 * 60);
            }
        });

        // Theme Toggle
        themeToggleButton.setOnAction(_ -> {
            darkMode = !darkMode;
            scene.getStylesheets().clear();
            if (darkMode) {
                scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
                System.out.println("🌙 Dark mode activated");
            } else {
                scene.getStylesheets().add(getClass().getResource("/light-theme.css").toExternalForm());
                System.out.println("☀️ Light mode activated");
            }
        });

        // Layouts
        HBox inputRow = new HBox(10, titleField, dueDatePicker, categoryBox, priorityBox);
        HBox controlRow = new HBox(10, addButton, saveButton, loadButton, deleteButton, undoButton);
        HBox optionsRow = new HBox(10, new Label("🔍 Filter:"), filterBox, themeToggleButton, countOverdueButton);
        inputRow.setPadding(new Insets(10));
        controlRow.setPadding(new Insets(10));
        optionsRow.setPadding(new Insets(10));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("📝 Smart To-Do List"),
                inputRow,
                controlRow,
                optionsRow,
                taskListView,
                completeButton,
                progressBar,
                statsLabel,
                startTimerButton,
                timerLabel
        );

        scene = new Scene(layout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Smart To-Do App");
        primaryStage.show();
    }

    private void refreshTaskList() {
        taskListView.getItems().clear();
        String selectedFilter = filterBox.getValue();
        for (Task task : taskManager.getAllTasks()) {
            boolean matches = selectedFilter.equals("All") || task.getCategory().equals(selectedFilter);
            if (matches) {
                String display = task.toString();
                if (task.isCompleted()) {
                    display += " ✅";
                }
                taskListView.getItems().add(display);
            }
        }
    }

    private void updateProgress() {
        int total = taskManager.getTotalTaskCount();
        int completed = taskManager.getCompletedCount();

        if (total == 0) {
            progressBar.setProgress(0);
            statsLabel.setText("📈 0 of 0 tasks completed");
        } else {
            progressBar.setProgress((double) completed / total);
            statsLabel.setText("📈 " + completed + " of " + total + " tasks completed");
        }
    }

    private void startCountdown(int seconds) {
        secondsRemaining = seconds;

        if (timer != null) {
            timer.stop();
        }

        timer = new Timeline(new KeyFrame(
                Duration.seconds(1),
                _ -> {
                    if (secondsRemaining <= 0) {
                        timer.stop();
                        timerLabel.setText("✅ Time's up!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "🎉 Time's up! Take a break.");
                        alert.showAndWait();
                    } else {
                        int minutes = secondsRemaining / 60;
                        int secs = secondsRemaining % 60;
                        timerLabel.setText(String.format("⏳ %02d:%02d remaining", minutes, secs));
                        secondsRemaining--;
                    }
                }
        ));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
