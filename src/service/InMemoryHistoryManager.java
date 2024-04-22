package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> historyView;
    private static final int NUMBERS_OF_SAVED_VIEWS = 10;

    public InMemoryHistoryManager() {
        historyView = new ArrayList<>();
    }

    @Override
    //Метод для создания типа задачи с названием и описанием
    public void add(Task task) {
        historyView.add(task);
        if (historyView.size()>NUMBERS_OF_SAVED_VIEWS){
            historyView.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory(){
        return historyView;
    }
}
