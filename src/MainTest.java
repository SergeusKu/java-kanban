import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
class MainTest {
    private TaskManager inMemoryTaskManager;
    private HistoryManager historyManager;
    private Epic epic_0;
    private Subtask subtask_1;
    private Subtask subtask_2;
    private Task task_3;
    private Epic epic_4;
    private Subtask subtask_5;
    private Task task_6;
    @BeforeEach
    //Cоздаем "Эпик 0", две подзадачи "Подзадача 1",
    // "Подзадача 2" и несвязанную задачу "Задача 3"
    public void createEpicAndTwoSubtaskAndTask() {

        inMemoryTaskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        epic_0 = new Epic("Эпик 0", "...");
        inMemoryTaskManager.addEpic(epic_0);

        subtask_1 = new Subtask("Подзадача 1", "...", epic_0.getId());
        inMemoryTaskManager.addSubtask(subtask_1);

        subtask_2 = new Subtask("Подзадача 2", "....", epic_0.getId());
        inMemoryTaskManager.addSubtask(subtask_2);

        task_3 = new Task("Задача 3", "...");
        inMemoryTaskManager.addTask(task_3);

        epic_4 = new Epic( "Эпик 4", "...");
        inMemoryTaskManager.addEpic(epic_4);

        subtask_5 = new Subtask("Подзадача 5", "...", epic_4.getId());
        inMemoryTaskManager.addSubtask(subtask_5);

        task_6 = new Task("Задача 6", "...");
        inMemoryTaskManager.addTask(task_6);

    }
    @Test
    //Проверям что вывод эпиков будет соответствовать ожидаемому списку
    public void getAllEpics() {
        ArrayList<Epic> allEpicsList = new ArrayList<>();
        ArrayList<Epic> epicsList = new ArrayList<>(inMemoryTaskManager.getAllEpics());
        allEpicsList.add(epic_0);
        allEpicsList.add(epic_4);
        assertEquals(allEpicsList.toString(),epicsList.toString(),
                "Список Эпиков не соответствует добавленным");
    }
    @Test
    //Проверям что вывод подзадач будет соответствовать ожидаемому списку
    public void getAllSubtasks() {
        ArrayList<Subtask> allSubtasksList = new ArrayList<>();
        ArrayList<Subtask> subtasksList  = new ArrayList<>(inMemoryTaskManager.getAllSubtasks());
        allSubtasksList.add(subtask_1);
        allSubtasksList.add(subtask_2);
        allSubtasksList.add(subtask_5);
        assertEquals(allSubtasksList.toString(),subtasksList.toString(),
                "Список Подзадач не соответствует добавленным");
    }
    @Test
    //Проверям что вывод задач будет соответствовать ожидаемому списку
    public void getAllTasks() {
        ArrayList<Task> allTasksList = new ArrayList<>();
        ArrayList<Task> tasksList  = new ArrayList<>(inMemoryTaskManager.getAllTasks());
        allTasksList.add(task_3);
        allTasksList.add(task_6);
        assertEquals(allTasksList.toString(),tasksList.toString(),
                "Список Задач не соответствует добавленным");
    }
    @Test
    //Проверяем что после изменения названия и описания эпика описание и название соответствуют ожидаемым
    public void checkEpicNameAndDescriptionById() {
        int exceptEpicId = epic_0.getId();
        String epicName = "Эпик № 0 ***";
        String epicDescription = "Какое-то описание эпика";
        epic_0.setName(epicName);
        epic_0.setDescription(epicDescription);
        assertEquals(epicName, epic_0.getName(),
                "Новое название эпика не соответствует заданному");
        assertEquals(epicDescription, epic_0.getDescription(),
                "Новое описание эпика не соответствует заданному");
    }

    @Test
    //Измените статусы задачи и подзадачи
    // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
    public void changeEpicAndSubtaskAndTaskStatus() {
        subtask_2.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subtask_2);
        task_6.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(task_6);

