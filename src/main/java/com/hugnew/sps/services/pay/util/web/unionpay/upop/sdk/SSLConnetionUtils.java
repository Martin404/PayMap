package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLConnetionUtils {

    private static final String upopHost = "query.unionpaysecure.com";
    private static final int HTTPS_PORT = 443;

    // 要导入的证书的别名
    private static final String certFileAlias = "unionpaysecure";

    // Java Trust Store密码，初始密码为changeit
    private static final String trustStorePassword = "changeit";

    // Java Trust Store的路径
    private static final String JAVA_HOME = "C:\\Program Files\\Java\\jdk1.6.0_21\\jre";
    private static final String JAVA_HOME_SYS = System.getProperty("java.home");
    private static final String trustStoreFileRelativePath = "\\lib\\security\\cacerts";

    /**
     * 导入证书到java key store
     *
     * @param certPath    要导入的证书的路径
     * @param jksPath     Trust Store的路径
     * @param jksPassword Trust Store密码
     */
    public static void importCertificate(String jksPath, String jksPassword) {
        try {
            Certificate certificate = getUpopCertificate();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, jksPassword.toCharArray());
            setRemainCertificates(ks, jksPath, jksPassword);
            ks.setCertificateEntry(certFileAlias, certificate);
            ks.store(new FileOutputStream(jksPath), jksPassword.toCharArray());
            System.out.println("Import Certificate " + certFileAlias + " Successful!");
        } catch (Exception e) {
            throw new RuntimeException("Exception while importing certificate to Java Key Store for unionpaysecure certificate.",
                    e);
        }
    }

    /**
     * 导入证书到java key store
     *
     * @param certificate
     */
    public static void importCertificate() {
        try {
            String jksPath = JAVA_HOME_SYS + trustStoreFileRelativePath;
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, trustStorePassword.toCharArray());
            setRemainCertificates(ks, jksPath, trustStorePassword);
            Certificate certificate = getUpopCertificate();
            ks.setCertificateEntry(certFileAlias, certificate);
            ks.store(new FileOutputStream(jksPath), trustStorePassword.toCharArray());
            System.out.println("Import Certificate " + certFileAlias + " Successful!");
        } catch (Exception e) {
            throw new RuntimeException("Exception while importing certificate to Java Key Store for unionpaysecure certificate.",
                    e);
        }
    }

    /**
     * 保留原来java key store中的证书
     *
     * @param ks
     * @param jksPath
     * @param jksPassword
     */
    private static void setRemainCertificates(KeyStore ks, String jksPath, String jksPassword) {
        try {
            FileInputStream in = new FileInputStream(jksPath);
            KeyStore ks4RemainCerts = KeyStore.getInstance("JKS");
            Certificate c = null;
            ks4RemainCerts.load(in, jksPassword.toCharArray());
            Enumeration<String> e = ks4RemainCerts.aliases();
            String alias;
            while (e.hasMoreElements()) {
                alias = (String) e.nextElement();
                c = ks4RemainCerts.getCertificate(alias);
                ks.setCertificateEntry(alias, c);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception while setting remain certificates to Java Key Store for unionpaysecure certificate.", e);
        }
    }

    /**
     * 删除某个证书
     *
     * @param alias
     * @param jksPath
     * @param jksPassword
     */
    public static void deleteCertificate(String alias, String jksPath, String jksPassword) {
        try {
            FileInputStream in = new FileInputStream(jksPath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, jksPassword.toCharArray());
            if (ks.containsAlias(alias)) {
                ks.deleteEntry(alias);
                ks.store(new FileOutputStream(jksPath), jksPassword.toCharArray());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while deleting certificate from Java Key Store.", e);
        }
    }

    /**
     * 删除某个证书
     *
     * @param alias
     */
    public static void deleteCertificate(String alias) {
        try {
            String jksPath = JAVA_HOME_SYS + trustStoreFileRelativePath;
            FileInputStream in = new FileInputStream(jksPath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, trustStorePassword.toCharArray());
            if (ks.containsAlias(alias)) {
                ks.deleteEntry(alias);
                ks.store(new FileOutputStream(jksPath), trustStorePassword.toCharArray());
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while deleting certificate from Java Key Store.", e);
        }
    }

    /**
     * 打印证书信息
     *
     * @param jksPath
     * @param jksPassword
     */
    public static void printCertificateInfo(String jksPath, String jksPassword) {
        try {
            FileInputStream in = new FileInputStream(jksPath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, jksPassword.toCharArray());
            Enumeration<String> e = ks.aliases();
            String alias;
            while (e.hasMoreElements()) {
                alias = (String) e.nextElement();
                if (certFileAlias.equals(alias)) {
                    System.out.println("Your imported certificate " + alias);
                    Certificate c = ks.getCertificate(alias);
                    System.out.println("Content: " + c.toString());
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while printing certificates in Java Key Store.", e);
        }
    }

    /**
     * 打印证书信息
     */
    public static void printCertificateInfo() {
        try {
            String jksPath = JAVA_HOME_SYS + trustStoreFileRelativePath;
            FileInputStream in = new FileInputStream(jksPath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, trustStorePassword.toCharArray());
            Enumeration<String> e = ks.aliases();
            String alias;
            while (e.hasMoreElements()) {
                alias = (String) e.nextElement();
                if (certFileAlias.equals(alias)) {
                    System.out.println("Your imported certificate " + alias);
                    Certificate c = ks.getCertificate(alias);
                    System.out.println("Content: " + c.toString());
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while printing certificates in Java Key Store.", e);
        }
    }

    /**
     * 从银联在线支付网站获取证书
     *
     * @return
     */
    public static Certificate getUpopCertificate() {
        try {
            TrustManager trm = new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{trm}, null);
            SSLSocketFactory factory = sc.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket(upopHost, HTTPS_PORT);
            socket.startHandshake();
            SSLSession session = socket.getSession();
            Certificate[] servercerts = session.getPeerCertificates();
            socket.close();
            return servercerts[0];
        } catch (Exception e) {
            throw new RuntimeException("Exception while get upop certificate.", e);
        }
    }

    public static void main(String[] args) throws Exception {
//		String jksFilePath = JAVA_HOME + trustStoreFileRelativePath;
//		deleteCertificate(certFileAlias, jksFilePath, trustStorePassword);
//		printCertificateInfo(jksFilePath, trustStorePassword);
//		importCertificate(jksFilePath, trustStorePassword);
//		printCertificateInfo(jksFilePath, trustStorePassword);

        deleteCertificate(certFileAlias);
        importCertificate();
        printCertificateInfo();
    }
}
