package com.example.home_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final String SECRET_KEY = "77f1e3166e872188609a548db949f92c12fcfc6176de1081345f5f777a16336ea21e1515e8b482f9c2e0898575ec1217d4f91e6863f67f8ef93750780d2316483ebfbc3a02ff1be07d93e071051f5c8c81065556684f2e218ecf8ee5527d24c08c31aa60a97f5d14f36b68d99d6be104e091b5d9fc0d5d01b2d7819ef90375de0329bfbe67d6a36fca9c91fc49fc89f05ec3f7754a2e19c2a0758d5986eaee71f098a29c60f7a996c959aee23a6170c767465eb4d66c48eddb786b24c90df925c10ef30ee90a4e1415a67f8dd36a7ea8f833695249edb512d42189c10c21ac99b3a4edcbcd29b1db97f5378a08f0c5df0673f4149b3d8e38c9d9402e3ca636117977992d3a5caeb38155d2c92e4eaa8e769204308a4134776861b7a409b38a852ad3cc4157037a0d6d20cb2a7f0918c6e2c2183dbb695164bb19937d35f6d363bb973773605f13083b4f34472caadf7421de14ca5acfc18d6ce5688f01328230342b4a66a848f3e2712736e5ac9f8ce942d7f20b7cd02b12037f6bbcd1e31c31ed4b89016caef834b53ef363dc76df373bd43948290f75cb6e2184652c4a58867e30b65e16f3a16c1eb3e708edc09ad08efebd88231e596daa144f097076c950932f06ac0ea641179062c9dbb4ef7bb50874db10e498cc916ea0cc352f15b496be7595cfa88e07b65123d415ff24ed9519ea894c048aa0a423a6678932057939f45e9f767303de100831d394dd244d09fb93302f3e7e373aa0b63a4ab36aaceebcd41b41b0d647205bd5970059165299964502824901b2e7f5a52417cfc440e7ff973ef0d188983dc036b56590f327fbad87913a78a27c4a657eb5ac194262ae7919b062be18ab0ca15686621c71ef343ee937f3667a8ce24fb6a312747e5310a4b94cf70ee009849179e24f9264b149b5d8c8e1dd0a4a521fc7d8897310b9057e8ef15d85f137d89ca81a4cd57eb88764607efde6354d35c3274d7358da6d8ad0c5d9370e1c70ed8a892102932889c50898757a3b11c67ac6dd010719f3cf18c36abf24f67fcb8d4b4f78b176197cde97c5f79368e8a655bc20c665fe9436b469449e047a0a735ca878e158ea048d92420f907907708b3896035395a11f9d505187f44e7db932e5c0f2e607f37ac019a2a77936614250ec0d50655181ed19cd086bfca6ee565775f5ec74b440b0fba7f8307810728855c1fdeab1169cc0177543f70cc992db568216ab5099d9986076a937836ab4a150bd0cdfe42af1df2a63584e4d6972575cb5975473bb527ed2b994180c66c509372b8767e76b8f8c50f85b4ebc56fc91888ac44d758cd8a9370078e22f139e4727cd44fb10366bfb7f7458882074ad913a7a70cf599cd6939a2e3cf676a8e09b951243f5a15aa8cfa42f9c54f6799432cf261cf25a31815225f109db23f43304c66f62646ef5a5f29e65e7110103f0acdb76162da15b5352c727c411f7e95c87a5bf897f4ed4ca76a84d62cb0576103e83368a21b438c889b158e7f9c17f53a8a014a1f0110c126edb10e09a70351ea4b40086891973973cf98e8474b2083840b10d17597ad99c7c192b17cc28412f151abfca81aa7467ea194399aa010d79f153bb3f279af75aa216bc11e6970d767c00c4da99548e9f22ad18801a05eb6a219a2674e1e331fbc4b2ef28ef7ce7bbbc396ed84d671d40d7044dd09bfed89f546f73063b160d85efbf43de648be06d115fc8cf74e99234c93675b854b8281cd458ba11b7ec24d0b484d956cfd36189727ea5f63cb51ea45d8ee3a0ec0413aa93f9e9064ec1535e8370fbc5512e7e71e6cb6b1c89ec4deead977957f9b10d2c938867d07fc4df147985b7c11d96ce3a679ed001ce3cf95c93ad31b793ac4d25e02d63e014947c0adfd654b01731d7b95ad9df373c22cf";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities",userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}