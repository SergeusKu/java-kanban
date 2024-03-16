import java.util.ArrayList;
public class Manager {
    //Класс предназначен для хранения информации о списке задач всех типов
    // В качестве списка для хранения задач выбрал ArrayList поскольку нет смысла усложнять реализацию
    // через хранение задач в разных HashMap (А индекс в ArrayList устраивает нас по условиям)
    ArrayList<Task> tasks;

    Manager() {
        tasks = new ArrayList<>();
    }

    //Метод для создания типа задачи с названием и описанием
    public void addTask(TaskType type, String name, String description) {
        //Выполняем создание задачи, в зависимости от типа
        // Можно добавить проверку наличия дубля задачи с тем же типом и наименованием,
        // но пока не хотелось тратить время
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

    // Метод для вывода списка всех задач в текстовом формате
    public String getTaskList() {
        String printer = "[";
        for (Task task : tasks) {
            printer += "id='" + tasks.indexOf(task) + '\'' +
                    "[" + task.toString() + "]\n";
        }
        printer += "]";
        return printer;
    }

    //Метод для определения возможного следующего статуса задачи
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
    //Больше всего ковырялся с этим методом
    //Метод позволяет изменить статус задачи с учетом изменения статуса родительских и дочерних задач
    public void changeStatus(Integer taskId, Status newStatus) {
        ArrayList <Task> allParentTask = new ArrayList<>();
        ArrayList <Task> allChildTask = new ArrayList<>();
        //Заполняем список родительских связанных задач, чтобы переиспользовать дальше только если потребуется
        if (!isNullOrEmpty(getAllParentId(taskId))){
            allParentTask.addAll(getAllParentId(taskId));
        }
        //Заполняем список дочерних задач связанных, чтобы переиспользовать дальше только если потребуется
        if (!isNullOrEmpty(getAllChild(taskId))){
            allChildTask.addAll(getAllChild(taskId));
        }
        tasks.get(taskId).setStatus(newStatus);
        //Если тип задачи Эпик и изменение статуса на Выполнено,
        // то можно изменить статус всех дочерних задач на Выполнено
        if (tasks.get(taskId).getType().name().equals("EPIC")
                && newStatus.equals(Status.DONE)) {
            if (!isNullOrEmpty(allChildTask)) {
                //Выполняем изменение статуса для каждой дочерней задачи
                for (Task task : allChildTask) {
                    tasks.get(tasks.indexOf(task)).setStatus(newStatus);
                }
            }
            //Если тип Задача или Подзадача, а статус Выполнено,
            //то требуется перепроверять все ли дочерние задачи Эпика завершены.
            // Если завершены все, то статус Эпика тоже меняем на Выполнено
        } else if ((tasks.get(taskId).getType().name().equals("TASK")
                || tasks.get(taskId).getType().name().equals("SUBTASK")) && newStatus.equals(Status.DONE)){
            //Вначале меняем статусы всех дочерних задач к закрытой, если требуется
            if (!isNullOrEmpty(allChildTask)) {
                for (Task task : allChildTask) {
                    tasks.get(tasks.indexOf(task)).setStatus(newStatus);
                }
            }
            //Переопределяем список всех дочерних задач к Эпику
            boolean isAllTasksStatusDone = true;
            ArrayList <Task> tasksForCheck = new ArrayList<>();
            for (Task task : allParentTask) {
                if (task.getType().equals(TaskType.EPIC)){
                    tasksForCheck.add(task);
                }
            }
            //Проверяем все дочерние к эпику задачи на предмет их Закрытия.
            // Если хотя бы одна будет не закрыта, то эпик не будет закрыт
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
                //Если все задачи связанные с Эпиком закрыты, то меняем статус на Выполнено
                if (isAllTasksStatusDone) {
                    for (Task task : allParentTask) {
                        tasks.get(tasks.indexOf(task)).setStatus(Status.DONE);
                    }
                }
            }
            //При изменении статуса "В работе" нужно перепроверять родительские задачи,
            // на необходимость актуализации также их статуса
        } else if ((tasks.get(taskId).getType().name().equals("SUBTASK")
                || tasks.get(taskId).getType().name().equals("TASK")) && newStatus.equals(Status.IN_PROGRESS)){
            if (isNullOrEmpty(allParentTask)) {
                return;
            }
            //Отражаем изменение в родительских классах
            for (Task task : allParentTask) {
                tasks.get(tasks.indexOf(task)).setStatus(Status.IN_PROGRESS);
            }
        }
    }
    //Метод позволяет получить весь список родительских Эпиков и Задач в зависимости от ид задачи
    private ArrayList<Task> getAllParentId(Integer taskId) {
        ArrayList<Task> previouslyParentList = new ArrayList<>(findParentByLinkId(taskId));
        ArrayList<Task> allParentList = new ArrayList<>(previouslyParentList);
        if (isNullOrEmpty(previouslyParentList)) return null;
        for (Task task : previouslyParentList) {
            allParentList.addAll(findParentByLinkId(tasks.indexOf(task)));
        }

        return allParentList;
    }
    //Метод позволяет найти список родительских ссылок
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
    //Метод позволяет найти список всех дочерних Задач и Подзадач, по выбранному ид
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
    // Метод находит перечень родительских задач
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
    // Вывод всех связанных дочерних задач к текущему ид, в строковом формате
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
    // Печать ссылок к выбранной задаче
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

    //Изменение ссылки в родительской задачи на дочернюю
    public void addTaskLink(Integer taskId, Integer link) {
        tasks.get(taskId).addLinks(link);
    }

    //Метод для проверки пустого значения в Списке
    public boolean checkTaskListIsNull() {
        if (tasks.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    //Метод удаления всех задач
    public void removeAllTasks() {
        tasks.removeAll(tasks);
    }
    //Метод удаления конкретной задачи
    public void removeTask(Integer taskId) {
        tasks.remove(taskId);
    }
    //Метод проверки списка на null
    public static boolean isNullOrEmpty(ArrayList object) {
        return object == null || object.isEmpty();
    }
}
