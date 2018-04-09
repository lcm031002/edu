package com.edu.erp.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.edu.erp.model.StudentInfo;

public class GeneratorWechatPictrue {

	static Statement stmt = null;

	/*
	 * public static void main(String[] args) { boolean withLogo = false;
	 * if(withLogo){ List<StudentInfo> list = readStudentInfo();//取数据
	 * for(StudentInfo tmp:list){ // 加密 if(tmp.getEncoding() != null &&
	 * !"".equals(tmp.getEncoding())){ // System.out.println(tmp.getEncoding());
	 * // System.out.println(tmp.getEncoding().getBytes()); String tmpEn =
	 * PwdEncryptUtil.encodeBase64(tmp.getEncoding().getBytes());
	 * System.out.println(tmpEn); //
	 * createQRCode("http://erp.klxuexi.org/Qr/"+tmpEn
	 * ,"E:\\work\\qr\\"+tmp.getBranch_name
	 * ()+"_"+tmp.getStudent_name()+tmp.getEncoding
	 * ()+".png","E:\\work\\qr\\logo.jpg");
	 * createQRCode("http://erp.klxuexi.org/Qr/"
	 * +tmpEn,"E:\\work\\qr\\"+tmp.getBranch_name
	 * ()+"_"+tmp.getStudent_name()+tmp
	 * .getEncoding()+".png","E:\\work\\qr\\logo.jpg"); }else{
	 * System.out.println(tmp.getBranch_name()+tmp.getStudent_name()); } }
	 * }else{ createQRCodeWithoutLogo("http://www.baidu.com/",
	 * "E:\\work\\qr\\withoutlogo.png"); } }
	 */

	/**
	 * 
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param imgPath
	 *            生成二维码图片完整的路径
	 * 
	 * @param ccbPath
	 *            二维码图片中间的logo路径
	 */

	public static int createQRCode(String content, String imgPath,
			String ccbPath) {

		try {
			BufferedImage bufImg = createQRCodeInner(content, ccbPath);
			// 生成二维码QRCode图片

			File imgFile = new File(imgPath);
			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e)

		{

			e.printStackTrace();

			return -100;

		}

		return 0;

	}

	/**
	 * 
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param output
	 *            输出流
	 * 
	 * @param ccbPath
	 *            二维码图片中间的logo路径
	 */

	public static int createQRCode(String content, OutputStream output,
			String ccbPath) {
		try {
			BufferedImage bufImg = createQRCodeInner(content, ccbPath);

			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", output);
		} catch (Exception e) {
			e.printStackTrace();
			return -100;
		}
		return 0;
	}

	/**
	 * 
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param ccbPath
	 *            二维码图片中间的logo路径
	 */

