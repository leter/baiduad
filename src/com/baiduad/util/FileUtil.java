package com.baiduad.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil
{
  private static final transient Log log = LogFactory.getLog(FileUtil.class);
  public static String CONTEXT_PATH = null;
  public static final boolean IS_WINDOWS = System.getProperties().getProperty("os.name").toLowerCase().contains("windows");
  public static final String IMG_FOLDER = "imgs";
  
  public static String radomPass()
  {
    return radomCode("1234567890", 2) + radomCode("mnopqstuvwxyzabcdefghjkl", 2) + radomCode("ABCDEFGHJKLMNOPQSTUVWXYZ", 2);
  }
  
  public static String getTodayString()
  {
    return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
  }
  
  private static String radomCode(String ssource, int m)
  {
    Random r = new Random();
    char[] src = ssource.toCharArray();
    char[] buf = new char[m];
    for (int i = 0; i < m; i++)
    {
      int rnd = Math.abs(r.nextInt()) % src.length;
      buf[i] = src[rnd];
    }
    return new String(buf);
  }
  
  public static String getPreFix(String fileName)
  {
    if ((fileName != null) && (!fileName.trim().equals("")))
    {
      if (fileName.indexOf(".") > 0) {
        return fileName.substring(0, fileName.indexOf("."));
      }
      return fileName;
    }
    return "";
  }
  
  public static boolean deleteFile(String filePath)
  {
    if (filePath != null) {
      try
      {
        File file = new File(filePath);
        if ((!file.isDirectory()) && (file.exists())) {
          file.delete();
        }
      }
      catch (Exception e)
      {
        log.error(e.getLocalizedMessage());
        return false;
      }
    }
    return true;
  }
  
  public static void DirIsExist(String path)
  {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }
  
  public static boolean FileIsExist(String path)
  {
    return new File(path).exists();
  }
  
  public static boolean StringNotEmpty(String str)
  {
    return (str != null) && (str.trim().length() > 0);
  }
  
  public static String uploadFile(MultipartFile multipartFile, String folder, HttpServletRequest servletRequest)
  {
    String newFileName = null;
    if ((multipartFile != null) && (!multipartFile.isEmpty()))
    {
      newFileName = getTodayString() + "_" + multipartFile.getOriginalFilename();
      
      String dir = servletRequest.getSession().getServletContext().getRealPath("") + File.separator + folder + File.separator;
      
      String filePath = dir + newFileName;
      
      DirIsExist(dir);
      try
      {
        multipartFile.transferTo(new File(filePath));
      }
      catch (Exception e)
      {
        log.error(e);
        return null;
      }
    }
    return newFileName;
  }
  
  public static void deleteFile(String fileName, String folder, HttpServletRequest servletRequest)
  {
    if (fileName != null)
    {
      String dir = servletRequest.getSession().getServletContext().getRealPath("") + File.separator + folder + File.separator;
      
      String filePath = dir + fileName;
      deleteFile(filePath);
    }
  }
  
  public static String compressPic(String fileName, String folder, HttpServletRequest servletRequest, int width, int height)
  {
    String newFileName = null;
    if ((fileName != null) && (!fileName.isEmpty()))
    {
      newFileName = fileName.replaceFirst("_", "_samll_");
      String contextPath = servletRequest.getSession().getServletContext().getRealPath("");
      String dir = contextPath + File.separator + folder + File.separator;
      String oldFilePath = dir + fileName;
      String newfilePath = dir + newFileName;
      try
      {
        Thumbnails.of(new String[] { oldFilePath }).size(width, height).toFile(newfilePath);
      }
      catch (IOException e)
      {
        log.error("压缩图片的时候发生异常" + oldFilePath, e);
      }
    }
    return newFileName;
  }
  
  public static void main(String[] aa)
  {
    System.out.println();
  }
}
