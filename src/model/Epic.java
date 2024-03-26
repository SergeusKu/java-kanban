package model;

import java.util.ArrayList;

public class Epic extends Task {


    public Epic (Integer id, String name, String description) {
        super(id, name, description);
    }
    ArrayList<Integer> subtaskIdList = new ArrayList<>();

    public String toString() {
        return "Epic{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", description='" + getDescription()+ '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public void setSubtaskIdList(Integer subtaskId) {
        this.subtaskIdList.add(subtaskId);
    }
    public void deleteSubtaskIdList(Integer subtaskId) {
        this.subtaskIdList.remove(subtaskId);
    }

    public ArrayList<Integer> getSubtaskIdList() {
        return subtaskIdList;
    }
}
