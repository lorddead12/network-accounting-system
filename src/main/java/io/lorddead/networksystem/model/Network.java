package io.lorddead.networksystem.model;

import java.time.LocalDateTime;

public class Network implements Model{
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Network() {}

    public Network(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Network(long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The argument cannot be negative");
        }

        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Network - { "
                + "id = [" + id + ']'
                + ", name = [" + name + ']'
                + ", description = [" + description + ']'
                + ", createdAt = [" + createdAt + ']' + ' ' + '}';
    }
}
