public class Subtask extends Task {
    Subtask (String name, String description) {
        super(name, description);
        setType(TaskType.SUBTASK);
    }

    @Override
    public String toString() {
        return "  Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription()+ '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
