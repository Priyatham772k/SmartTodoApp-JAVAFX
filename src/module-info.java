module SmartToDoApp {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports app;
    exports model;
    exports manager;
}
