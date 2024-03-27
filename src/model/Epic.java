package model;

import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtaskIdList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
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
