package service;

import model.Status;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;
    private ArrayList<Integer> allId = new ArrayList<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
    public void addEpic(Epic epic) {
        Integer id = generateId(epic.getId());
        epic.setId(id);
        epics.put(epic.getId(), epic);

    }

    @Override
    //Метод для создания типа задачи с названием и описанием
    public void addTask(Task task) {
        Integer id = generateId(task.getId());
        task.setId(id);
        tasks.put(task.getId(), task);
    }

    @Override
    //Метод для создания типа подзадачи с названием и описанием
    public void addSubtask(Subtask subtask) {
        Integer id = generateId(subtask.getId());
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
        if (epics.get(subtask.getParentId()) == null) throw new Error("Такого эпика нет.");
        epics.get(subtask.getParentId()).setSubtaskIdList(subtask.getId());
        updateEpicStatus(subtask.getParentId());

    }

    @Override
    //Метод для обновления задачи с названием и описанием
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    //Метод для обновления подзадачи с названием и описанием
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getParentId());
    }

    private Integer generateId(Integer extendId) {
        Integer id = -1;
        if (extendId != null && extendId > 0) id = extendId;
        if (allId.contains(id)) throw new Error("Задача с таким id уже существует");
        if (id > 0) return id;
        int last = 0;
        for (Integer newId : allId) {
            if (newId > last) {
                last = newId;
            }
        }
        allId.add(++last);
        return ++last;
    }

    @Override
    //Метод "Получения по идентификатору"
    public Task getById(Integer id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);

        } else if (subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
            return subtasks.get(id);

        } else {
            historyManager.add(tasks.get(id));
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

    @Override
    //Метод "Получение списка всех задач"
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    //Метод "Получение списка всех эпиков"
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    //Метод "Получение списка всех подзадач"
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
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

    @Override
    //Метод удаления всех эпиков
    public void removeAllEpics() {
        epics.clear();
        removeAllSubtask();
    }

    @Override
    //Метод "Удаление всех задач"
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    //Метод "Удаление всех подзадач"
    public void removeAllSubtask() {
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtaskIdList();
        }
        subtasks.clear();
    }

    @Override
    //Метод удаления эпика и всех привязанных подзадач
    public void removeEpic(Integer epicId) {
        Epic epic = epics.remove(epicId);
        for (Integer subtaskId : epic.getSubtaskIdList()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    //Метод удаления конкретной задачи и всех подзадач
    public void removeTask(Integer taskId) {
        tasks.remove(taskId);
    }

    @Override
    //Метод удаления конкретной сабтаски
    public void removeSubtask(Integer subtaskId) {
        if (subtasks.get(subtaskId) != null) {
            epics.get(subtasks.get(subtaskId).getParentId()).deleteSubtaskIdList(subtaskId);
        }
        updateEpicStatus(subtasks.get(subtaskId).getParentId());
        subtasks.remove(subtaskId);
    }

    @Override
    //Метод получения истории просмотров
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
