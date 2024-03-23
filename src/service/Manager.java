package service;
import model.Status;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
public class Manager {
    private HashMap<Integer, Task> tasks;
    private HashMap <Integer, Epic> epics;
    private HashMap <Integer, Subtask> subtasks;
    private Integer commonId = 0;

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }
    public void addEpic(String name, String description) {
        Integer epicId = generateId();
        Epic epic = new Epic(epicId, name, description);

        if (epics.containsValue(epic)) {
            System.out.println("Уже есть такой эпик!");
        } else {
            epics.put(epicId, epic);
        }
    }

    //Метод для создания типа задачи с названием и описанием
    public void addTask(String name, String description) {
            Integer taskId = generateId();
            Task task = new Task(taskId, name, description);

            if (tasks.containsValue(task)) {
                System.out.println("Уже есть такая задача!");
            } else {
                tasks.put(taskId, task);
            }
    }

    //Метод для создания типа задачи с названием и описанием
    public void addSubtask(String name, String description, Integer epicId) {
        Integer subtaskId = generateId();
        Subtask subtask = new Subtask(subtaskId, name, description, epicId);

        if (subtasks.containsValue(subtask)) {
            System.out.println("Уже есть такая задача!");
        } else {
            subtasks.put(subtaskId, subtask);
        }
        updateEpicStatus();
    }


    private int generateId() {
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
    //Метод позволяет получить только конкретную Задачу/Эпик/Подзадачу
    public String getById(Integer id) {
        String printer = "";
        if (epics.containsKey(id)) {
            printer += epics.get(id);

        } else if (subtasks.containsKey(id)) {
            printer += subtasks.get(id);

        } else if (tasks.containsKey(id)) {
            printer += tasks.get(id);

        }
        return printer;
    }

    //Метод позволяет изменить статус задачи
    public void changeStatus(Integer id, Status newStatus) {
        if (subtasks.containsKey(id)) {
            subtasks.get(id).setStatus(newStatus);
            updateEpicStatus();

        } else if (tasks.containsKey(id)) {
            tasks.get(id).setStatus(newStatus);
         }

    }
    private void updateEpicStatus() {
        for (Integer epicId : epics.keySet()){
            ArrayList<Status> statuses = new ArrayList<>();

            if (getSubtasksByEpicId(epicId)!=null) {
                ArrayList<Task> subtaskList = new ArrayList<>();
                subtaskList.addAll(getSubtasksByEpicId(epicId));
                for (Task task : subtaskList){
                    statuses.add(task.getStatus());
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
    public String getAll() {
        String printer = "";
        if (!epics.isEmpty()){
            for (Integer epicId : epics.keySet()){
                printer += epics.get(epicId) + "\n";
            }
        }
        if (!subtasks.isEmpty()){
            for (Integer subtaskId : subtasks.keySet()){
                printer += subtasks.get(subtaskId) + "\n";
            }
        }
        if (!tasks.isEmpty()){
            for (Integer taskId : tasks.keySet()){
                printer += tasks.get(taskId) + "\n";
            }
        }
        return printer;
    }
    // Вывод всех связанных дочерних задач к текущему ид, в строковом формате
    public String printEpicLinks(Integer epicId) {
        String printer = "";
        if (!epics.isEmpty()) {
            printer += epics.get(epicId);
            printer += "\nLinks{";

            if (!getSubtasksByEpicId(epicId).isEmpty()) {
                ArrayList<Subtask> subtaskList = new ArrayList<>();
                subtaskList.addAll(getSubtasksByEpicId(epicId));
                for (Subtask subtask : subtaskList) {
                    printer += "\n" + subtask;
                }
            }
            return printer+="}" ;
        }
        return null;
    }

    public ArrayList<Subtask> getSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> subtasksIdList = new ArrayList<>();
        if (!subtasks.isEmpty()){
            for (Integer subtaskId : subtasks.keySet()){
                if (subtasks.get(subtaskId).getParentId() == epicId){
                    subtasksIdList.add(subtasks.get(subtaskId));
                }
            }
            return subtasksIdList;
        } else {
            return null;
        }
    }

    //Метод удаления всех задач
    public void removeAllEpics() {
        ArrayList<Integer> epicsIdToRemove = new ArrayList<>();
        epicsIdToRemove.addAll(epics.keySet());
        for (Integer epicId : epicsIdToRemove){
            removeEpic(epicId);
        }
    }

    //Метод удаления всех задач
    public void removeAllTask() {
        tasks.clear();
    }

    //Метод удаления всех задач
    public void removeAllSubtask() {
        subtasks.clear();
    }
    //Метод удаления эпика и всех привязанных подзадач
    public void removeEpic(Integer epicId) {
        if (getSubtasksByEpicId(epicId)!=null) {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
            subtaskList.addAll(getSubtasksByEpicId(epicId));
            for (Subtask subtask : subtaskList){
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
        subtasks.remove(subtaskId);
        updateEpicStatus();
    }
}
