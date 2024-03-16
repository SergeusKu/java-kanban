import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Manager manager;
    static Scanner scanner;
    public static void main(String[] args) {
        manager = new Manager();
        scanner = new Scanner(System.in);
        Integer command = null;
        while (true) {
            printMenu();
            command = scanner.nextInt();

            switch (command) {
                case 1:
                    scanner.nextLine();
                    addNewTask();
                    break;
                case 2:
                    scanner.nextLine();
                    if (manager.checkTaskListIsNull()) {
                        System.out.println("Список задач пуст!");
                    } else {
                        changeStatus();
                    }
                    break;
                case 3:
                    scanner.nextLine();
                    if (manager.checkTaskListIsNull()) {
                        System.out.println("Список задач пуст!");
                    } else {
                        changeTaskLinks();
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    if (manager.checkTaskListIsNull()) {
                        System.out.println("Список задач пуст!");
                    } else {
                        System.out.println(manager.getTaskList());
                    }
                    break;
                case 5:
                    scanner.nextLine();
                    getLinksById();
                    break;
                case 6:
                    scanner.nextLine();
                    if (manager.checkTaskListIsNull()) {
                        System.out.println("Список задач пуст!");
                    } else {
                        removeAllTasks();
                    }
                    break;
                case 7:
                    scanner.nextLine();
                    if (manager.checkTaskListIsNull()) {
                        System.out.println("Список задач пуст!");
                    } else {
                        removeTask();
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Нет такой команды!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Создать задачу");
        System.out.println("2 - Изменить статус задачи");
        System.out.println("3 - Связать задачу");
        System.out.println("4 - Просмотр всех задач");
        System.out.println("5 - Вывести связанные задачи к выбранной");
        System.out.println("6 - Удалить все задачи");
        System.out.println("7 - Удалить выбранную задачу");
        System.out.println("0 - Выход");
    }

    private static void addNewTask() {
        TaskType type = askTaskType();
        System.out.println("Введите название задачи: ");
        String taskName = scanner.nextLine();
        System.out.println("Введите описание задачи: ");
        String taskDescription = scanner.nextLine();
        manager.addTask(type, taskName, taskDescription);
        System.out.println("Задача создана!");
    }

    private static TaskType askTaskType() {
        TaskType type = null;
        String taskType = null;
        while (true) {
            System.out.println("Выберите тип задачи: ");
            getAllTaskType();
            taskType = scanner.nextLine();
            switch (taskType) {
                case "TASK":
                    type = TaskType.TASK;
                    return type;
                case "EPIC":
                    type = TaskType.EPIC;
                    return type;
                case "SUBTASK":
                    type = TaskType.SUBTASK;
                    return type;
                default:
                    System.out.println("Не корректно введен тип задачи.");
            }
        }
    }
    private static void getAllTaskType() {
        for (TaskType task : TaskType.values()){
            System.out.println(task);
        }
    }

    private static void changeStatus() {
        while (true) {
            System.out.println("Выберите id задачи: ");
            manager.getTaskList();
            Integer taskId = scanner.nextInt();
            //Требуется проверить что номер введенной задачи есть среди созданных
            if (manager.checkTaskListIsNull()) {
                System.out.println("Список задач пуст!");
                break;

            } else if (taskId >= 0 && taskId < manager.tasks.size()){
                Status newStatus = askNewStatus(taskId);
                if (newStatus == null) {
                    break;
                }

                manager.changeStatus(taskId, newStatus);
                System.out.println("Статус заявки id " + taskId + " изменен на " + newStatus);
                break;

            } else {
                System.out.println("Нет такого номера задачи!");
            }
        }
    }
    private static void getAllStatusList(ArrayList statuses) {
        for (Object status : statuses){
            System.out.println(status);
        }
    }
    private static Status askNewStatus(Integer taskId) {
        ArrayList statuses = new ArrayList(manager.getAvailableStatuses(taskId));
        String newStatus = null;
        if (statuses.isEmpty()) {
            System.out.println("Нельзя изменить статус заявки");
            return null;
        } else {
            while (true) {
                System.out.println("Выберите один из возможных статусов:");
                getAllStatusList(statuses);
                newStatus = scanner.next();
                switch (newStatus) {
                    case "NEW":
                        return Status.NEW;
                    case "IN_PROGRESS":
                        return Status.IN_PROGRESS;
                    case "DONE":
                        return Status.DONE;
                    default:
                        System.out.println("Не корректный новый статус задачи.");
                }
            }
        }
    }


    private static void getLinksById() {
        Integer taskId = null;
        if (manager.checkTaskListIsNull()) {
            System.out.println("Список задач пуст!");
            return;
        }
        while (true) {
            System.out.println("Выберите id задачи: ");
            manager.getTaskList();
            taskId = scanner.nextInt();
            if (taskId >= 0 && taskId < manager.tasks.size()) {
                System.out.println(manager.getLinks(taskId));
                break;
            } else {
                System.out.println("Нет такого номера задачи!");
            }
        }
    }
    private static void changeTaskLinks() {
        Integer taskId = null;
        Integer linkId = null;
        while (true) {
            System.out.println("Выберите id задачи, к которой нужно добавить дочернюю: ");
            manager.getTaskList();
            taskId = scanner.nextInt();
            System.out.println("Введите id задачи, которую нужно связать: ");
            manager.getTaskList();
            linkId = scanner.nextInt();
            if (taskId >= 0 && taskId < manager.tasks.size() && linkId >= 0 && linkId < manager.tasks.size()) {
                if(checkIdForLinks(taskId, linkId)) {
                    manager.addTaskLink(taskId, linkId);
                    break;
                } else {
                    System.out.println("Нельзя связать выбранные типы задач!");
                }
            } else {
                System.out.println("Нет такого номера задачи!");
            }
        }
        System.out.println("Добавлена в " + taskId + " ссылку на задачу " + linkId + " ");
    }
    private static Boolean checkIdForLinks(Integer taskId, Integer linkId) {
        if (manager.tasks.get(taskId).getType().name().equals("EPIC") && manager.tasks.get(linkId).getType().name().equals("TASK")) {
            return true;
        } else if (manager.tasks.get(taskId).getType().name().equals("TASK") && manager.tasks.get(linkId).getType().name().equals("SUBTASK")) {
            return true;
        } else {
            return false;
        }
    }

    private static void removeAllTasks() {
        manager.removeAllTasks();
    }

    private static void removeTask() {
        while (true) {
            System.out.println("Выберите id задачи: ");
            manager.getTaskList();
            Integer taskId = scanner.nextInt();
            //Требуется проверить что номер введенной задачи есть среди созданных
            if (taskId >= 0 && taskId < manager.tasks.size()){
                manager.removeTask(taskId);
                break;

            } else {
                System.out.println("Нет такого номера задачи!");
            }
        }
        System.out.println("Задача удалена!");
    }

}
