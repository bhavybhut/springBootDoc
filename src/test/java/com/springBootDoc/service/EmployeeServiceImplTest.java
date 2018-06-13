package com.springBootDoc.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.springBootDoc.entity.Employee;
import com.springBootDoc.repository.EmployeeRepository;
import com.springBootDoc.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestConfiguration {

        @Bean
        public EmployeeService getService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;

    private List<Employee> employees;

    @Before
    public void setup() {
        Employee emp = new Employee();
        emp.setFirstName("Jack");
        emp.setLastName("Sparrow");
        emp.setEmail("jsparrow@pirates.caribbean");

        employees = Collections.singletonList(emp);

        Mockito.when(repository.findAll())
               .thenReturn(employees);

        Mockito.when(repository.findById(emp.getId()))
               .thenReturn(Optional.of(emp));
    }

    @After
    public void cleanup() {
    }

    @Test
    public void findAll() throws Exception {
        List<Employee> result = service.findAll();
        Assert.assertEquals("employee list should match", employees, result);
    }

    @Test
    public void findOne() throws Exception {
        Employee result = service.findOne(employees.get(0)
                                                   .getId());
        Assert.assertEquals("employee should match", employees.get(0), result);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneNotFound() throws Exception {
        Employee result = service.findOne("asdfasdf");
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }
}