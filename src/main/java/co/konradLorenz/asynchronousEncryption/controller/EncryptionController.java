package co.konradLorenz.asynchronousEncryption.controller;

import co.konradLorenz.asynchronousEncryption.model.Text;
import co.konradLorenz.asynchronousEncryption.service.IEncryptionService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EncryptionController {

    private final IEncryptionService encryption;

    @PostMapping("/encryption")
    public Mono<Text> encryptedText(@RequestBody Text text) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return Mono.just(new Text(encryption.encryption(text.getText())));
    }

}
