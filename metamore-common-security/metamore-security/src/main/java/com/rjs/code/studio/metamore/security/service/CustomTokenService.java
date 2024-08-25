package com.rjs.code.studio.metamore.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class CustomTokenService {

    @Value("token.key")
    private String SECRET_KEY ;

    @Value("token.expiry")
    private long TOKEN_EXPIRY ;

    public String generateToken(String username) {
        long timestamp = System.currentTimeMillis();
        String tokenData = username + ":" + timestamp;
        String signature = generateSignature(tokenData);

        return Base64.getEncoder().encodeToString((tokenData + ":" + signature).getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        String[] parts = decodedToken.split(":");
        return parts[0];
    }

    public boolean validateToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        String[] parts = decodedToken.split(":");

        if (parts.length != 3) {
            return false;
        }

        String username = parts[0];
        long timestamp = Long.parseLong(parts[1]);
        String providedSignature = parts[2];

        long currentTime = System.currentTimeMillis();
        if (currentTime - timestamp > TOKEN_EXPIRY) {
            return false;
        }

        String tokenData = username + ":" + timestamp;
        String expectedSignature = generateSignature(tokenData);

        return providedSignature.equals(expectedSignature);
    }

    private String generateSignature(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC signature", e);
        }
    }


}
