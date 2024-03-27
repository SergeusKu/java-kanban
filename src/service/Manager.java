package service;

import model.Status;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
    private Integer commonId = 0;

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public void addEpic(Epic epic) {
        Integer id = generateId();
        epic.setId(id);
        epics.put(epic.getId(), epic);

    }

    //Метод для создания типа задачи с названием и описанием
    public void addTask(Task task) {
        Integer id = generateId();
        task.setId(id);
        tasks.put(task.getId(), task);
    }

    //Метод для создания типа подзадачи с названием и описанием
    public void addSubtask(Subtask subtask) {
        Integer id = generateId();
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getParentId()).setSubtaskIdList(subtask.getId());
        updateEpicStatus(subtask.getParentId());
    }

    //Метод для обновления задачи с названием и описанием
    public void updateTask(Integer id, Task task) {
        tasks.put(id, task);
    }

    //Метод для обновления подзадачи с названием и описанием
    public void updateSubtask(Integer id, Subtask subtask) {
        subtasks.put(id, subtask);
        updateEpicStatus(subtask.getParentId());
    }

    private int generateId() {
        return commonId++;
    }

    //Метод "Получения по идентификатору"
    public Task getById(Integer id) {
        if (epics.containsKey(id)) {
            return epics.get(id);

        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);

        } else {
            return tasks.get(id);

        }
    }

    private void updateEpicStatus(Integer epicId) {
        ArrayList<Status> statuses = new ArrayList<>();

        if (getSubtasksByEpicId(epicId) != null) {
            for (Subtask subtask : getSubtasksByEpicId(epicId)) {
                statuses.add(subtask.getStatus());
            }
        }

        if (statuses.stream().allMatch(s -> s.equals(Status.NEW))) {
            epics.get(epicId).setStatus(Status.NEW);
        } else if (statuses.stream().allMatch(s -> s.equals(Status.DONE))) {
            epics.get(epicId).setStatus(Status.DONE);
        } else {
            epics.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

    //Метод "Получение списка всех задач"
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Метод "Получение списка всех эпиков"
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //Метод "Получение списка всех подзадач"
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> subtasksIdList = new ArrayList<>();

        if (!subtasks.isEmpty()) {
            for (Integer subtaskId : epics.get(epicId).getSubtaskIdList()) {
                subtasksIdList.add(subtasks.get(subtaskId));
            }
            return subtasksIdList;
        } else {
            return null;
        }
    }

    //Метод удаления всех эпиков
    public void removeAllEpics() {
        epics.clear();
        removeAllSubtask();
    }

    //Метод "Удаление всех задач"
    public void removeAllTask() {
        tasks.clear();
    }

    //Метод "Удаление всех подзадач"
    public void removeAllSubtask() {
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtaskIdList();
        }
        subtasks.clear();
    }

    //Метод удаления эпика и всех привязанных подзадач
    public void removeEpic(Integer epicId) {
        Epic epic = epics.remove(epicId);
        for (Integer subtaskId : epic.getSubtaskIdList()) {
            subtasks.remove(subtaskId);
        }
    }

    //Метод удаления конкретной задачи и всех подзадач
    public void removeTask(Integer taskId) {
        tasks.remove(taskId);
    }

    //Метод удаления конкретной сабтаски
    public void removeSubtask(Integer subtaskId) {
        if (subtasks.get(subtaskId) != null) {
            epics.get(subtasks.get(subtaskId).getParentId()).deleteSubtaskIdList(subtaskId);
        }
        updateEpicStatus(subtasks.get(subtaskId).getParentId());
        subtasks.remove(subtaskId);
    }
}
