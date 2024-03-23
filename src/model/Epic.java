package model;

public class Epic extends Task {


    public Epic (Integer id, String name, String description) {
        super(id, name, description);
    }
    @Override
    public String toString() {
        return "Epic{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription()+ '\'' +
                ", status=" + getStatus() +
                '}';
    }

}
