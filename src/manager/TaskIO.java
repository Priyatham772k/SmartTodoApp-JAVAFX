package manager;

import model.Task;

import java.io.*;
import java.util.List;

public class TaskIO {

	public static void saveTasksToFile(List<Task> tasks, String filename) {
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
	        out.writeObject(tasks);
	        System.out.println("✅ Saved " + tasks.size() + " tasks to file: " + filename);
	    } catch (IOException e) {
	        System.err.println("❌ Save failed:");
	        e.printStackTrace();
	    }
	}


    public static List<Task> loadTasksFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
			List<Task> tasks = (List<Task>) in.readObject();
            System.out.println("📂 Loaded " + tasks.size() + " tasks from file.");
            return tasks;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Load failed:");
            e.printStackTrace();
        }
        return null;
    }

}