        assertEquals(Status.IN_PROGRESS, subtask_2.getStatus(),
                "Статус Подзадачи не соответствует IN_PROGRESS");
        assertEquals(Status.DONE, task_6.getStatus(),
                "Статус Задачи не соответствует DONE");
        assertEquals(Status.IN_PROGRESS, epic_0.getStatus(),
                "Статус Эпика не соответствует IN_PROGRESS");
    }

    @Test
    //Измените статусы всех подзадач к эпику
    // Проверьте, что статусы подзадач сохранились, а статус эпика рассчитался по статусам подзадач.
    public void changeEpicStatusBySubtasksDoneStatus() {
        subtask_1.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subtask_1);
        subtask_2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subtask_2);
        inMemoryTaskManager.updateTask(task_6);
        assertEquals(Status.DONE, subtask_1.getStatus(),
                "Статус Подзадачи не соответствует DONE");
        assertEquals(Status.DONE, subtask_2.getStatus(),
                "Статус Подзадачи не соответствует DONE");
        assertEquals(Status.DONE, epic_0.getStatus(),
                "Статус Эпика не соответствует DONE");
    }
    @Test
    //попробуйте удалить одну из подзадач.
    public void removeSubtask() {
        inMemoryTaskManager.removeSubtask(subtask_5.getId());
        assertNull(inMemoryTaskManager.getById(subtask_5.getId()));
    }
    @Test
    //попробуйте удалить задачу
    public void removeTask() {
        inMemoryTaskManager.removeTask(task_6.getId());
        assertNull(inMemoryTaskManager.getById(task_6.getId()));
    }
    @Test
    //попробуйте удалить один из эпиков.
    public void removeEpic() {
        inMemoryTaskManager.removeEpic(epic_0.getId());
        assertNull(inMemoryTaskManager.getById(epic_0.getId()));
    }
    @Test
    //Удаляем все задачи
    public void removeAllTask() {
        inMemoryTaskManager.removeAllTask();
        assertEquals("[]", inMemoryTaskManager.getAllTasks().toString());
    }

    @Test
    //Удаляем все подзадачи
    public void removeAllSubtask() {
        inMemoryTaskManager.removeAllSubtask();
        assertNotNull(inMemoryTaskManager.getAllEpics());
        assertEquals("[]", inMemoryTaskManager.getAllSubtasks().toString());
    }

    @Test
    //Удаляем все Эпики
    public void removeAllEpics() {
        inMemoryTaskManager.removeAllEpics();
        assertTrue(inMemoryTaskManager.getAllEpics().size()==0);
        assertTrue(inMemoryTaskManager.getAllSubtasks().size()==0);
    }

    @Test
    //Проверяем что экземпляры класса TASK равны друг другу, если равен их id
    public void taskEqualToEachOtherIfTheirIDIsEqual() {

        final Task savedTask = task_3;

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task_3, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task_3, tasks.get(0), "Задачи не совпадают.");
        assertEquals(task_6, tasks.get(1), "Задачи не совпадают.");
    }
    @Test
    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    public void epicObjectCannotBeAddedToItself() {
        try {
            Epic epic_7 = new Epic("Эпик 7", "...");
            inMemoryTaskManager.addEpic(epic_7);
            epic_7.setSubtaskIdList(7);

        } catch (Error error){
            assertTrue(error.getMessage()=="Epic нельзя добавить в самого себя в виде подзадачи");
        }
    }
    @Test
    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    public void subtaskObjectCannotBeMadeItsOwnEpic() {
        try {
            Subtask subtask_7 = new Subtask(7,"Подзадача 7", "...", 7);
            inMemoryTaskManager.addSubtask(subtask_7);
        } catch (Error error){
            assertTrue(error.getMessage()=="Subtask нельзя сделать своим же эпиком");
        }

    }
    @Test
    //убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    public void utilityClassAlwaysReturnsInitializedAndReadyToUseManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        assertTrue( taskManager.getClass() == inMemoryTaskManager.getClass() );

    }
    @Test
    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    public void addsTasksOfDifferentTypesAndCanFindThemById() {
        Epic epic_A = new Epic("Эпик 0", "...");
        Epic epic_B = new Epic("Подзадача 1", "...");


        inMemoryTaskManager.addEpic(epic_A);
        inMemoryTaskManager.addEpic(epic_B);

        assertEquals(inMemoryTaskManager.getById(epic_A.getId()), epic_A);
        assertEquals(inMemoryTaskManager.getById(epic_B.getId()), epic_B);
    }
    @Test
    //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    public void conflictTasksWithGivenIdAndGeneratedId() {
        try {
            Subtask subtask_7 = new Subtask(6,"Подзадача 7", "...", 0);
            inMemoryTaskManager.addSubtask(subtask_7);

        } catch (Error error){
            assertTrue(error.getMessage()=="Задача с таким id уже существует");
        }
    }
    @Test
    //создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    public void immutabilityTask() {
        Epic epic_A = new Epic("Эпик 0", "...");
        inMemoryTaskManager.addEpic(epic_A);
        assertEquals(inMemoryTaskManager.getById(epic_A.getId()).toString(),epic_A.toString());
    }
    @Test
    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    public void addedTaskRetainPreviousVersion() {
        String task = inMemoryTaskManager.getById(subtask_5.getId()).toString();
        subtask_5.setName("Новое название таски");
        subtask_5.setDescription("Новое описание таски");

        assertNotEquals(task, inMemoryTaskManager.getHistory().toString());
    }
}