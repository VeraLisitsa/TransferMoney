package com.example.transfermoney;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class TransferMoneyApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final GenericContainer<?> test = new GenericContainer<>("transfermoney:1.0");

    @Test
    protected void successOperationTest() throws Exception {

        this.mockMvc.perform(post("/transfer").
                        contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"cardFromNumber\": \"1111222233334444\"," +
                                "\"cardFromValidTill\": \"12/24\"," +
                                "\"cardFromCVV\": \"111\"," +
                                "\"cardToNumber\": \"5555666677778888\"," +
                                "\"amount\": { \"value\": 1000," +
                                "\"currency\": \"rub\"} }"))
                .andExpect(status().isOk());

    }

    @Test
    protected void errorInputDataOperationTest() throws Exception {

        this.mockMvc.perform(post("/transfer").
                        contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"cardFromNumber\": \"1111222233334445\"," +
                                "\"cardFromValidTill\": \"12/24\"," +
                                "\"cardFromCVV\": \"111\"," +
                                "\"cardToNumber\": \"5555666677778888\"," +
                                "\"amount\": { \"value\": 1000," +
                                "\"currency\": \"rub\"} }"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    protected void errorTransferOperationTest() throws Exception {

        this.mockMvc.perform(post("/transfer").
                        contentType("application/json")
                        .content("{ \"cardFromNumber\": \"1111222233334444\"," +
                                "\"cardFromValidTill\": \"12/24\"," +
                                "\"cardFromCVV\": \"111\"," +
                                "\"cardToNumber\": \"5555666677778888\"," +
                                "\"amount\": { \"value\": 1000000000," +
                                "\"currency\": \"rub\"} }"))
                .andExpect(status().is5xxServerError());

    }

    @Test
    protected void errorConfirmationTest() throws Exception {
        this.mockMvc.perform(post("/confirmOperation")
                        .contentType("application/json")
                        .content("{\"operationId\": \"2\", \"code\": \"0000\"}"))
                .andExpect(status().is5xxServerError());
    }


}
