package com.itciod.goosesystem;

import com.itciod.goosesystem.pojo.Enum.Androidstaue;
import com.itciod.goosesystem.pojo.Enum.Identity;
import com.itciod.goosesystem.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class GooseSystemApplicationTests {

    @Test
    void contextLoads() {
        User user = new User(1,"张三","666", Identity.ADMIN, Androidstaue.ACTIVATED,null);
        System.out.println(user);
        System.out.println(user.getIdentity());
    }

    /**
     * 生成JWT
     */
    @Test
    public void testGenJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","jerry");


        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"itcioditcioditcioditcioditcioditcioditcioditcioditcioditcioditcioditciod")//签名算法
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))//设置有效期为1h
                .compact();
        System.out.println(jwt);

    }

    /**
     * 解析JWT
     */
    @Test
    public void testParseJwt(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcyNjk4Mzc2Nn0.NSikifZpIo0McSqJMZGn22x2eRiKbYtkasbOwb2qGKo";

        Claims claims = Jwts.parser()
                .setSigningKey("itcioditcioditcioditcioditcioditcioditcioditcioditcioditcioditcioditciod")
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println(claims);
    }
}
