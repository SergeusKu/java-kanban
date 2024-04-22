package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public void addEpic(Epic epic);

    //Метод для создания типа задачи с названием и описанием
    public void addTask(Task task);

    //Метод для создания типа подзадачи с названием и описанием
    public void addSubtask(Subtask subtask);

    //Метод для обновления задачи с названием и описанием
    public void updateTask(Task task);

    //Метод для обновления подзадачи с названием и описанием
    public void updateSubtask(Subtask subtask);

    //Метод "Получения по идентификатору"
    public <T> T getById(Integer id);

    //Метод "Получение списка всех задач"
    public ArrayList<Task> getAllTasks();

    //Метод "Получение списка всех эпиков"
    public ArrayList<Epic> getAllEpics();

    //Метод "Получение списка всех подзадач"
    public ArrayList<Subtask> getAllSubtasks();

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId);


    //Метод удаления всех эпиков
    public void removeAllEpics();

    //Метод "Удаление всех задач"
    public void removeAllTask();

    //Метод "Удаление всех подзадач"
    public void removeAllSubtask();

    //Метод удаления эпика и всех привязанных подзадач
    public void removeEpic(Integer epicId);

    //Метод удаления конкретной задачи и всех подзадач
    public void removeTask(Integer taskId);

    //Метод удаления конкретной сабтаски
    public void removeSubtask(Integer subtaskId);
    //Метод получения истории просмотров
    public List<Task> getHistory();

}
