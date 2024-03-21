package control;
import taskTypes.Status;

import taskTypes.Epic;
import taskTypes.Subtask;
import taskTypes.Task;

import java.util.HashMap;
import java.util.ArrayList;
public class Manager {
    private HashMap<Integer, Task> tasks;
    private HashMap <Integer, Epic> epics;
    private HashMap <Integer, Subtask> subtasks;
    private static Integer commonId = -1;

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
    public void addTask(String name, String description, Integer epicId) {
            Integer taskId = generateId();
            Task task = new Task(taskId, name, description, epicId);

            if (tasks.containsValue(task)) {
                System.out.println("Уже есть такая задача!");
            } else {
                tasks.put(taskId, task);
            }
        updateEpicStatus();
    }

    //Метод для создания типа задачи с названием и описанием
    public void addSubtask(String name, String description, Integer taskId) {
        Integer subtaskId = generateId();
        Subtask subtask = new Subtask(subtaskId, name, description, taskId);

        if (subtasks.containsValue(subtask)) {
            System.out.println("Уже есть такая задача!");
        } else {
            subtasks.put(subtaskId, subtask);
        }
        updateEpicStatus();
    }
    public int generateId() {
        commonId++;
        return commonId ;
    }

    //Больше всего ковырялся с этим методом
    //Метод позволяет изменить статус задачи с учетом изменения статуса родительских и дочерних задач
    public void changeStatus(Integer id, Status newStatus) {
        if (subtasks.containsKey(id)) {
            subtasks.get(id).setStatus(newStatus);
            updateEpicStatus();

        } else if (tasks.containsKey(id)) {
            tasks.get(id).setStatus(newStatus);
            updateEpicStatus();
        }

    }
    public void updateEpicStatus() {
        for (Integer epicId : epics.keySet()){
            ArrayList<Status> statuses = new ArrayList<>();

            if (getTasksByEpicId(epicId)!=null) {
                ArrayList<Task> taskList = new ArrayList<>();
                taskList.addAll(getTasksByEpicId(epicId));
                for (Task task : taskList){
                    statuses.add(task.getStatus());

                    if (getSubtasksByTaskId(task.getId())!=null) {
                        ArrayList<Subtask> subtaskList  = new ArrayList<>();
                        subtaskList.addAll(getSubtasksByTaskId((task.getId())));
                        for (Subtask subtask : subtaskList){
                            statuses.add(subtask.getStatus());
                        }
                    }
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
        if(epics.isEmpty() && tasks.isEmpty() && subtasks.isEmpty()){
            printer += "пусто";
        }
        if (!epics.isEmpty()){
            for (Integer epicId : epics.keySet()){
                printer += epics.get(epicId) + "\n";
            }
        }
        if (!tasks.isEmpty()){
            for (Integer taskId : tasks.keySet()){
                printer += tasks.get(taskId) + "\n";
            }
        }
        if (!subtasks.isEmpty()){
            for (Integer subtaskId : subtasks.keySet()){
                printer += subtasks.get(subtaskId) + "\n";
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

            if (!getTasksByEpicId(epicId).isEmpty()) {
                ArrayList<Task> taskList = new ArrayList<>();
                taskList.addAll(getTasksByEpicId(epicId));
                for (Task task : taskList) {
                    printer += "\n" + tasks.get(task.getId());
                    if (getSubtasksByTaskId(task.getId())!= null){
                        for (Subtask subtask : getSubtasksByTaskId(task.getId())) {
                            printer +="\n" + subtask;
                        }
                    }
                }
            }
            return printer+="}" ;
        }
        return null;
    }

    public ArrayList<Task> getTasksByEpicId(Integer epicId) {
        ArrayList<Task> tasksIdList = new ArrayList<>();
        if (!tasks.isEmpty()){
            for (Integer taskId : tasks.keySet()){
                if (tasks.get(taskId).getParentId() == epicId){
                    tasksIdList.add(tasks.get(taskId));
                }
            }
            return tasksIdList;
        } else {
            return null;
        }
    }

    public ArrayList<Subtask> getSubtasksByTaskId (Integer taskId) {
        ArrayList<Subtask> subtasksIdList = new ArrayList<>();
        if (!subtasks.isEmpty()){
            for (Integer subtaskId : subtasks.keySet()){
                if (subtasks.get(subtaskId).getParentId() == taskId){
                    subtasksIdList.add(subtasks.get(subtaskId));
                }
            }
            return subtasksIdList;
        } else {
            return null;
        }
    }

    //Метод удаления всех задач
    public void removeAll() {
        epics.clear();
        tasks.clear();
        subtasks.clear();
    }
    //Метод удаления эпика и всех привязанных задач и подзадач
    public void removeEpic(Integer epicId) {
        if (getTasksByEpicId(epicId)!=null) {
            ArrayList<Task> taskList = new ArrayList<>();
            taskList.addAll(getTasksByEpicId(epicId));
            for (Task task : taskList){
                removeTask(task.getId());
            }
        }
        epics.remove(epicId);
    }
    //Метод удаления конкретной задачи и всех подзадач
    public void removeTask(Integer taskId) {
        if (getSubtasksByTaskId(taskId)!=null) {
            ArrayList<Subtask> subtaskList  = new ArrayList<>();
            subtaskList.addAll(getSubtasksByTaskId(taskId));
            for (Subtask subtask : subtaskList){
                removeSubtask(subtask.getId());
            }
        }
        tasks.remove(taskId);
        updateEpicStatus();
    }
    //Метод удаления конкретной сабтаски
    public void removeSubtask(Integer subtaskId) {
        subtasks.remove(subtaskId);
        updateEpicStatus();
    }
}
