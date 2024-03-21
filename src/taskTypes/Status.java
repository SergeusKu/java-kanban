package taskTypes;

public enum Status {
    //Список статусов задач содержит: Название типа, Признак финального статуса, приоритет отображения
    //Изменение статусов возможно только если следующий статус с большим приоритетом отображения,
    // в
    NEW("Новая", false, 0),
    IN_PROGRESS("В работе", false, 1),
    DONE("Завершена", true, 2);

    private final String statusName; //Описание статуса
    private final Integer sequence; //Последовательность статусов задач
    private final Boolean isFinal; //Признак того является ли статус задачи конечным
        Status(String statusName, Boolean isFinal, Integer sequence) {
            this.statusName = statusName;
            this.isFinal = isFinal;
            this.sequence = sequence;
        }
    //Метод позволяет получить следующий статус
    public Integer getSequence() {
        return sequence;
    }
}
