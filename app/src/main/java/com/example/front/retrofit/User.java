package com.example.front.retrofit;

public class User {
    private int id;
    private String name;
    private String second_name;
    private String last_name;
    private String email;
    private String email_verified_at;
    private String password_reset_at;
    private int role;
    private int blocked;
    private String created_at;
    private String updated_at;
    private String card_id;
    private String phone;
    private int points;
    private String full_name;
    private boolean curator;
    private String address;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public User() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getPassword_reset_at() {
        return password_reset_at;
    }

    public void setPassword_reset_at(String password_reset_at) {
        this.password_reset_at = password_reset_at;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isCurator() {
        return curator;
    }

    public void setCurator(boolean curator) {
        this.curator = curator;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", email_verified_at='" + email_verified_at + '\'' +
                ", password_reset_at='" + password_reset_at + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                ", phone='" + phone + '\'' +
                ", points=" + points +
                ", card_id=" + card_id +
                ", full_name='" + full_name + '\'' +
                ", curator=" + curator +
                '}';
    }
}
