# 📝 Smart To-Do App with Productivity Boost

A beautiful JavaFX-based productivity application built for CSYE 6200, featuring intelligent task tracking, category filters, undo history, and a dark/light mode switch. Boost your workflow with a touch of code!

---

## 🚀 Features

- ➕ **Add tasks** with title, due date, category, and priority
- ✅ **Mark tasks as completed** (with a check ✅ badge)
- 🗑 **Delete tasks**, and ↩️ **Undo deletion** with stack history
- 📅 **Recursively count overdue tasks**
- 📂 **Save and 💾 Load tasks from file** (`tasks.dat`)
- 🌗 **Switch between dark and light modes** for UI themes
- ⏱ **Pomodoro timer** for focused work on tasks
- 🔍 **Category Filters** to sort tasks (Work, Personal, Study, etc.)

---

## 🧰 Tech Stack

- Java 17
- JavaFX SDK 21
- Eclipse IDE (or any JavaFX-supported IDE)
- Object Serialization (for file save/load)
- CSS (custom dark/light theme styling)

---

## 🖥️ Installation

1. **Download or clone the repository** to your local machine.
2. **Install JavaFX**: Make sure you have **JavaFX** installed on your system.
   - Download JavaFX from [openjfx.io](https://openjfx.io/).
   - Ensure JavaFX is configured in your IDE or build tool.
3. **Import the project into your IDE**:
   - Import into **Eclipse**, IntelliJ, or similar.
   - Link JavaFX libraries in your project settings.
4. **Run the application**:
   - Open `Main.java` from the `app` package and run it.
   - The app starts with the main task list view.

---

## ⚙️ How to Use

- **Add a Task**: Enter title, date, category, priority → click ➕ Add Task
- **Mark Done**: Select task → click ✅ Mark Done
- **Delete Task**: Select task → click 🗑 Delete
- **Undo Delete**: Click ↩️ Undo to restore a deleted task
- **View Overdue**: Click 📅 Overdue for recursive overdue counter
- **Start Timer**: Select task → click ⏱ 25-min Timer
- **Switch Themes**: Click 🌗 Theme to toggle dark/light mode


---

## 📂 File Structure

- **`src/`** – source code
  - `app/` – UI and main app logic
  - `manager/` – task logic and data handling
  - `model/` – task models (abstract + concrete)
- **`tasks.dat`** – file for saved tasks
- **`dark-theme.css`, `light-theme.css`** – UI themes
- **`README.md`** – this file

---

## 👥 Contributors

Developed by:
- **Nagapriyatham Pindi**
- **Vamsi Gajja**

As part of CSYE 6200 – Concepts of Object-Oriented Design  
Professor: **Jones Yu**

---

## 🔮 Future Enhancements

- Calendar view to visually schedule tasks
- Reminders and task notifications
- Google Calendar sync or iCal export
- Login system for multi-user support
- Mobile-friendly version with JavaFX Mobile or Flutter

---

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).