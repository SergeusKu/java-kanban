package model;

public enum TaskType {
    //Список типов задач содержит: Название типа, ....(пока не придумал что еще понадобится)
    EPIC("Эпик"),
    TASK("Задача"),
    SUBTASK("Подзадача");

    private final String taskName;
    TaskType(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

}
