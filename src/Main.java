import control.Manager;
import taskTypes.Status;

public class Main {
    static Manager manager;
    public static void main(String[] args) {
        // Делаю только заданные тест кейсы.
        manager = new Manager();

        manager.addEpic("Эпик 0", "...");
        manager.addTask("Задача 1", "...",0);
        manager.addSubtask("Подзадача 2", "...",1);
        manager.addSubtask("Подзадача 3", "....",1);
        manager.addTask("Задача 4", "...",0);


        manager.addEpic("Эпик 5", "...");
        manager.addTask("Задача 6", "...",5);
        manager.addSubtask("Подзадача 7", "...",6);

        manager.addEpic("Эпик 8", "...");
        manager.addTask("Задача 9", "...",8);


        //Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..)
        System.out.println("Список всех задач: ");
        System.out.println(manager.getAll());
        System.out.println();
        System.out.println("Список эпиков № 0: ");
        System.out.println(manager.printEpicLinks(0));
        System.out.println();
        //Измените статусы созданных объектов, распечатайте их.
        // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
        System.out.println("Изменил статус подзадачи № 3");
        manager.changeStatus(3, Status.IN_PROGRESS);
        System.out.println(manager.printEpicLinks(0));
        System.out.println();

        System.out.println("Изменил статус задачи № 9");
        manager.changeStatus(9, Status.DONE);
        System.out.println(manager.printEpicLinks(8));
        System.out.println();

        System.out.println("Изменил статус всех подзадач к эпику 5 на DONE");
        manager.changeStatus( 6, Status.DONE);
        manager.changeStatus( 7, Status.DONE);
        System.out.println(manager.printEpicLinks(5));
        System.out.println();

        // И, наконец, попробуйте удалить одну из подзадач.
        System.out.println("Удаляем сабтаск: 3");
        manager.removeSubtask(3);
        System.out.println(manager.getAll());
        System.out.println(manager.printEpicLinks(0));
        System.out.println();
        //  одну из задач.
        System.out.println("Удаляем таск: 6");
        manager.removeTask(6);
        System.out.println(manager.getAll());
        System.out.println(manager.printEpicLinks(5));
        System.out.println();
        //  один из эпиков.

        System.out.println("Удаляем эпик: 0");
        manager.removeEpic(0);
        System.out.println(manager.getAll());
        System.out.println();

        System.out.println("Удаляем все");
        manager.removeAll();
        System.out.println(manager.getAll());
    }

}
