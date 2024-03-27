package model;

import java.util.Objects;

public class Task {
    private Integer id;
    private String name; //Название задачи
    private String description; //Описание задачи
    private Status status; //Текущий статус задачи

    public Task( String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }
    public Task(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public int hashCode() {
        int hash = 17;
        if (id != null) {
            hash += id.hashCode();
        }
        hash = hash * 31;

        if (name != null) {
            hash += name.hashCode();
        }
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return true;
        Task otherTask = (Task) obj;
        return Objects.equals(id, otherTask.getId()) &&
                Objects.equals(name, otherTask.getName());
    }
}
