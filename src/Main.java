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

        Epic epic_0 = new Epic(manager.generateId(), "Эпик 0", "...");
        manager.addEpic(epic_0);

        Subtask subtask_1 = new Subtask(manager.generateId(), "Подзадача 1", "...", 0);
        manager.addSubtask(subtask_1);
        manager.updateEpicStatus();

        Subtask subtask_2 = new Subtask(manager.generateId(), "Подзадача 2", "....", 0);
        manager.addSubtask(subtask_2);
        manager.updateEpicStatus();

        Task task_3 = new Task(manager.generateId(), "Задача 3", "...");
        manager.addTask(task_3);

        Epic epic_4 = new Epic(manager.generateId(), "Эпик 4", "...");
        manager.addEpic(epic_4);

        Subtask subtask_5 = new Subtask(manager.generateId(), "Подзадача 5", "...", 4);
        manager.addSubtask(subtask_5);
        manager.updateEpicStatus();

        Task task_6 = new Task(manager.generateId(), "Задача 6", "...");
        manager.addTask(task_6);

        //Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..)
        System.out.println("Список всех задач: ");
        System.out.println(manager.getAll());
        System.out.println();
        System.out.println("Эпик № 0: ");
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        System.out.println("Меняем название и описание эпика 0");
        epic_0.setName("Эпик № 0 ***");
        epic_0.setDescription("Какое-то описание эпика");

        System.out.println("Меняем название и описание подзадачи 2");
        subtask_2.setName("Подзадача № 2 ***");
        subtask_2.setDescription("Какое-то описание подзадачи");

        System.out.println("Меняем название и описание задачи 6");
        task_6.setName("Задача № 6 *");
        task_6.setDescription("Какое-то описание задачи");

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
        manager.updateEpicStatus();
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        System.out.println("Изменил статус задачи № 6");
        task_6.setStatus(Status.DONE);
        manager.updateEpicStatus();
        System.out.println(manager.getById(6));
        System.out.println();

        System.out.println("Изменил статус всех подзадач к эпику 0 на DONE");
        subtask_1.setStatus(Status.DONE);
        subtask_2.setStatus(Status.DONE);
        manager.updateEpicStatus();
        System.out.println(epic_0);
        System.out.println(manager.getSubtasksByEpicId(epic_0.getId()));
        System.out.println();

        // И, наконец, попробуйте удалить одну из подзадач.
        System.out.println("Удаляем сабтаск: 5");
        manager.removeSubtask(5);
        System.out.println(manager.getAll());
        System.out.println(epic_4);
        System.out.println();
        //  одну из задач.
        System.out.println("Удаляем таск: 6");
        manager.removeTask(6);
        System.out.println(manager.getAll());
        System.out.println();
        //  один из эпиков.

        System.out.println("Удаляем эпик: 0");
        manager.removeEpic(0);
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Удаляем все таски");
        manager.removeAllTask();
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Удаляем все подзадачи");
        manager.removeAllSubtask();
        manager.updateEpicStatus();
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Добавляем список эпиков и подзадач для удаления");

        Epic epic_7 = new Epic(manager.generateId(), "Эпик 7", "...");
        manager.addEpic(epic_7);

        Subtask subtask_8 = new Subtask(manager.generateId(), "Подзадача 8", "...", 7);
        manager.addSubtask(subtask_8);

        Subtask subtask_9 = new Subtask(manager.generateId(), "Подзадача 9", "....", 7);
        manager.addSubtask(subtask_9);

        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Удаляем все эпики");
        manager.removeAllEpics();
        System.out.println(manager.getAll());
    }

}
