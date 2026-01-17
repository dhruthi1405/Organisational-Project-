package com.organization.controller;

import com.organization.entity.*;
import com.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/departments")
    public Department createDepartment(@RequestBody Department department) {
        return organizationService.createDepartment(department);
    }

    @PostMapping("/departments/{deptId}/subdepartments")
    public SubDepartment createSubDepartment(@PathVariable Long deptId, @RequestBody SubDepartment subDepartment) {
        return organizationService.createSubDepartment(deptId, subDepartment);
    }

    @PostMapping("/subdepartments/{subDeptId}/teams")
    public Team createTeam(@PathVariable Long subDeptId, @RequestBody Team team) {
        return organizationService.createTeam(subDeptId, team);
    }

    @PostMapping("/teams/{teamId}/positions")
    public Position createPosition(@PathVariable Long teamId, @RequestBody Position position) {
        return organizationService.createPosition(teamId, position);
    }

    @PostMapping("/employees")
    public Employee assignEmployee(@RequestBody Employee employee) {
        return organizationService.assignEmployee(employee);
    }
}
