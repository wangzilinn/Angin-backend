package com.***REMOVED***.site.integration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SslUtil {
    public static SSLSocketFactory getSocketFactory() throws Exception {


        File file = ResourceUtils.getFile("classpath:certs/server-cert.pem");

        Security.addProvider(new BouncyCastleProvider());
        X509Certificate caCert = null;

//        BufferedInputStream bis = new BufferedInputStream(interMediateCrtFileInputStream);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

//        while (bis.available() > 0) {
        caCert = (X509Certificate) cf.generateCertificate(new FileInputStream(file));
//        }
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(caKs);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext.getSocketFactory();
    }
}
