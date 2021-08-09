import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wangp.srb.ApplicationSMS;
import org.wangp.srb.sms.util.SmsProperties;
import sun.nio.cs.UnicodeEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.function.UnaryOperator;

@SpringBootTest(classes = ApplicationSMS.class)
@RunWith(SpringRunner.class)
public class UtilsTests {

    @Test
    public void testProperties(){
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.KEY_SECRET);
        System.out.println(SmsProperties.REGION_Id);
        String signName = SmsProperties.SIGN_NAME;
        System.out.println("signName = " + signName);
        try {
            String decode = URLDecoder.decode(signName, "utf-8");
            System.out.println("decode = " + decode);
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
    }
}