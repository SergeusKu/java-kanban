import java.util.Objects;
public class Task extends Epic {
    private Integer parentId;
    public Task(Integer id, String name, String description, Integer parentId) {
        super(id, name, description);
        this.parentId = parentId;
    }


    @Override
    public String toString() {
        return " Task{" +
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
