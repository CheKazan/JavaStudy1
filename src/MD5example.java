import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class MD5example {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {
        byte[] content = byteArrayOutputStream.toByteArray();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] content2 =md.digest(content);
        StringBuilder content3 = new StringBuilder();
        for(byte b: content2){
            content3.append(String.format("%02X",b));
        }
        return md5.toUpperCase().equals(content3.toString());
    }
}
