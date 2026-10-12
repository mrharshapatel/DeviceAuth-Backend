package com.deviceauth.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="devices", uniqueConstraints=@UniqueConstraint(columnNames={"owner_id","device_identifier"}))
public class Device {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100) private String deviceName;
    @Column(nullable=false,length=160) private String deviceIdentifier;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="owner_id",nullable=false) private AppUser owner;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private DeviceStatus status=DeviceStatus.PENDING;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="approved_by") private AppUser approvedBy;
    private Instant approvedAt;
    @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
    public Long getId(){return id;} public String getDeviceName(){return deviceName;} public void setDeviceName(String v){deviceName=v;}
    public String getDeviceIdentifier(){return deviceIdentifier;} public void setDeviceIdentifier(String v){deviceIdentifier=v;}
    public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
    public DeviceStatus getStatus(){return status;} public void setStatus(DeviceStatus v){status=v;}
    public AppUser getApprovedBy(){return approvedBy;} public void setApprovedBy(AppUser v){approvedBy=v;}
    public Instant getApprovedAt(){return approvedAt;} public void setApprovedAt(Instant v){approvedAt=v;}
    public Instant getCreatedAt(){return createdAt;}
}
