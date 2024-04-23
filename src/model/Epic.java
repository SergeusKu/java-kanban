package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIdList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(Epic epic) {
        super(epic);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public void setSubtaskIdList(Integer subtaskId) {
        if (this.getId() == subtaskId) {
            throw new Error("Epic нельзя добавить в самого себя в виде подзадачи");
        }
        this.subtaskIdList.add(subtaskId);
    }

    public void deleteSubtaskIdList(Integer subtaskId) {
        this.subtaskIdList.remove(subtaskId);
    }

    public void deleteAllSubtaskIdList() {
        this.subtaskIdList.clear();
    }

    public ArrayList<Integer> getSubtaskIdList() {
        return subtaskIdList;
    }
}
