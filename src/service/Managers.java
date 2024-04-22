package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Managers implements TaskManager, HistoryManager{
    private final TaskManager manager;
    private final HistoryManager history;

    public Managers(TaskManager manager, HistoryManager history){
        this.manager = manager;
        this.history = history;
    }

    public HistoryManager getDefaultHistory(){
        return this.history;
    }
    public TaskManager getDefault(){
        return this.manager;
    }
    @Override
    public void addEpic(Epic epic) {
        this.manager.addEpic(epic);
    }

    @Override
    public void addTask(Task task) {
        this.manager.addTask(task);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        this.manager.addSubtask(subtask);
    }

    @Override
    public void updateTask(Task task) {
        this.manager.updateTask(task);
    }

    @Override
    public void updateSubtask(Integer id, Subtask subtask) {
        this.manager.updateSubtask(id, subtask);
    }

    @Override
    public Task getById(Integer id) {

        if(this.manager.getById(id) instanceof Epic) history.add(new Epic(this.manager.getById(id)));
        else if (this.manager.getById(id) instanceof Subtask) history.add(new Subtask(this.manager.getById(id)));
        else if(this.manager.getById(id) instanceof Task) history.add(new Task(this.manager.getById(id)));

        return this.manager.getById(id);
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return this.manager.getAllTasks();
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return this.manager.getAllEpics();
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return this.manager.getAllSubtasks();
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        return this.manager.getSubtasksByEpicId(epicId);
    }

    @Override
    public void removeAllEpics() {
        this.manager.removeAllEpics();
    }

    @Override
    public void removeAllTask() {
        this.manager.removeAllTask();
    }

    @Override
    public void removeAllSubtask() {
        this.manager.removeAllSubtask();
    }

    @Override
    public void removeEpic(Integer epicId) {
        this.manager.removeEpic(epicId);
    }

    @Override
    public void removeTask(Integer taskId) {
        this.manager.removeTask(taskId);
    }

    @Override
    public void removeSubtask(Integer subtaskId) {
        this.manager.removeSubtask(subtaskId);
    }

    @Override
    public void add(Task task) {
        this.history.add(task);
    }

    @Override
    public List<?> getHistory() {
        return this.history.getHistory();
    }
}
