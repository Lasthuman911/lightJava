import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * todo
 *
 * @author wzm
 * @create 2017-11-15-15:33
 */
public class Encrypt {

    public static final String DES = "DES"; // DES
    public static final String PADDING = "DES/ECB/PKCS5Padding"; // modify by wzm   中文有乱码
    public static final String NOPADDING = "DES/ECB/NoPadding"; // modify by wzm   中文有乱码
    public static final String KEY = "0123456789ABCDEF";

    // modify by wzm   中文有乱码
    private String desType = DES;

    private byte[] desKey;

    /**

     * modify by wzm   中文有乱码
     * <p>Title: </p>
     * <p>Description: </p>
     * @param desKey
     */
    public Encrypt(byte[] desKey) {
        this.desKey = desKey;
    }

    /**modify by wzm   中文有乱码
     * @param plainText
     * @return byte[]
     * @throws Exception
     */
    public byte[] doEncrypt(byte[] plainText) throws Exception {

        SecureRandom random = new SecureRandom();
        // //new SecureRandom();
        byte[] rawKeyData = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(desType);
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, random);
        byte[] data = plainText;
        byte[] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }

    /** modify by wzm   中文有乱码
     * @param text
     * @return string
     * @throws Exception
     */
    public String doEncrypt(String text) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] rawKeyData = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(desType);
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, random);
        byte[] encryptedData = cipher.doFinal(text.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int n = 0; n < encryptedData.length; n++) {
            String stmp = (java.lang.Integer.toHexString(encryptedData[n] & 0XFF));
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString().toUpperCase();
    }

}
