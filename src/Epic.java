public class Epic extends Task{
    Epic (String name, String description) {
        super(name, description);
        setType(TaskType.EPIC);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription()+ '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
