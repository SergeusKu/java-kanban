import service.Manager;
import model.Status;

public class Main {
    static Manager manager;
    public static void main(String[] args) {
        // Делаю только заданные тест кейсы.
        manager = new Manager();

        manager.addEpic("Эпик 0", "...");
        manager.addSubtask("Подзадача 1", "...",0);
        manager.addSubtask("Подзадача 2", "....",0);

        manager.addTask("Задача 3", "...");

        manager.addEpic("Эпик 4", "...");
        manager.addSubtask("Подзадача 5", "...",4);

        manager.addTask("Задача 6", "...");

        //Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..)
        System.out.println("Список всех задач: ");
        System.out.println(manager.getAll());
        System.out.println();
        System.out.println("Эпик № 0: ");
        System.out.println(manager.printEpicLinks(0));
        System.out.println();

        System.out.println("Меняем название и описание эпика 0");
        manager.changeNameAndDescription(0, "Эпик № 0 ***", "Какое-то описание эпика");

        System.out.println("Меняем название и описание подзадачи 2");
        manager.changeNameAndDescription(2, "Подзадача № 2 ***", "Какое-то описание подзадачи");

        System.out.println("Меняем название и описание задачи 6");
        manager.changeNameAndDescription(6, "Задача № 6 *", "Какое-то описание задачи");

        System.out.println("Получаем конкретный эпик без подзадач");
        System.out.println(manager.getById(0));
        System.out.println("Получаем конкретную подзадачу");
        System.out.println(manager.getById(2));
        System.out.println("Получаем конкретную задачу");
        System.out.println(manager.getById(6));

        //Измените статусы созданных объектов, распечатайте их.
        // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
        System.out.println("Изменил статус подзадачи № 2");
        manager.changeStatus(2, Status.IN_PROGRESS);
        System.out.println(manager.printEpicLinks(0));
        System.out.println();

        System.out.println("Изменил статус задачи № 6");
        manager.changeStatus(6, Status.DONE);
        System.out.println(manager.getById(6));
        System.out.println();

        System.out.println("Изменил статус всех подзадач к эпику 0 на DONE");
        manager.changeStatus( 1, Status.DONE);
        manager.changeStatus( 2, Status.DONE);
        System.out.println(manager.printEpicLinks(0));
        System.out.println();

        // И, наконец, попробуйте удалить одну из подзадач.
        System.out.println("Удаляем сабтаск: 5");
        manager.removeSubtask(5);
        System.out.println(manager.getAll());
        System.out.println(manager.printEpicLinks(4));
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
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Добавляем список эпиков и подзадач для удаления");
        manager.addEpic("Эпик 7", "...");
        manager.addSubtask("Подзадача 8", "...",7);
        manager.addSubtask("Подзадача 9", "....",7);
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Удаляем все эпики");
        manager.removeAllEpics();
        System.out.println(manager.getAll());
    }

}
