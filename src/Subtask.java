public class Subtask extends Task {
    public Subtask (Integer id, String name, String description, Integer parentId) {
        super(id, name, description, parentId);
    }

    @Override
    public String toString() {
        return "  Subtask{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription()+ '\'' +
                ", status=" + getStatus() +
                '}';
    }

}
