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
        epics.put(epic.getId(), epic);

    }

    //Метод для создания типа задачи с названием и описанием
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    //Метод для создания типа задачи с названием и описанием
    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getParentId()).setSubtaskIdList(subtask.getId());
    }


    public int generateId() {
        return commonId++;
    }

    //Метод позволяет изменить название и описание Задачи/Эпика/Подзадачи
    public void changeNameAndDescription(Integer id, String name, String description) {
        if (epics.containsKey(id)) {
            epics.get(id).setName(name);
            epics.get(id).setDescription(description);

        } else if (subtasks.containsKey(id)) {
            subtasks.get(id).setName(name);
            subtasks.get(id).setDescription(description);

        } else if (tasks.containsKey(id)) {
            tasks.get(id).setName(name);
            tasks.get(id).setDescription(description);

        }
    }

    //Метод "Получения по идентификатору"
    public Object getById(Integer id) {
        if (epics.containsKey(id)) {
            return epics.get(id);

        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);

        } else {
            return tasks.get(id);

        }
    }

    public void updateEpicStatus() {
        for (Integer epicId : epics.keySet()) {
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
    }

    //Метод "Получение списка всех задач"
    public Object getAll() {
        ArrayList<Object> list = new ArrayList<>();
        if (!epics.isEmpty()) {
            for (Integer epicId : epics.keySet()) {
                list.add(epics.get(epicId));
            }
        }
        if (!subtasks.isEmpty()) {
            for (Integer subtaskId : subtasks.keySet()) {
                list.add(subtasks.get(subtaskId));
            }
        }
        if (!tasks.isEmpty()) {
            for (Integer taskId : tasks.keySet()) {
                list.add(tasks.get(taskId));
            }
        }
        return list;
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> subtasksIdList = new ArrayList<>();

        if (!subtasks.isEmpty()) {
            for (Integer subtaskId : epics.get(epicId).getSubtaskIdList()){
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
    }

    //Метод "Удаление всех задач"
    public void removeAllTask() {
        tasks.clear();
    }

    //Метод "Удаление всех подзадач"
    public void removeAllSubtask() {
        subtasks.clear();
    }

    //Метод удаления эпика и всех привязанных подзадач
    public void removeEpic(Integer epicId) {
        if (getSubtasksByEpicId(epicId) != null) {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
            subtaskList.addAll(getSubtasksByEpicId(epicId));
            for (Subtask subtask : subtaskList) {
                removeSubtask(subtask.getId());
            }
        }
        epics.remove(epicId);
    }

    //Метод удаления конкретной задачи и всех подзадач
    public void removeTask(Integer taskId) {
        tasks.remove(taskId);
    }

    //Метод удаления конкретной сабтаски
    public void removeSubtask(Integer subtaskId) {
        if (subtasks.get(subtaskId) != null){
            epics.get(subtasks.get(subtaskId).getParentId()).deleteSubtaskIdList(subtaskId);
        }
        subtasks.remove(subtaskId);
    }
}
