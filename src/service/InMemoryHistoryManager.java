package service;

import model.Task;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int NUMBERS_OF_SAVED_VIEWS = 10;
    private LinkedList<Task> historyView;

    public InMemoryHistoryManager() {
        historyView = new LinkedList<>();
    }

    @Override
    //Метод для создания типа задачи с названием и описанием
    public void add(Task task) {
        historyView.add(task);
        if (historyView.size() > NUMBERS_OF_SAVED_VIEWS) {
            historyView.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyView;
    }
}
