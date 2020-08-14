package ru.simbirsoft.warehouse_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.simbirsoft.warehouse_management.service.ReportService;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReportControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private LocalDateTime start;
  private LocalDateTime end;

  @MockBean ReportService reportService;

  @BeforeEach
  void before() {
    start = LocalDateTime.now().minusWeeks(1);
    end = LocalDateTime.now();
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void shopsReport() throws Exception {
    given(reportService.getAllShopsOrderReport(start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=orders.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void shopsReportWithWrongTimeFormat() throws Exception {
    given(reportService.getAllShopsOrderReport(start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", "THIS IS WRONG TIME VALUE")
                .param("end time", String.valueOf(end)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void shopReportById() throws Exception {
    given(reportService.getOneShopOrderReport(1L, start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/sales/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=order.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void shopReportByIdWithWrongTimeFormat() throws Exception {
    given(reportService.getOneShopOrderReport(1L, start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/sales/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", "WRONG TIME")
                .param("end time", String.valueOf(end)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void writeoffReportAllWarehouses() throws Exception {
    given(reportService.getAllWarehousesWriteoffReport(start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/writeoffs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=writeoffs.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void writeoffReportByWarehouseId() throws Exception {
    given(reportService.getOneWarehouseWriteoffReport(1L, start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/writeoffs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=writeoff.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void allShopsRevenueReport() throws Exception {
    given(reportService.getAllShopsRevenueReport(start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=revenues.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void shopRevenueReport() throws Exception {
    given(reportService.getOneShopRevenueReport(1L, start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/revenues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=revenue.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void allShopAverageCheckReport() throws Exception {
    given(reportService.getAllShopsAverageCheckReport(start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/averageCheck")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=averageChecks.pdf"));
  }

  @Test
  @WithMockUser(authorities = "WAREHOUSE_KEEPER")
  void oneShopAverageCheckReport() throws Exception {
    given(reportService.getOneShopAverageCheckReport(1L, start, end))
        .willReturn(new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray()));
    mockMvc
        .perform(
            get("/report/averageCheck/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start time", String.valueOf(start))
                .param("end time", String.valueOf(end)))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PDF))
        .andExpect(
            MockMvcResultMatchers.header()
                .string("Content-Disposition", "attachment; filename=averageCheck.pdf"));
  }
}
