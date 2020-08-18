package com.task.login.model;

public class UpdateInfo {
    private int id;
    private String updateName;
    private String updatePhone;
    private String updatePassword;

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdatePhone() {
        return updatePhone;
    }

    public void setUpdatePhone(String updatePhone) {
        this.updatePhone = updatePhone;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "id=" + id +
                ", newName='" + updateName + '\'' +
                ", newPhone='" + updatePhone + '\'' +
                ", newPassword='" + updatePassword + '\'' +
                '}';
    }
}
