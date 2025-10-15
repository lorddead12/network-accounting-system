package io.lorddead.networksystem.model;


import java.time.LocalDateTime;

public class Device implements Model{

    private long id;
    private long networkId;
    private String name;
    private String ipAddress;
    private String macAddress;
    private String type;
    private String status;
    private LocalDateTime createdAt;

    public Device() {}

    public Device(long id, long networkId, String name, String ipAddress,
                  String macAddress, String type, String status, LocalDateTime createdAt) {
        this.id = id;
        this.networkId = networkId;
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Device(String name, String ipAddress, String macAddress, String type, String status) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.type = type;
        this.status = status;
    }

    public Device(long networkId, String name, String ipAddress,
                  String macAddress, String type, String status) {
        this.networkId = networkId;
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.type = type;
        this.status = status;
    }

    public long getNetworkId() {
        return networkId;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setNetworkId(long networkId) {
        this.networkId = networkId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Device - { " +
                "id = [" + id + ']' +
                ", networkId = [" + networkId + ']' +
                ", name = [" + name + ']' +
                ", ipAddress = [" + ipAddress + ']' +
                ", macAddress = [" + macAddress + ']' +
                ", type = [" + type + ']' +
                ", status = [" + status + ']' +
                ", createdAt = [" + createdAt + ']' + " }";
    }
}
