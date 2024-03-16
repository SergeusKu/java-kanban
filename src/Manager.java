import java.util.ArrayList;
public class Manager {
    ArrayList<Task> tasks;
    Manager(){
        tasks = new ArrayList<>();
    }

    public void addTask(TaskType type, String name, String description) {
        //Выполняем заведение типа задачи
        if (type.name().equals("EPIC")) {
            Epic epic = new Epic(name, description);
            tasks.add(epic);
        } else if (type.name().equals("TASK")) {
            Task task = new Task(name, description);
            tasks.add(task);
        } else {
            Subtask subtask = new Subtask(name, description);
            tasks.add(subtask);
        }
    }

    public String getTaskList() {
        String printer = "[";
        for (Task task : tasks){
            printer += "id='" + tasks.indexOf(task) + '\'' +
                    "[" + task.toString() + "]\n";
        }
        printer += "]";
        return printer;
    }

    public ArrayList<Status> getAvailableStatuses(Integer taskId) {
        Task task = tasks.get(taskId);

        ArrayList nextStatusList = new ArrayList();
        //Требуется проверить что переход в новый статус легитимный
        for (Status status : Status.values()) {
            if (task.getStatus().getSequence() < status.getSequence()) {
                nextStatusList.add(status.name());
                if (status.getIsFinal().equals(true)) {
                    break;
                }
            }
        }

        return nextStatusList;
    }

    public void changeStatus(Integer taskId, Status newStatus) {
        ArrayList <Task> allParentTask = new ArrayList<>();
        ArrayList <Task> allChildTask = new ArrayList<>();
        if (!isNullOrEmpty(getAllParentId(taskId))){
            allParentTask.addAll(getAllParentId(taskId));
        }
        if (!isNullOrEmpty(getAllChild(taskId))){
            allChildTask.addAll(getAllChild(taskId));
        }
        tasks.get(taskId).setStatus(newStatus);

        if (tasks.get(taskId).getType().name().equals("EPIC")
                && newStatus.equals(Status.DONE)) {
            if (!isNullOrEmpty(allChildTask)) {
                for (Task task : allChildTask) {
                    tasks.get(tasks.indexOf(task)).setStatus(newStatus);
                }
            }
        } else if ((tasks.get(taskId).getType().name().equals("TASK")
                || tasks.get(taskId).getType().name().equals("SUBTASK")) && newStatus.equals(Status.DONE)){
            if (!isNullOrEmpty(allChildTask)) {
                for (Task task : allChildTask) {
                    tasks.get(tasks.indexOf(task)).setStatus(newStatus);
                }
            }
            boolean isAllTasksStatusDone = true;
            ArrayList <Task> tasksForCheck = new ArrayList<>();
            for (Task task : allParentTask) {
                if (task.getType().equals(TaskType.EPIC)){
                    tasksForCheck.add(task);
                }
            }
            if (!isNullOrEmpty(tasksForCheck)){
                for (Task parentTask : tasksForCheck) {
                    ArrayList <Task> newChildList = new ArrayList<>();
                    newChildList = getAllChild(tasks.indexOf(parentTask));
                    if (!isNullOrEmpty(newChildList)){
                        for (Task task : newChildList){
                            if (!task.getStatus().equals(Status.DONE)){
                                isAllTasksStatusDone = false;
                            }
                        }
                    }
                }
                if (isAllTasksStatusDone) {
                    for (Task task : allParentTask) {
                        tasks.get(tasks.indexOf(task)).setStatus(Status.DONE);
                    }
                }
            }

        } else if ((tasks.get(taskId).getType().name().equals("SUBTASK")
                || tasks.get(taskId).getType().name().equals("TASK")) && newStatus.equals(Status.IN_PROGRESS)){
            if (isNullOrEmpty(allParentTask)) {
                return;
            }
            for (Task task : allParentTask) {
                tasks.get(tasks.indexOf(task)).setStatus(Status.IN_PROGRESS);
            }
        }
    }
    private ArrayList<Task> getAllParentId(Integer taskId) {
        ArrayList<Task> previouslyParentList = new ArrayList<>(findParentByLinkId(taskId));
        ArrayList<Task> allParentList = new ArrayList<>(previouslyParentList);
        if (isNullOrEmpty(previouslyParentList)) return null;
        for (Task task : previouslyParentList) {
            allParentList.addAll(findParentByLinkId(tasks.indexOf(task)));
        }

        return allParentList;
    }
    private ArrayList<Task> findParentByLinkId(Integer taskId) {
        ArrayList<Task> parentList = new ArrayList<>();
        for (Task task : tasks) {
            if (!isNullOrEmpty(task.getLinks())) {
                for (Integer link : task.getLinks()) {
                    if (link.equals(taskId)) {
                        parentList.add(task);
                    }
                }
            }
        }
        return parentList;
    }
    private ArrayList<Task> getAllChild(Integer taskId) {
        Task task = tasks.get(taskId);
        ArrayList<Task> allChildIdList = new ArrayList<>();
        allChildIdList.addAll(getChildTasks(taskId));
        if (!isNullOrEmpty(task.getLinks())){
            for (Integer link : task.getLinks()) {
                if (!isNullOrEmpty(getChildTasks(link))){
                    allChildIdList.addAll(getChildTasks(link));
                }
            }
        }
        return allChildIdList;
    }
    private ArrayList<Task> getChildTasks(Integer taskId) {
        Task task = tasks.get(taskId);
        ArrayList<Task> childIdList = new ArrayList<>();
        if (!isNullOrEmpty(task.getLinks())) {
            for (Integer linkId : task.getLinks()) {
                childIdList.add(tasks.get(linkId));
            }
        }
        return childIdList;
    }
    public String getLinks(Integer taskId) {
        //
        Task task = tasks.get(taskId);
        String taskMap = task.toString();
        taskMap += "\nLinks{";
        if (tasks.get(taskId).getLinks() != null && (tasks.get(taskId).getType().name().equals("TASK")
                || tasks.get(taskId).getType().name().equals("EPIC"))) {
            taskMap += printLink(taskId);

        } else if (tasks.get(taskId).getType().name().equals("SUBTASK")) {
            return null;
        }
        taskMap += "} \n";
        return taskMap;
    }
    public String printLink(Integer taskId) {
        ArrayList<Integer> links = tasks.get(taskId).getLinks();
        String taskMap = "[";

        for (Integer linkId : links) {
            taskMap += "\n[ id= " + linkId + ": ";
            taskMap += tasks.get(linkId).toString();
            ArrayList<Integer> links2 = tasks.get(linkId).getLinks();
            if (links2 != null) {
                for (Integer link2 : links2) {
                    taskMap += "\n[ id= " + link2 + ": ";
                    taskMap += tasks.get(link2).toString();
                    taskMap += "]";
                }
            }
            taskMap+= "]";
        }

        return taskMap;
    }

    public void addTaskLink(Integer taskId, Integer link) {
        tasks.get(taskId).addLinks(link);
    }

    public boolean checkTaskListIsNull() {
        if (tasks.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public void removeAllTasks() {
        tasks.removeAll(tasks);
    }

    public void removeTask(Integer taskId) {
        tasks.remove(taskId);
    }

    public static boolean isNullOrEmpty(ArrayList object) {
        return object == null || object.isEmpty();
    }
}
