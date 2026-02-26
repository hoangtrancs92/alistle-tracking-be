package alistle.com.identifyservice.infrastructure.security;

import alistle.com.identifyservice.application.exception.AppException;
import alistle.com.identifyservice.application.exception.ErrorCode;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    @NonFinal
    @Value("${spring.jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    /**
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

        /**
     * Validates a JWT token.
     *
     * <p>This method parses the given token and checks whether it is expired.
     * If the token cannot be parsed or is expired, the method returns false.</p>
     *
     * @param token the JWT token string to validate
     * @return true if the token is successfully parsed and not expired;
     *         false if the token is invalid or expired
     */
    public boolean validateToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            SignedJWT signedJWT = parseToken(token);

            boolean isValidSignature = signedJWT.verify(verifier);
            if (!isValidSignature) {
                return false;
            }

            return isTokenExpired(signedJWT);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

//        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }


    public String refreshToken(String token) throws JOSEException, ParseException {
            SignedJWT signedJWT = verifyToken(token, true);
            if (isTokenExpired(signedJWT)) {
                String email = signedJWT.getJWTClaimsSet().getSubject();
                return generateToken(email);
            } else {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return !expirationTime.before(new Date());
    }

    /*
     * Extract the user email from the JWT token.
     */
    private SignedJWT parseToken(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate a refresh token for the given user email.
     *
     * <p>
     * This method creates a signed JWT (JSON Web Token) using HMAC SHA-256 algorithm.
     * The refresh token contains:
     * </p>
     *
     * <ul>
     *     <li><b>sub (subject)</b>: user's email</li>
     *     <li><b>iat (issued at)</b>: token creation time</li>
     *     <li><b>exp (expiration time)</b>: token expiration time based on REFRESHABLE_DURATION</li>
     *     <li><b>type</b>: set to "refresh" to distinguish from access token</li>
     * </ul>
     *
     * <p>
     * The token is signed using the configured SECRET_KEY to ensure integrity and authenticity.
     * The final result is returned in compact serialized format,
     * which can be safely transmitted to the client.
     * </p>
     *
     * @param email the user's email (used as JWT subject)
     * @return a signed refresh token in compact JWT format
     * @throws RuntimeException if signing process fails
     */
    public String generateRefreshToken(String email) {
        // Create JWT header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // Create JWT claims for payload
        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(REFRESHABLE_DURATION)))
                .claim("type", "refresh")// 7 days expiration
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimSet.toJSONObject()));
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
