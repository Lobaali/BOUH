package com.bouh.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "agora")
public class AgoraConfig {
    private String appId;
    private String appCertificate;

    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }

    public String getAppCertificate() { return appCertificate; }
    public void setAppCertificate(String appCertificate) { this.appCertificate = appCertificate; }
}