import java.util.ArrayList;
public class Task {
    private String name; //Название задачи
    private String description; //Описание задачи
    private ArrayList<Integer> links; //Список дочерних связанных задач
    private Status status; //Текущий статус задачи
    private TaskType type; //Текущий тип задачи

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        setType(TaskType.TASK);
    }

    @Override
    public String toString() {
        return " Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<Integer> getLinks() {
        return links;
    }

    public void addLinks(Integer link) {
        if (links == null ) {
            links = new ArrayList<>();
        }
        this.links.add(link);
    }

    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }

}
