package in.yagnyam.myid.utils;

import lombok.NonNull;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * PEM utils for converting Security Objects like Keys and Certifications to and from Strings for easy Transport and Storing
 */
public class PemUtils {

    public static final String KEY_GENERATION_ALGORITHM = "RSA";
    public static final String PROVIDER_NAME = BouncyCastleProvider.PROVIDER_NAME;

    /**
     * Converts byte content to String
     * @param name Name of the Entity
     * @param content PEM content as String
     * @return PEM object as String
     * @throws IOException if content is invalid
     */
    public static String asPemString(@NonNull String name, @NonNull byte[] content) throws IOException {
        PemObject pemObject = new PemObject(name, content);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        JcaPEMWriter writer = new JcaPEMWriter(new PrintWriter(byteStream));
        writer.writeObject(pemObject);
        // Don't use try with resources here
        writer.close();
        return new String(byteStream.toByteArray(), "UTF-8");
    }

    public static byte[] getPemContent(@NonNull String encodedPemObject) throws IOException {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new ByteArrayInputStream(encodedPemObject.getBytes("UTF-8"))))) {
            return pemReader.readPemObject().getContent();
        } catch (Throwable t) {
            throw new IOException("Invalid Pem Content", t);
        }
    }

    /**
     * Constructs Public Key from PEM encoded String
     * @param encodedKey PEM object as String
     * @return Public Key extracted from input String
     * @throws GeneralSecurityException If input is invalid or missing setup
     * @throws IOException If input is invalid
     */
    public static PublicKey decodePublicKey(@NonNull String encodedKey) throws GeneralSecurityException, IOException {
        KeyFactory factory = KeyFactory.getInstance(KEY_GENERATION_ALGORITHM, PROVIDER_NAME);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(getPemContent(encodedKey));
        return factory.generatePublic(publicKeySpec);
    }

    /**
     * Convert Public Key into PEM encoded String
     * @param publicKey Public Key to be converted to String
     * @return Public Key as String using PEM encoding
     * @throws GeneralSecurityException If input is invalid or missing setup
     * @throws IOException If input is invalid
     */
    public static String encodePublicKey(@NonNull PublicKey publicKey) throws GeneralSecurityException, IOException {
        return asPemString("RSA PUBLIC KEY", publicKey.getEncoded());
    }

    /**
     * Convert PEM encoded String to Private Key
     * @param encodedKey
     * @return Private Key constructed from input string
     * @throws GeneralSecurityException If input is invalid or missing setup
     * @throws IOException If input is invalid
     */
    public static PrivateKey decodePrivateKey(@NonNull String encodedKey) throws GeneralSecurityException, IOException {
        KeyFactory factory = KeyFactory.getInstance(KEY_GENERATION_ALGORITHM, PROVIDER_NAME);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(getPemContent(encodedKey));
        return factory.generatePrivate(privateKeySpec);
    }

    /**
     * Convert private key to String
     * @param privateKey Private key to be converted to string
     * @return PEM encoded string for input private key
     * @throws GeneralSecurityException If input is invalid or missing setup
     * @throws IOException If input is invalid
     */
    public static String encodePrivateKey(@NonNull PrivateKey privateKey) throws GeneralSecurityException, IOException {
        return asPemString("RSA PRIVATE KEY", privateKey.getEncoded());
    }

}
