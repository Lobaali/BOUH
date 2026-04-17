package com.bouh.backend.model.Dto.Meeting;

public class JoinMeetingResponseDto {
    private String appId;
    private String channelName;
    private String token;
    private Integer uid;
    private String appointmentId;
    private String role;

    public JoinMeetingResponseDto() {}

    public JoinMeetingResponseDto(
            String appId,
            String channelName,
            String token,
            Integer uid,
            String appointmentId,
            String role) {
        this.appId = appId;
        this.channelName = channelName;
        this.token = token;
        this.uid = uid;
        this.appointmentId = appointmentId;
        this.role = role;
    }

    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }

    public String getChannelName() { return channelName; }
    public void setChannelName(String channelName) { this.channelName = channelName; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
