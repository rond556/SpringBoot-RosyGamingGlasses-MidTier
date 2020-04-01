package com.rosygamingglassesmidtier.midtier.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws Exception {
        try {
            keyStore = KeyStore.getInstance("Jks");
            InputStream resourceAsStream = getClass().getResourceAsStream("/seenIt.jks");
            keyStore.load(resourceAsStream, "password".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new Exception("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) throws Exception {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() throws Exception {
        try {
            return (PrivateKey) keyStore.getKey("seenIt", "password".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new Exception("Exception occurred while retrieving private key from keystore");
        }
    }

    public boolean validateToken(String jwt) throws Exception {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() throws Exception {
        try {
            return keyStore.getCertificate("rosygamingglasses").getPublicKey();
        } catch (KeyStoreException e) {
            throw new Exception("Exception occurred while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }
}
