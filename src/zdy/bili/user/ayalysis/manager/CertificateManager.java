package zdy.bili.user.ayalysis.manager;

import okhttp3.CertificatePinner;
import okio.Buffer;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

public final class CertificateManager {
    private static final CertificateManager INSTANCE = new CertificateManager();

    private SSLSocketFactory sslSocketFactory;
    private X509TrustManager trustManager;

    private CertificateManager() {
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public static CertificateManager getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {
        String zhihucer = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIFBzCCA++gAwIBAgIQB++J1/oOvP9jhdzDz+1qjDANBgkqhkiG9w0BAQsFADBE\n" +
                "MQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMU\n" +
                "R2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTQwOTI3MDAwMDAwWhcNMTgwNTA2MjM1\n" +
                "OTU5WjCBijELMAkGA1UEBhMCQ04xDzANBgNVBAgMBuWMl+S6rDEQMA4GA1UEBwwH\n" +
                "YmVpamluZzEpMCcGA1UECgwgWkhJWkhFIFNJSEFJKEJFSUpJTkcpIFRFQ0hOT0xP\n" +
                "R1kxFzAVBgNVBAsMDkluZnJhc3RydWN0dXJlMRQwEgYDVQQDDAsqLnpoaWh1LmNv\n" +
                "bTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALidJfNeKTQoNoaHlYs8\n" +
                "EamL6hbwsTu2EgROr0A0Jxc8jDN6OT6NYIX88WL0T/+aCv55m6BbZpzGh94yb+tL\n" +
                "LeoTonIM34adtf6tS3IzvAucKFQaRDyytpjLpLwrUCjGQ0aIoedspwzo8XQWDDEG\n" +
                "lkwdPit1uv9wysb8g7zW3l36jbSV24oPM6D5k5IHwE7hy6SniafG15gXUKZVa+bV\n" +
                "XsXHyrllQsf7ripX3BETIM0cTqvrzEhRScW59ieo5Hywa3ecqr0XwJZgKWmq6GOc\n" +
                "iV7swpq1NcZL5C4jRmjwYcOsZ++p5kUvMtWxaODeJFstrL0B+X9ipH8BW6iFkNog\n" +
                "cCcCAwEAAaOCAawwggGoMCEGA1UdEQQaMBiCCyouemhpaHUuY29tggl6aGlodS5j\n" +
                "b20wCQYDVR0TBAIwADAOBgNVHQ8BAf8EBAMCBaAwKwYDVR0fBCQwIjAgoB6gHIYa\n" +
                "aHR0cDovL2duLnN5bWNiLmNvbS9nbi5jcmwwgaEGA1UdIASBmTCBljCBkwYKYIZI\n" +
                "AYb4RQEHNjCBhDA/BggrBgEFBQcCARYzaHR0cHM6Ly93d3cuZ2VvdHJ1c3QuY29t\n" +
                "L3Jlc291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMEEGCCsGAQUFBwICMDUMM2h0dHBz\n" +
                "Oi8vd3d3Lmdlb3RydXN0LmNvbS9yZXNvdXJjZXMvcmVwb3NpdG9yeS9sZWdhbDAd\n" +
                "BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHwYDVR0jBBgwFoAU0m/3lvSF\n" +
                "P3I8MH0j2oV4m6N8WnwwVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNodHRw\n" +
                "Oi8vZ24uc3ltY2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vZ24uc3ltY2IuY29t\n" +
                "L2duLmNydDANBgkqhkiG9w0BAQsFAAOCAQEAFvvMWanpGou2phspvO14VcXZ9dNo\n" +
                "Fl1qFZxcBBJL1ZM3U6lh7BDC42Um27giPvGdrKQ0fCNgBRSlbPvEJLvgnIQfRjBs\n" +
                "X+sQ7bNWrDTUl52N8FxSQbKOucHYTnEiUMzzQHXS6lBv7EE5iscOB5DEtLT/Qld9\n" +
                "D89ACO+bDCSl3/AI+YWPtnt3I88oRRII9m0o5ez161LbcD4sV9zQmqAcEedvPqi+\n" +
                "+asXuZcIQVL44Qg6+HXOJSclxj1GMPrrW9yHhoHImFcfMrZ0Fn/rKu5V9p+epNSP\n" +
                "7PCOPwqyBWRN7pVUzDwZKvVBWKRMn5BmoUqqmMf2vqXRmUJrL40Fm3N+kw==\n" +
                "-----END CERTIFICATE-----\n";

        String jikecer = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIFzjCCBLagAwIBAgIQHSrOB6vT4SI71nRNDjzIsTANBgkqhkiG9w0BAQsFADCB\n" +
                "lzELMAkGA1UEBhMCQ04xJTAjBgNVBAoTHFRydXN0QXNpYSBUZWNobm9sb2dpZXMs\n" +
                "IEluYy4xHzAdBgNVBAsTFlN5bWFudGVjIFRydXN0IE5ldHdvcmsxHTAbBgNVBAsT\n" +
                "FERvbWFpbiBWYWxpZGF0ZWQgU1NMMSEwHwYDVQQDExhUcnVzdEFzaWEgRFYgU1NM\n" +
                "IENBIC0gRzUwHhcNMTYxMDMxMDAwMDAwWhcNMTcxMDMxMjM1OTU5WjAgMR4wHAYD\n" +
                "VQQDDBVhcHAuamlrZS5ydWd1b2FwcC5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IB\n" +
                "DwAwggEKAoIBAQCmaUiXc9YifIH0Jyb9pmteo/pAHCbDxj03Kye68LG9alWuDIJJ\n" +
                "FwtIoUHZLYxNagRQTpb0do0gP/3y4Gfv2r1c86F7LB8uvjnUYe5kKi1/q0BQ+oZP\n" +
                "0rwPKbme/0r8B3a6EhIJxVr0aq726TlEVgMydTYG9ymyIJc54tRiqLagz5HWOn+f\n" +
                "ptA2ta53ibSFYFrfD4NGDJtqEep4eMXc2kUX2gFl/XVr3XwIk+4Au/kMDquBTfRk\n" +
                "n9Xv0aI5ucOgfHd9qqs3CryVFYKH6UCjjHUzyinaStAoC3ze49mjpUMhYfl/hYIE\n" +
                "QP/Swt+ONKDL/yRqC9/Apgzgr2/g49Wbjp5tAgMBAAGjggKKMIIChjAgBgNVHREE\n" +
                "GTAXghVhcHAuamlrZS5ydWd1b2FwcC5jb20wCQYDVR0TBAIwADBhBgNVHSAEWjBY\n" +
                "MFYGBmeBDAECATBMMCMGCCsGAQUFBwIBFhdodHRwczovL2Quc3ltY2IuY29tL2Nw\n" +
                "czAlBggrBgEFBQcCAjAZDBdodHRwczovL2Quc3ltY2IuY29tL3JwYTAfBgNVHSME\n" +
                "GDAWgBRtWMd/GufhPy6mjJc1Qrv00zisPzAOBgNVHQ8BAf8EBAMCBaAwHQYDVR0l\n" +
                "BBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMIGbBggrBgEFBQcBAQSBjjCBizA8Bggr\n" +
                "BgEFBQcwAYYwaHR0cDovL3RydXN0YXNpYTItb2NzcC5kaWdpdGFsY2VydHZhbGlk\n" +
                "YXRpb24uY29tMEsGCCsGAQUFBzAChj9odHRwOi8vdHJ1c3Rhc2lhMi1haWEuZGln\n" +
                "aXRhbGNlcnR2YWxpZGF0aW9uLmNvbS90cnVzdGFzaWFnNS5jcnQwggEEBgorBgEE\n" +
                "AdZ5AgQCBIH1BIHyAPAAdQDd6x0reg1PpiCLga2BaHB+Lo6dAdVciI09EcTNtuy+\n" +
                "zAAAAVgYtHUbAAAEAwBGMEQCIDTqYeiLJy5eNbH50qlRS0HlehMu0JVsWTk4xMvU\n" +
                "HJOFAiAaYOST08M0+gjETv043XX/QtZSkPzgA9Zv8CsF6WroMgB3AGj2mPgfZIK+\n" +
                "OozuuSgdTPxxUV1nk9RE0QpnrLtPT/vEAAABWBi0dToAAAQDAEgwRgIhANh/EwP/\n" +
                "FXwJOY2SzSipuDvPf3+5plzpCvPjY+6SJBygAiEA1WB+CdoXfUOLWEOrLS3Wa7xL\n" +
                "TuI1SgPsyR/f4IJjni4wDQYJKoZIhvcNAQELBQADggEBAHlAF22C00nG4NdxA+6q\n" +
                "nfRZLeJVerxJI5MXm2E+8mpDqTfiKUf/Q2Mk0HBYVKTtBbQ8zMa816TB37EhtQWr\n" +
                "eRWpO5LR+nGs6Va7pPbtcu49+mJvzBVj+WAWPJGZuz+boJserztQB85gSz2LbJGX\n" +
                "qprib0XnQi7rkfhIK6J7u9tk35+wdMjYb9xOD4G23booxWD4NCWlbg2XsmE7+2Nk\n" +
                "XlmkUbKdpFzt7BxnYgzjOJcnGoB1hZPalUO6lXLbsg8q6r+Ch1OBt7q6+MPtf9yY\n" +
                "6jExvxScdeOBLSBmIj0e8suPCcOEGpvAuEICNUALnZfBSdDZngjzQgmmLbPCXdz9\n" +
                "QNI=\n" +
                "-----END CERTIFICATE-----\n";

        return new Buffer()
                .writeUtf8(zhihucer)
                .writeUtf8(jikecer)
                .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}