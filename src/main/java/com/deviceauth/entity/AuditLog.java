package com.deviceauth.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long actorId; private Long deviceId;
    @Column(nullable=false,length=80) private String eventType;
    @Column(nullable=false,length=40) private String result;
    @Column(nullable=false,updatable=false) private Instant timestamp=Instant.now();
    protected AuditLog(){}
    public AuditLog(Long actorId, Long deviceId, String eventType, String result){this.actorId=actorId;this.deviceId=deviceId;this.eventType=eventType;this.result=result;}
    public Long getId(){return id;} public Long getActorId(){return actorId;} public Long getDeviceId(){return deviceId;}
    public String getEventType(){return eventType;} public String getResult(){return result;} public Instant getTimestamp(){return timestamp;}
}
