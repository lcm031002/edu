package com.edu.workflow.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @ClassName: FileUtil
 * @Description: 文件解压
 *
 */
public class FileUtil {

	/**
	 * 解压,处理下载的zip工具包文件
	 * 
	 * @param directory
	 *            要解压到的目录
	 * @param zip
	 *            工具包文件
	 * 
	 * @throws Exception
	 *             操作失败时抛出异常
	 */
	@SuppressWarnings("resource")
	public static void unzipFile(String directory, File zip) throws Exception {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			ZipEntry ze = zis.getNextEntry();
			File parent = new File(directory);
			if (!parent.exists() && !parent.mkdirs()) {
				throw new Exception("创建解压目录 \"" + parent.getAbsolutePath()
						+ "\" 失败");
			}
			while (ze != null) {
				String name = ze.getName();
				File child = new File(parent, name);
				FileOutputStream output = new FileOutputStream(child);
				byte[] buffer = new byte[10240];
				int bytesRead = 0;
				while ((bytesRead = zis.read(buffer)) > 0) {
					output.write(buffer, 0, bytesRead);
				}
				output.flush();
				output.close();
				ze = zis.getNextEntry();
			}
			zis.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	public static void copyFolder(InputStream inputStream, String newPath) {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(newPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b = new byte[1024 * 5];
		int len = -1;
		try {
			while ((len = inputStream.read(b)) != -1) {
				output.write(b, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// /**
	// * 解压缩JAR包
	// *
	// * @param fileName
	// * 文件名
	// * @param outputPath
	// * 解压输出路径
	// * @throws IOException
	// * IO异常
	// */
	// private void decompress(String fileName, String outputPath)
	// throws IOException {
	//
	// if (!outputPath.endsWith(File.separator)) {
	//
	// outputPath += File.separator;
	//
	// }
	//
	// JarFile jf = new JarFile(fileName);
	//
	// for (Enumeration e = jf.entries(); e.hasMoreElements();) {
	// JarEntry je = (JarEntry) e.nextElement();
	// String outFileName = outputPath + je.getName();
	// File f = new File(outFileName);
	// System.out.println(f.getAbsolutePath());
	//
	// // 创建该路径的目录和所有父目录
	// makeSupDir(outFileName);
	//
	// // 如果是目录，则直接进入下一个循环
	// if (f.isDirectory()) {
	// continue;
	// }
	//
	// InputStream in = null;
	// OutputStream out = null;
	//
	// try {
	// in = jf.getInputStream(je);
	// out = new BufferedOutputStream(new FileOutputStream(f));
	// byte[] buffer = new byte[2048];
	// int nBytes = 0;
	// while ((nBytes = in.read(buffer)) > 0) {
	// out.write(buffer, 0, nBytes);
	// }
	// } catch (IOException ioe) {
	// throw ioe;
	// } finally {
	// try {
	// if (null != out) {
	// out.flush();
	// out.close();
	// }
	// } catch (IOException ioe) {
	// throw ioe;
	// } finally {
	// if (null != in) {
	// in.close();
	// }
	// }
	// }
	// }
	// }

	/**
	 * 
	 * 用FileWrite向文件写入内容
	 * 
	 * 
	 * 
	 * @param _destFile
	 * 
	 * @throws IOException
	 */

	public static void writeByFileWrite(String _sDestFile, String _sContent)

	throws IOException {

		FileWriter fw = null;

		try {

			fw = new FileWriter(_sDestFile);

			fw.write(_sContent);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fw != null) {

				fw.close();

				fw = null;

			}

		}

	}

	/**
	 * 
	 * 用FileOutputStream向文件写入内容
	 * 
	 * 
	 * 
	 * @param _destFile
	 * 
	 * @throws IOException
	 */

	public static void writeByFileOutputStream(String _sDestFile,

	String _sContent) throws IOException {

		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(_sDestFile);

			fos.write(_sContent.getBytes());

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fos != null) {

				fos.close();

				fos = null;

			}

		}

	}

	/**
	 * 
	 * 用OutputStreamWrite向文件写入内容
	 * 
	 * 
	 * 
	 * @param _destFile
	 * 
	 * @throws IOException
	 */

	public static void writeByOutputStreamWrite(File _sDestFile,

	String _sContent) throws IOException {

		OutputStreamWriter os = null;

		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(_sDestFile);

			os = new OutputStreamWriter(fos, "UTF-8");

			os.write(_sContent);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (os != null) {

				os.close();

				os = null;

			}

			if (fos != null) {

				fos.close();

				fos = null;

			}

		}

	}

	public static void deleteFile(File file) {
		if (file.exists() && file.canRead()) {
			if (file.isDirectory()) {
				for (File fileSub : file.listFiles()) {
					deleteFile(fileSub);
				}
				
			} 
			file.delete();
		}
	}

	public static void main(String[] args) {
		FileUtil.copyFolder(FileUtil.class.getResource("/workflow").getPath(),
				"/workflow");
	}
}
