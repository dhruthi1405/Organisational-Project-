package com.organization.service;

import com.organization.entity.*;
import com.organization.repository.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final DepartmentRepository departmentRepository;
    private final SubDepartmentRepository subDepartmentRepository;
    private final TeamRepository teamRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public SubDepartment createSubDepartment(Long departmentId, SubDepartment subDepartment) {
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        subDepartment.setDepartment(department);
        return subDepartmentRepository.save(subDepartment);
    }

    public Team createTeam(Long subDepartmentId, Team team) {
        SubDepartment subDepartment = subDepartmentRepository.findById(subDepartmentId)
            .orElseThrow(() -> new RuntimeException("Sub-Department not found"));
        team.setSubDepartment(subDepartment);
        return teamRepository.save(team);
    }

    public Position createPosition(Long teamId, Position position) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new RuntimeException("Team not found"));
        position.setTeam(team);
        return positionRepository.save(position);
    }

    public Employee assignEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
