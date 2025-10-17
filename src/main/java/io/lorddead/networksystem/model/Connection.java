package io.lorddead.networksystem.model;

import java.time.LocalDateTime;

public class Connection implements Model {
    private long id;
    private long deviceFromId;
    private long deviceToId;
    private String connectionType;
    private String status;
    private LocalDateTime createdAt;

    public Connection(ConnectionBuilder builder) {
        this.id = builder.id;
        this.deviceFromId = builder.deviceFromId;
        this.deviceToId = builder.deviceToId;
        this.connectionType = builder.connectionType;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
    }

    public static class ConnectionBuilder {
        private long id;
        private long deviceFromId;
        private long deviceToId;
        private String connectionType;
        private String status;
        private LocalDateTime createdAt;

        public ConnectionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ConnectionBuilder setDeviceFromId(long deviceFromId) {
            this.deviceFromId = deviceFromId;
            return this;
        }

        public ConnectionBuilder setDeviceToId(long deviceToId) {
            this.deviceToId = deviceToId;
            return this;
        }

        public ConnectionBuilder setConnectionType(String connectionType) {
            this.connectionType = connectionType;
            return this;
        }

        public ConnectionBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public ConnectionBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Connection build() {
            return new Connection(this);
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public long getDeviceFromId() {
        return deviceFromId;
    }

    public long getDeviceToId() {
        return deviceToId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeviceFromId(long deviceFromId) {
        this.deviceFromId = deviceFromId;
    }

    public void setDeviceToId(long deviceToId) {
        this.deviceToId = deviceToId;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Connection { " +
                "id = [" + id + ']' +
                ", deviceFromId = [" + deviceFromId + ']' +
                ", deviceToId = [" + deviceToId + ']' +
                ", connectionType = [" + connectionType + ']' +
                ", status = [" + status + ']' +
                ", createdAt = [" + createdAt + "] " + '}';
    }
}
