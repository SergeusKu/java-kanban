package service;

public abstract class Managers implements TaskManager, HistoryManager {
    private final TaskManager manager;
    private final HistoryManager history;

    public Managers(TaskManager manager, HistoryManager history) {
        this.manager = manager;
        this.history = history;
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
