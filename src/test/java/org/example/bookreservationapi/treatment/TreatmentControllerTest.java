package org.example.bookreservationapi.treatment;


import org.example.bookreservationapi.treament.controller.TreatmentController;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.service.TreatmentService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.example.bookreservationapi.treament.entity.Treatment;
import org.example.bookreservationapi.treament.repository.TreatmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

class TreatmentControllerTest {

    @Autowired
    private TreatmentController treatmentController;

    @Test
    void getAllTreatments_shouldReturnSeededData() {

        ResponseEntity<List<Treatment>> response =
                treatmentController.getAllTreatments();

        List<Treatment> treatments = response.getBody();

        assertThat(treatments).hasSize(4);
        assertThat(treatments.get(0).getTitle()).isEqualTo("treatment1");
        assertThat(treatments.get(1).getTitle()).isEqualTo("treatment2");
    }

    @Test
    void getTreatmentById_shouldReturnTreatment1() {

        ResponseEntity<Treatment> response =
                treatmentController.getTreatmentById(1L);

        Treatment treatment = response.getBody();

        assertThat(treatment.getTitle()).isEqualTo("treatment1");
    }

    @Test
    void createAndDeleteTreatment() {

        Treatment treatmentTest = new Treatment();
        treatmentTest.setTitle("treatmentTest");
        treatmentTest.setPrice(200);
        treatmentTest.setDurationMinutes(2001);

        ResponseEntity<Treatment> response =
                treatmentController.createTreatment(
                        treatmentTest

                );

        Treatment treatment = response.getBody();

        assertThat(treatment.getTitle()).isEqualTo("treatmentTest");

        ResponseEntity<String> deletion =
                treatmentController.deleteById(treatment.getTreatmentId());

        assertThat(deletion.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(deletion.getBody())
                .isEqualTo("Deleted Treatment with id: " + treatment.getTreatmentId());
    }
}