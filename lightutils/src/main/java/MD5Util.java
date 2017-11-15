import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * todo
 *
 * @author wzm
 * @create 2017-11-15-15:32
 */
public class MD5Util {
    /**
     *modify by wzm   中文有乱码
     * @param plainText
     * @return string
     */
    public static String encryption(String plainText) {
        StringBuffer remd5 = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            int i;

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    remd5.append("0");
                }
                remd5.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
        }
        return remd5.toString();
    }

    /**
     modify by wzm   中文有乱码
     * @param str
     * @return string
     */
    public static String doEncrypt(String str) {
        String enceypt = new String();
        try {
            String key = Encrypt.KEY; // 16λkey
            Encrypt desEncrypt = new Encrypt(key.getBytes());
            enceypt = desEncrypt.doEncrypt(str);
        } catch (Exception e) {
        }
        return enceypt;
    }

    /**
     modify by wzm   中文有乱码
     * @param str
     * @return STRING
     */
    public static String toMD5(String str) {
        String encryptStr = doEncrypt(str);
        String mdStr = encryption(encryptStr);
        return mdStr;
    }

}
