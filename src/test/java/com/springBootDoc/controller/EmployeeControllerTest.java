package com.springBootDoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBootDoc.entity.Employee;
import com.springBootDoc.repository.EmployeeRepository;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    @Before
    public void setup() {
        Employee emp = new Employee();
        emp.setId("jack-id");
        emp.setFirstName("Jack");
        emp.setLastName("Sparrow");
        emp.setEmail("jsparrow@pirates.caribbean");
        repository.save(emp);
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees"))
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
           .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("jsparrow@pirates.caribbean")));
    }

    @Test
    public void findOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees/jack-id"))
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("jsparrow@pirates.caribbean")));
    }

    @Test
    public void findOne404() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees/asdfdsfasdfasd"))
           .andExpect(MockMvcResultMatchers.status()
                                           .isNotFound());
    }

    @Test
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Black");
        emp.setLastName("Pearl");
        emp.setEmail("blackpearl@pirates.caribbean");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(mapper.writeValueAsBytes(emp))
                   )
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
           .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("blackpearl@pirates.caribbean")));


        mvc.perform(MockMvcRequestBuilders.get("/employees"))
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void create400() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Black");
        emp.setLastName("Pearl");
        emp.setEmail("jsparrow@pirates.caribbean");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(mapper.writeValueAsBytes(emp))
                   )
           .andExpect(MockMvcResultMatchers.status()
                                           .isBadRequest());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }
}