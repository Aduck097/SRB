import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * 2021/8/5
 *
 * @author PingW
 */
public class JwtTest {

    //过期时间，毫秒，24小时
    private static long tokenExpiration = 24*60*60*1000;
    //秘钥
    private static String tokenSignKey = "atguigu123";

    @Test
    public void testCreateToken(){
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") //令牌类型
                .setHeaderParam("alg", "HS256") //签名算法

                .setSubject("guli-user") //令牌主题
                .setIssuer("atguigu")//签发者
                .setAudience("atguigu")//接收者
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 20*1000)) //20秒后可用
                .setId(UUID.randomUUID().toString())

                .claim("nickname", "Helen")
                .claim("avatar", "1.jpg")

                .signWith(SignatureAlgorithm.HS256, tokenSignKey)//签名哈希
                .compact(); //转换成字符串

        System.out.println(token);
    }
    @Test
    public void testGetUserInfo(){

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpc3MiOiJhdGd1aWd1IiwiYXVkIjoiYXRndWlndSIsImlhdCI6MTYyODEyODU2OSwiZXhwIjoxNjI4MjE0OTY5LCJuYmYiOjE2MjgxMjg1ODksImp0aSI6ImQ1Y2E1NWYxLWNmOWYtNDhkNC05YjcxLWZkOWExNjQxMGViNCIsIm5pY2tuYW1lIjoiSGVsZW4iLCJhdmF0YXIiOiIxLmpwZyJ9.9zOHIyVNPHqUAakSbB6Me63_mSyBkNix6fivK_AAybw";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        String subject = claims.getSubject();
        String issuer = claims.getIssuer();
        String audience = claims.getAudience();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        Date notBefore = claims.getNotBefore();
        String id = claims.getId();

        System.out.println(subject);
        System.out.println(issuer);
        System.out.println(audience);
        System.out.println(issuedAt);
        System.out.println(expiration);
        System.out.println(notBefore);
        System.out.println(id);;

        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");

        System.out.println(nickname);
        System.out.println(avatar);
    }
}