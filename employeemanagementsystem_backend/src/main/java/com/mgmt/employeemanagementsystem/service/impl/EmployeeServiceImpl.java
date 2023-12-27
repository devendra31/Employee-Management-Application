package com.mgmt.employeemanagementsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mgmt.employeemanagementsystem.dto.EmployeeDto;
import com.mgmt.employeemanagementsystem.entity.Employee;
import com.mgmt.employeemanagementsystem.exception.ResourceNotFoundException;
import com.mgmt.employeemanagementsystem.mapper.EmployeeMapper;
import com.mgmt.employeemanagementsystem.repository.EmployeeRepository;
import com.mgmt.employeemanagementsystem.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		
		Employee employee =EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee =employeeRepository.save(employee);
		
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee employee=employeeRepository.findById(employeeId)
		.orElseThrow(()->
		new ResourceNotFoundException("Employee is not exist with given id:"+employeeId));
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees =employeeRepository.findAll();
		return employees.stream().map((employee)-> EmployeeMapper.mapToEmployeeDto(employee))
				.collect(Collectors.toList());
		
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
	Employee employee =	employeeRepository.findById(employeeId).orElseThrow(
				()-> new ResourceNotFoundException("Employee is not exist with given id:"+employeeId)
				
				);
	
	employee.setFirstName(updatedEmployee.getFirstName());
	employee.setLastName(updatedEmployee.getLastName());
	employee.setEmail(updatedEmployee.getEmail());
	
	Employee updatedEmployeeObj =employeeRepository.save(employee);
		
		return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		Employee employee =	employeeRepository.findById(employeeId).orElseThrow(
				()-> new ResourceNotFoundException("Employee is not exist with given id:"+employeeId)
				
				);
	employeeRepository.deleteById(employeeId);
		
		
	}

}
