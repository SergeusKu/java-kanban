package model;

public class Subtask extends Task {
    private Integer parentId;

    public Subtask(String name, String description, Integer parentId) {
        super(name, description);
        this.parentId = parentId;
    }
    public Subtask(Integer id, String name, String description, Integer parentId) {
        super(id, name, description);
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return " Subtask{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public Integer getParentId() {
        return parentId;
    }

}
