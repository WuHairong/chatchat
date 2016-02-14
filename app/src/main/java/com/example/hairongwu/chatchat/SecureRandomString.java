package com.example.hairongwu.chatchat;
import java.math.BigInteger;
import java.security.SecureRandom;
/**
 * Created by hairongwu on 2/13/16.
 */
public class SecureRandomString {
    private SecureRandom random = new SecureRandom();
    public String nextString() {
        return new BigInteger(130, random).toString(32);
    }
}