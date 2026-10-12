package com.deviceauth.controller;
import com.deviceauth.dto.StatusRequest;import com.deviceauth.service.DeviceService;import jakarta.validation.Valid;import java.security.Principal;import java.util.*;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin")
public class AdminController {private final DeviceService service;public AdminController(DeviceService s){service=s;}@GetMapping("/devices") public Map<String,Object> devices(){return service.adminList();}@PatchMapping("/devices/{id}/status") public Map<String,Object> status(Principal p,@PathVariable Long id,@Valid @RequestBody StatusRequest r){return service.changeStatus(p.getName(),id,r.status());}@GetMapping("/audit-logs") public List<Map<String,Object>> logs(){return service.audit();}}
