package alistle.com.identifyservice.infrastructure.security;

import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

import com.nimbusds.jose.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtil {
    @NonFinal
    @Value("${spring.jwt.secret}")
    String SECRET_KEY;

    @NonFinal
    @Value("${spring.jwt.expiration}")
    Long EXPIRATION_TIME;

    /*
     * Generate a JWT token for the given username.
     */
    public String generateToken(String email) {
        // Create JWT header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // Create JWT claims for payload
        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME))) // 1 hour expiration
                .build();
        Payload payload = new Payload(claimSet.toJSONObject());

        // From header and payload, create combined JWT object
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            // Apply the HMAC protection with key
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            // Detect the JWT compact serialization from complex JWT object to a string format that can be easily transmitted
            // Serialize to compact form, produces something like "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsIml."
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
