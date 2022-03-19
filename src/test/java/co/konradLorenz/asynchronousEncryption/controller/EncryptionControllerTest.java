package co.konradLorenz.asynchronousEncryption.controller;

import co.konradLorenz.asynchronousEncryption.model.Text;
import co.konradLorenz.asynchronousEncryption.service.impl.EncryptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(EncryptionController.class)
public class EncryptionControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private EncryptionServiceImpl encryptionService;

    @Test
    void testEncryptionText() throws Exception {
        var req = new Text("Text to encrypted");
        var expected = "fjsdghfsdfgreuivbdcjsbvdsgkdsajfgdñghdfñgh";
        Mockito.when(encryptionService.encryption(req.getText()))
                .thenReturn(expected);
         webClient
                .post()
                .uri("/api/encryption")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(req), Text.class)
                 .exchange()
                         .expectBody(Text.class);
    }
}
