package org.example.bookreservationapi.security;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.JWSAlgorithm;

@RestController
class JwkSetEndpointController {
    private final RsaKeyProperties rsaKeyProperties;

    JwkSetEndpointController(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> jwks() {
        RSAPublicKey publicKey = rsaKeyProperties.publicKey();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID("my-key-id")   // should match the JWT header kid
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .build();

        Map<String, Object> body = new JWKSet(rsaKey).toJSONObject();

        return ResponseEntity.ok(body);
    }
}