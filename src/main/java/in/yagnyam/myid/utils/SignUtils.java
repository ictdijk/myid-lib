package in.yagnyam.myid.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import java.security.*;

/**
 * Utilities for Signing and Verification
 */
@Slf4j
public class SignUtils {

    public static final String ALGORITHM_SHA256WithRSA = "SHA256WithRSA";
    public static final String ALGORITHM_MD5WithRSA = "MD5withRSA";

    public static final String ALGORITHM_SHA256 = "SHA-256";
    public static final String ALGORITHM_MD5 = "MD5";

    /**
     * Construct Signature for given content using given algorithm and private key
     *
     * @param content    Content to be signed
     * @param algorithm  Algorithm to use for Signing
     * @param privateKey Private key for signing
     * @return Signature as String
     */
    public static String getSignature(String content, String algorithm, PrivateKey privateKey) {
        try {
            Signature signer = getSignatureInstance(algorithm);
            signer.initSign(privateKey);
            signer.update(content.getBytes());
            return Base64.toBase64String(signer.sign());
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("failed to generate signature for {} with algorithm {} and private key {}", content, algorithm, privateKey);
            throw new RuntimeException(e);
        }
    }

    /**
     * Verify the signature using Public Key
     *
     * @param content   Content to be verified with given signature
     * @param algorithm Algorithm to use for Verification
     * @param publicKey Public key for Verification
     * @param signature Signature to Verify
     * @return true if signature mathces, false otherwise
     */
    public static boolean verifySignature(String content, String algorithm, PublicKey publicKey, String signature) {
        try {
            Signature verifier = getSignatureInstance(algorithm);
            verifier.initVerify(publicKey);
            verifier.update(content.getBytes());
            return verifier.verify(Base64.decode(signature));
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("failed to verify signature for {} with algorithm {}, public key {} and signature {}", content, algorithm, publicKey, signature);
            throw new RuntimeException(e);
        }
    }

    /**
     * Proive Signature instance for given algorithm
     *
     * @param algorithm of Singature
     * @return Signature Instance
     * @throws NoSuchProviderException  If BC is not setup property
     * @throws NoSuchAlgorithmException If Invalid Algorithm provided as input
     */
    public static Signature getSignatureInstance(@NonNull String algorithm) throws NoSuchProviderException, NoSuchAlgorithmException {
        return Signature.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
    }


    /**
     * Get Hash for given content using given algorithm
     *
     * @param content   Content to find Signature
     * @param algorithm Algorithm to generate Hash
     * @return Hash for given inpu
     */
    public static String getHash(@NonNull String content, @NonNull String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(content.getBytes());
            return Base64.toBase64String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("failed to find hash for {} using {}", content, algorithm);
            throw new RuntimeException(e);
        }
    }

}
