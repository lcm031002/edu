/**  
 * @Title: CaptchaImageCreateController.java
 * @Package com.edu.cas.jcaptcha
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月8日 下午6:38:17
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.jcaptcha;


import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * @ClassName: CaptchaImageCreateController
 * @Description: 校验码图片生成请求
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月8日 下午6:38:17
 *
 */
@SuppressWarnings("restriction")
public class CaptchaImageCreateController implements Controller, InitializingBean {
   private ImageCaptchaService jcaptchaService;
   public CaptchaImageCreateController(){
   }
   public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
      byte captchaChallengeAsJpeg[] = null;
      ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
      String captchaId = request.getSession().getId();
      java.awt.image.BufferedImage challenge=jcaptchaService.getImageChallengeForID(captchaId,request.getLocale());
      JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
 
      jpegEncoder.encode(challenge);
      captchaChallengeAsJpeg = jpegOutputStream.toByteArray();response.setHeader("Cache-Control", "no-store");
      response.setHeader("Pragma", "no-cache");
      response.setDateHeader("Expires", 0L);
      response.setContentType("image/jpeg");
      ServletOutputStream responseOutputStream = response.getOutputStream();
      responseOutputStream.write(captchaChallengeAsJpeg);
      responseOutputStream.flush();
      responseOutputStream.close();
      return null;
   }
   public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
      this.jcaptchaService = jcaptchaService;
   }
   public void afterPropertiesSet() throws Exception {
      if(jcaptchaService == null)
         throw new RuntimeException("Image captcha service wasn`t set!");
      else
         return;
   }
}