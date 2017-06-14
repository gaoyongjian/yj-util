package com.yj.util.security;

/**
 * Created by gaoyj on 2017/6/13.
 */


import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    /** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 2048;

    public static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";

    public static void main(String[] args) {

//        createKey();

        String puk="Sun RSA public key, 2048 bits\n" +
                "  modulus: 23708315316385396629926519474872553572561952756052209049429371848829088500640490386457313358022004775166484493828776986510351963864305247014093308430990745102839056345622394636841668003243104963970941877920897333109441873821378195299663304387724965468924621924119081042882648358045800021134249632552707133974619217936200278950130938684668796261496144148073707222068928917275572184353648769331645710642284689946154483329225724090707724396821031162961110265753320852446395489898413797777668962420846515140619163612528122374075736962728840661532707636981820410350318507231343254711901133590962307567832542671335993593533\n" +
                "  public exponent: 65537";
        String prK="\u0001\u0001\u0001\u0005 \u0004�\u0004�0�\u0004�\u0002\u0001 \u0002�\u0001\u0001 ��X4!҃����i#��l��;l��?P\u0011�8d/�u���\u0010�b\n" +
                "���$\u0013��-��\u000Enx�G�\u0602s��4=F\u001D�\u0015� �Z��l�ԉ��<�\t_KWBzJ\u0016��d�P�v� \u007F��i\u0014��j8ک�x�\u000B����>;Lf��֞[��;���ږ���>��\u0007��\u000BuF0�\"1��6�Q���>�b$��v��Q�M�u�\u0001��N�\u0013X���+�/�Z\\$�1+B�lD�\u0018\u0011�7[7�=�\b�H��+P�;֬��t-\n" +
                "\u001F\"G�\u0004F�\u000B��䡖+\u0019\u001D\u0006.Ɋ��\u000FN�\u0002\u0003\u0001 \u0001\u0002�\u0001 #��xM��>�+�S�Ud���xC�d��' �\u000E\u0019\n" +
                "�&\u0007!�\u000F�\uDB7E\uDFE7�6\u001E%jj�lʶ':�\u0003��vo�N\u0016\u000Fx�]��\u000E[ױ�Wԙ�$�K�\u001DU\u0006��m1��\n" +
                "F ׂ�t\u001F\u001C'S��\u0006X��\u0003b\u000B2Eg����˽�\u0003���\u0012E\u0019�ېd\u001D�&wT\u001E���\u000F<lt\u0013)���\u0012\u001F�/��\"lܤ1��\u0006\u000EM��\u0016�2,K�\u000F�k��a�^+�}8�zI\u0012l&�m�\u0015o�S'W�0|\"���a\u0011H|ǋ\u0002�� ��2G,[\u0019yG��̲\u0018��~\t�j�ʊ\u0018�q�~\u0001���~�kp�zi;���i��h�ר�\\�*%LD�\b�*��h�]t��?��6�T�\u0018q;�~�oqc���.z�3y�����x���ۋ�����\u0013�Z��4I���/���6�*K�\u0002��.\u001A�:ӘJ����\u0014��>��\u007Fag��w5��r��ф��\"�\"I�!b�\"L���\n" +
                "&��6[����\u0005c���;\u001A�D�C�\u001B�\u000EM��\u001Aǣ���Lr~\u0005�X�xb��3�\f�\u0018�ź\u0015P\u001F�^lB)�5�\t���A\u0015�?�;D�p���\t�f������|�T^���\u007F\u001E��{\u0005\u001Bo����Km���\u0002��=���\u0002������5�u�3��e���u��C���U���%���\u000BԦ\u0007�[g�/\u001FF�$�~�v�r<���\u0017��b[��O$~$�� ����j����܋\u0017�[�o�Ts��TWW��&T#��\u000E��m�}y� R�p(��\n";
    }



    public static void createKey(){
        Map<String, byte[]> keyMap = generateKeyBytes();
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        System.out.println(publicKey.toString());
        System.out.println("----------------------");
        System.out.println(privateKey.getEncoded());
        System.out.println(new String(privateKey.getEncoded()));
    }



    public static void  test(){
        Map<String, byte[]> keyMap = generateKeyBytes();

        // 加密
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));

        byte[] encodedText = RSAEncode(publicKey, PLAIN_TEXT.getBytes());
        System.out.println("RSA encoded: " + Base64Util.encode(encodedText));
        System.out.println("RSA encoded: " + Base64.encodeBase64String(encodedText));

        // 解密
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        System.out.println("RSA decoded: "
                + RSADecode(privateKey, encodedText));
    }

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     *
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
//            System.out.println("##########"+publicKey.toString());
            return publicKey;
        } catch (NoSuchAlgorithmException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeySpecException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeySpecException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，三步走。
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(NoSuchPaddingException e){
            e.printStackTrace();
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }
        catch(BadPaddingException e){
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 解密，三步走。
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static String RSADecode(PrivateKey key, byte[] encodedText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(NoSuchPaddingException e){
            e.printStackTrace();
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }
        catch(BadPaddingException e){
            e.printStackTrace();
        }
        return null;

    }
}