	public static BufferedImage createQRCodeInner(String content, String ccbPath)
			throws Exception {
		BufferedImage bufImg = new BufferedImage(140, 140,
				BufferedImage.TYPE_INT_RGB);
		try {

//			Qrcode qrcodeHandler = new Qrcode();
//
//			qrcodeHandler.setQrcodeErrorCorrect('M');
//
//			qrcodeHandler.setQrcodeEncodeMode('B');
//
//			qrcodeHandler.setQrcodeVersion(7);
//
//			// System.out.println(content);
//
//			byte[] contentBytes = content.getBytes("gb2312");
//
//			Graphics2D gs = bufImg.createGraphics();
//
//			gs.setBackground(Color.WHITE);
//
//			gs.clearRect(0, 0, 140, 140);
//
//			// 设定图像颜色 > BLACK
//
//			gs.setColor(Color.BLACK);
//
//			// 设置偏移量 不设置可能导致解析出错
//
//			int pixoff = 2;
//
//			// 输出内容 > 二维码
//
//			if (contentBytes.length > 0 && contentBytes.length < 120) {
//
//				boolean[][] codeOut =
//
//				qrcodeHandler.calQrcode(contentBytes);
//
//				for (int i = 0; i < codeOut.length; i++) {
//
//					for (int j = 0; j < codeOut.length; j++) {
//
//						if (codeOut[j][i]) {
//
//							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//
//						}
//
//					}
//
//				}
//
//			} else {
//
//				System.err.println("QRCode content bytes length = "
//
//				+ contentBytes.length + " not in [ 0,120 ]. ");
//				throw new Exception("QRCode content bytes length = "
//						+ contentBytes.length + " not in [ 0,120 ]. ");
//			}
//			int ps = ccbPath.indexOf(":");
//			Image img = null;
//			if (ps != -1) {
//				img = ImageIO.read(new File(ccbPath));
//			} else {
//				img = ImageIO.read(GeneratorWechatPictrue.class
//						.getResource(ccbPath));
//			}
//
//			// 实例化一个Image对象。
//
//			gs.drawImage(img, 47, 47, null);
//
//			gs.dispose();
//
//			bufImg.flush();
//
//			// 实例化一个Image对象。
//
//			gs.drawImage(img, 55, 55, null);
//
//			gs.dispose();
//
//			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return bufImg;

	}

	/**
	 * 不带有logo 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param imgPath
	 *            生成二维码图片完整的路径
	 *
	 */

	public static int createQRCodeWithoutLogo(String content, String imgPath) {

		try {
			BufferedImage bufImg = createQRCodeWithoutLogo(content);
			// 生成二维码QRCode图片

			File imgFile = new File(imgPath);
			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e)

		{

			e.printStackTrace();

			return -100;

		}

		return 0;

	}

	/**
	 * 不带有logo 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param imgFile
	 *            生成二维码图片完整的路径
	 *
	 */

	public static int createQRCodeWithoutLogo(String content, File imgFile) {

		try {
			BufferedImage bufImg = createQRCodeWithoutLogo(content);
			// 生成二维码QRCode图片

			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e)

		{

			e.printStackTrace();

			return -100;

		}

		return 0;

	}

	/**
	 * 不带有Logo 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 * 
	 * @param output
	 *            输出流
	 *
	 */

	public static int createQRCodeWithoutLogo(String content,
			OutputStream output) {
		try {
			BufferedImage bufImg = createQRCodeWithoutLogo(content);

			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", output);
		} catch (Exception e) {
			e.printStackTrace();
			return -100;
		}
		return 0;
	}

	/**
	 * 
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            二维码图片的内容
	 *
	 */

	public static BufferedImage createQRCodeWithoutLogo(String content)
			throws Exception {
		BufferedImage bufImg = new BufferedImage(140, 140,
				BufferedImage.TYPE_INT_RGB);
		try {

//			Qrcode qrcodeHandler = new Qrcode();
//
//			qrcodeHandler.setQrcodeErrorCorrect('M');
//
//			qrcodeHandler.setQrcodeEncodeMode('B');
//
//			qrcodeHandler.setQrcodeVersion(7);
//
//			// System.out.println(content);
//
//			byte[] contentBytes = content.getBytes("gb2312");
//
//			Graphics2D gs = bufImg.createGraphics();
//
//			gs.setBackground(Color.WHITE);
//
//			gs.clearRect(0, 0, 140, 140);
//
//			// 设定图像颜色 > BLACK
//
//			gs.setColor(Color.BLACK);
//
//			// 设置偏移量 不设置可能导致解析出错
//
//			int pixoff = 2;
//
//			// 输出内容 > 二维码
//
//			if (contentBytes.length > 0 && contentBytes.length < 120) {
//
//				boolean[][] codeOut =
//
//				qrcodeHandler.calQrcode(contentBytes);
//
//				for (int i = 0; i < codeOut.length; i++) {
//
//					for (int j = 0; j < codeOut.length; j++) {
//
//						if (codeOut[j][i]) {
//
//							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//
//						}
//
//					}
//
//				}
//
//			} else {
//
//				System.err.println("QRCode content bytes length = "
//
//				+ contentBytes.length + " not in [ 0,120 ]. ");
//				throw new Exception("QRCode content bytes length = "
//						+ contentBytes.length + " not in [ 0,120 ]. ");
//			}
//
//			gs.dispose();
//
//			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return bufImg;

	}

}