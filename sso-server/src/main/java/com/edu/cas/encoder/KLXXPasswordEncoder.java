/**  
 * @Title: KLXXPasswordEncoder.java
 * @Package org.jasig.cas.authentication.handler
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月8日 下午5:03:50
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.encoder;

import java.security.MessageDigest;

import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.authentication.handler.PasswordEncoder;

/**
 * @ClassName: KLXXPasswordEncoder
 * @Description: 厝边素高密码加密算法
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月8日 下午5:03:50
 *
 */
public class KLXXPasswordEncoder implements PasswordEncoder {

    @NotNull
    private final String encodingAlgorithm;

    @SuppressWarnings("unused")
	private String characterEncoding;

    public KLXXPasswordEncoder(final String encodingAlgorithm) {
        this.encodingAlgorithm = encodingAlgorithm;
    }

    public String encode(final String password) {
        if (password == null) {
            return null;
        }

        return encrypt(password);
    }
    
    /**
	 * 加密
	 * @param 	str		要加密的字符串
	 * @return	String	加密后的字符串
	 */
	private String encrypt(String str)
	{
		if(str == null || str.trim().length() == 0)
			return "";
		return encryptSHA(encryptMD5(str) + "SHRY");
	}
	
	/**
	 * MD5加密
	 * @param 	orgStr		MD5加密前的明文
	 * @return	String		MD5加密后的密文
	 */
	private String encryptMD5(String orgStr) 
	{
		String retStr = "";
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdtemp = md.digest(orgStr.getBytes());
			for (int i = 0; i < mdtemp.length; i++) 
			{
				retStr += Integer.toHexString(mdtemp[i] + 512).substring(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("MD5加密失败！");
			e.printStackTrace();
		}
		return retStr;
	}
	
	/**
	 * SHA加密
	 * @param 	orgStr		SHA加密前的明文
	 * @return	String		SHA加密后的密文
	 */
	private String encryptSHA(String orgStr) 
	{
		String retStr = "";
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] mdtemp = md.digest(orgStr.getBytes());
			for (int i = 0; i < mdtemp.length; i++) 
			{
				retStr += Integer.toHexString(mdtemp[i] + 512).substring(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("SHA加密失败！");
			e.printStackTrace();
		}
		return retStr;
	}

    public final void setCharacterEncoding(final String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }
}
