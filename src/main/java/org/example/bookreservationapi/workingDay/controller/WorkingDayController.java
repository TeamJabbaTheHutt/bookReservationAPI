package org.example.bookreservationapi.workingDay.controller;

import org.example.bookreservationapi.workingDay.enity.WorkingDay;
import org.example.bookreservationapi.workingDay.service.WorkingDayService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.PortUnreachableException;
import java.util.List;

@RestController
@RequestMapping("/api/workingdays")
public class WorkingDayController {

    private final WorkingDayService workingDayService;

    public WorkingDayController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public List<WorkingDay> getMyWorkingDays(Authentication authentication) {
        return workingDayService.getMyWorkingDays(authentication);
    }

    @PostMapping
    public WorkingDay createWorkingDay(@RequestBody WorkingDay workingDay, Authentication authentication){
        return workingDayService.createWorkingDay(workingDay, authentication);
    }

    @PutMapping("/{id}")
    public WorkingDay updateWorkingDay(@PathVariable Long workingDayId, @RequestBody WorkingDay workingDay, Authentication authentication){
        return workingDayService.updateWorkingDay(workingDayId,workingDay,authentication);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkingDay(@PathVariable Long id, Authentication authentication) {
        workingDayService.deleteWorkingDay(id, authentication);
    }

}
