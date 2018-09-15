package com.lee.yantu.util;


import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.yantu.Entity.Token;
import com.lee.yantu.Entity.User;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;

import java.util.HashMap;
import java.util.Map;

public class JWT {
    private static final String SECRET = "LeeHi((*998#$";

    private static final String EXP = "exp";

    private static final String PAYLOAD = "payload";

    /**
     * get jwt String of object
     *
     * @param object the POJO object
     * @param maxAge the milliseconds of life time
     * @return the jwt token
     */
    private static <T> String sign(T object, long maxAge) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + maxAge);
            return signer.sign(claims);
        } catch (Exception e) {
            throw new ResultException(SystemEnum.TOKEN_ERR);
        }
    }



    /**
     * get the object of jwt if not expired
     *
     * @param jwt
     * @return POJO object
     */
    private static <T> T unsign(String jwt, Class<T> classT) {
        //解密token
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> claims = verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                //获取过期时间
                long exp = (Long) claims.get(EXP);
                //获取系统时间
                long currentTimeMillis = System.currentTimeMillis();
                System.out.println("exp:"+exp+"  now:"+currentTimeMillis);
                if (exp > currentTimeMillis) {
                    String json = (String) claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }else throw new ResultException(SystemEnum.TOKEN_EXP);
            }else throw new ResultException(SystemEnum.TOKEN_MISS);
        } catch (ResultException e) {
            throw e;
        } catch (Exception e){
            throw new ResultException(SystemEnum.TOKEN_ERR,e.getMessage());
        }
    }

    /**
     * 获取加密后的token
     * @param user
     * @return
     */
    public static String getTokenString(User user){
        Token token = new Token();
        token.setUserId(user.getUserId());
        token.setEmail(user.getEmail());
        return sign(token,1*60*60*1000L); //设置过期时间默认1小时
    }

    /**
     * 获取加密后的token，可以自己定义过期时间
     * @param user
     * @param time
     * @return
     */
    public static String getTokenString(User user,Long time){
        Token token = new Token();
        token.setUserId(user.getUserId());
        token.setEmail(user.getEmail());
        return sign(token,time);
    }

    /**
     * 获取解密后的token
     * @return
     */
    public static Token getTokenInstance(String token){
        return unsign(token,Token.class);
    }
}
