package com.deviceauth.service;

import com.deviceauth.dto.DeviceRequest;import com.deviceauth.entity.*;import com.deviceauth.repository.*;
import java.time.Instant;import java.util.*;
import org.springframework.http.HttpStatus;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.springframework.web.server.ResponseStatusException;

@Service
public class DeviceService {
 private final DeviceRepository devices;private final UserRepository users;private final AuditLogRepository logs;
 public DeviceService(DeviceRepository d,UserRepository u,AuditLogRepository l){devices=d;users=u;logs=l;}
 @Transactional(readOnly=true) public List<Map<String,Object>> mine(String email){AppUser owner=user(email);return devices.findByOwnerIdOrderByCreatedAtDesc(owner.getId()).stream().map(this::view).toList();}
 @Transactional public Map<String,Object> register(String email,DeviceRequest r){AppUser owner=user(email);if(devices.existsByOwnerIdAndDeviceIdentifier(owner.getId(),r.deviceIdentifier().trim()))throw new ResponseStatusException(HttpStatus.CONFLICT,"This device identifier is already registered to your account");Device d=new Device();d.setOwner(owner);d.setDeviceName(r.deviceName().trim());d.setDeviceIdentifier(r.deviceIdentifier().trim());d.setStatus(DeviceStatus.PENDING);d=devices.save(d);logs.save(new AuditLog(owner.getId(),d.getId(),"DEVICE_REGISTERED","PENDING"));return view(d);}
 @Transactional(readOnly=true) public Map<String,Object> adminList(){return Map.of("devices",devices.findAll().stream().map(this::view).toList());}
 @Transactional public Map<String,Object> changeStatus(String email,Long id,DeviceStatus status){if(status==DeviceStatus.PENDING)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin may set status to TRUSTED or BLOCKED only");AppUser admin=user(email);Device d=devices.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Device not found"));d.setStatus(status);d.setApprovedBy(admin);d.setApprovedAt(Instant.now());devices.save(d);logs.save(new AuditLog(admin.getId(),d.getId(),"DEVICE_STATUS_CHANGED",status.name()));return view(d);}
 @Transactional public Map<String,Object> authenticate(String email,Long id){AppUser owner=user(email);Device d=devices.findByIdAndOwnerId(id,owner.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Device not found"));String result=d.getStatus()==DeviceStatus.TRUSTED?"ALLOWED":"DENIED";logs.save(new AuditLog(owner.getId(),d.getId(),"DEVICE_AUTHENTICATION",result));if(d.getStatus()!=DeviceStatus.TRUSTED)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Device is not trusted; current status: "+d.getStatus());return Map.of("result","ALLOWED","message","Device authentication successful","deviceId",d.getId());}
 @Transactional(readOnly=true) public List<Map<String,Object>> audit(){return logs.findTop100ByOrderByTimestampDesc().stream().map(l->Map.<String,Object>of("id",l.getId(),"actorId",Objects.toString(l.getActorId(),""),"deviceId",Objects.toString(l.getDeviceId(),""),"eventType",l.getEventType(),"result",l.getResult(),"timestamp",l.getTimestamp().toString())).toList();}
 private AppUser user(String email){return users.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not found"));}
 private Map<String,Object> view(Device d){return Map.of("id",d.getId(),"deviceName",d.getDeviceName(),"deviceIdentifier",d.getDeviceIdentifier(),"ownerId",d.getOwner().getId(),"ownerName",d.getOwner().getName(),"ownerEmail",d.getOwner().getEmail(),"status",d.getStatus().name(),"createdAt",d.getCreatedAt().toString(),"approvedAt",d.getApprovedAt()==null?"":d.getApprovedAt().toString());}
}
