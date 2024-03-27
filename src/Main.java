import model.Epic;
import model.Subtask;
import model.Task;
import service.Manager;
import model.Status;

public class Main {
    static Manager manager;

    public static void main(String[] args) {
        // Делаю только заданные тест кейсы.
        manager = new Manager();

        Epic epic_0 = new Epic("Эпик 0", "...");
        manager.addEpic(epic_0);

        Subtask subtask_1 = new Subtask("Подзадача 1", "...", 0);
        manager.addSubtask(subtask_1);

        Subtask subtask_2 = new Subtask("Подзадача 2", "....", 0);
        manager.addSubtask(subtask_2);

        Task task_3 = new Task("Задача 3", "...");
        manager.addTask(task_3);

        Epic epic_4 = new Epic( "Эпик 4", "...");
        manager.addEpic(epic_4);

        Subtask subtask_5 = new Subtask("Подзадача 5", "...", 4);
        manager.addSubtask(subtask_5);

        Task task_6 = new Task("Задача 6", "...");
        manager.addTask(task_6);

        //Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..)
        System.out.println("Список всех задач: ");
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllTasks());
        System.out.println();
        System.out.println("Эпик № 0: ");
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        System.out.println("Меняем название и описание эпика 0");
        epic_0.setName("Эпик № 0 ***");
        epic_0.setDescription("Какое-то описание эпика");


        System.out.println("Меняем название и описание подзадачи 2");
        Subtask subtask_7 = new Subtask(subtask_2.getId(),"Подзадача № 2 ***", "Какое-то описание подзадачи", 0);
        manager.updateSubtask(subtask_2.getId(), subtask_7);

        System.out.println("Меняем название и описание задачи 6");
        Task task_8 = new Task(task_6.getId(),"Задача № 6 *", "Какое-то описание задачи");
        manager.updateTask(task_6.getId(), task_8);

        System.out.println("Получаем конкретный эпик без подзадач");
        System.out.println(manager.getById(0));
        System.out.println("Получаем конкретную подзадачу");
        System.out.println(manager.getById(2));
        System.out.println("Получаем конкретную задачу");
        System.out.println(manager.getById(6));

        //Измените статусы созданных объектов, распечатайте их.
        // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
        System.out.println("Изменил статус подзадачи № 2");
        subtask_2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask_2.getId(),subtask_2);
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        System.out.println("Изменил статус задачи № 6");
        task_6.setStatus(Status.DONE);
        manager.updateTask(task_6.getId(),task_6);
        System.out.println(manager.getById(6));
        System.out.println();

        System.out.println("Изменил статус всех подзадач к эпику 0 на DONE");
        subtask_1.setStatus(Status.DONE);
        manager.updateSubtask(subtask_1.getId(),subtask_1);
        subtask_2.setStatus(Status.DONE);
        manager.updateSubtask(subtask_2.getId(),subtask_2);
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        // И, наконец, попробуйте удалить одну из подзадач.
        System.out.println("Удаляем сабтаск: 5");
        manager.removeSubtask(5);
        System.out.println(manager.getAllSubtasks());
        System.out.println(epic_4);
        System.out.println();
        //  одну из задач.
        System.out.println("Удаляем таск: 6");
        manager.removeTask(6);
        System.out.println(manager.getAllTasks());
        System.out.println();
        //  один из эпиков.

        System.out.println("Удаляем эпик: 0");
        manager.removeEpic(0);
        System.out.println(manager.getAllEpics());
        System.out.println();

        System.out.println("Удаляем все таски");
        manager.removeAllTask();
        System.out.println(manager.getAllTasks());
        System.out.println();

        System.out.println("Удаляем все подзадачи");
        manager.removeAllSubtask();
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Добавляем список эпиков и подзадач для удаления");

        Epic epic_7 = new Epic("Эпик 7", "...");
        manager.addEpic(epic_7);
        System.out.println(manager.getAllEpics());

        Subtask subtask_8 = new Subtask("Подзадача 8", "...", 7);
        manager.addSubtask(subtask_8);

        Subtask subtask_9 = new Subtask("Подзадача 9", "....", 7);
        manager.addSubtask(subtask_9);

        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Удаляем все эпики");
        manager.removeAllEpics();
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
    }

}
