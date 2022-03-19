package co.konradLorenz.asynchronousEncryption.service.impl;

import co.konradLorenz.asynchronousEncryption.exception.EncryptionException;
import co.konradLorenz.asynchronousEncryption.service.IEncryptionService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class EncryptionServiceImpl implements IEncryptionService {

    private final KeyPair keyPair;
    private static final int SIZE_WORD_ENCRYPTED = 250;

    @Override
    public String encryption(String text) {
        return splitText(text)
                .map(this::encryptPart)
                .collect(Collectors.joining());
    }

    public Stream<byte[]> splitText(String text){
        return Lists.partition(Arrays.asList(text.split("")), SIZE_WORD_ENCRYPTED)
                .stream()
                .map(this::listStringToBytes)
                .collect(Collectors.toList())
                .stream();

    }

    public String encryptPart(byte[] bytes) {
        try {
            var rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            return Hex.encodeHexString(rsa.doFinal(bytes));
        } catch (IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new EncryptionException("Fail to encrypted", e);
        }

    }

    private byte[] listStringToBytes(List<String> strings){
        return String.join("", strings).getBytes(StandardCharsets.UTF_8);
    }

}
