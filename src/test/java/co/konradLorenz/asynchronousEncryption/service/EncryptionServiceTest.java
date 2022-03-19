package co.konradLorenz.asynchronousEncryption.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EncryptionServiceTest {

    @Autowired
    private IEncryptionService encryptionService;
    private String text  = "Texto de prueba";

    @Test
    void EncryptionText() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        var encrypted = encryptionService.encryption(text);

        assertNotNull(encrypted);
    }

}
