package com.github.binarywang.demo.wechat.builder;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.filechooser.FileSystemView; 


public class SSS{ 
  public static String osName = System.getProperty("os.name"); 
/** 
  * @param args 
 * @throws IOException 
  */ 
public static void main(String[] args) throws IOException { 

	SSS sc = new SSS(); 
    File installDir = new File("D:\\Git\\"); 
    System.out.println(sc.createWindowsShortcut(installDir, "git-cmd.exe", 
    	      "Git", "xxxx")); 
    Runtime.getRuntime().exec("cmd.exe /c start C:\\Users\\Administrator\\Desktop\\fs.js");
	File desktopDir =FileSystemView.getFileSystemView().getHomeDirectory();

	String StringdesktopPath = desktopDir.getAbsolutePath();
	System.out.println(StringdesktopPath);
} 
/** 
   * 
   * @param installDir 
   *            文件路径 
   * @param runnable 
   *            可执行性文件(***.exe) 
   * @param folder 
   *            文件夹名称(本级文件夹名称) 
   * @param name 
   *            快捷方式名称 
   * @return 
   */ 
  public boolean createWindowsShortcut(File installDir, String runnable, 
    String folder, String name) 
  { 
   String command = null; 
   if (osName.indexOf("9") != -1) 
    command = "command.com   /c   cscript.exe   /nologo   "; 
   else 
    command = "cmd.exe   /c   cscript.exe   /nologo   "; 
   if (command != null) 
    try 
    { 
     File shortcutMaker = new File(installDir, "makeshortcut.js"); 
     PrintStream out = new PrintStream(new FileOutputStream( 
       shortcutMaker)); 
     out.println("WScript.Echo(\"Creating   shortcuts...\");"); 
     out 
       .println("Shell   =   new   ActiveXObject(\"WScript.Shell\");"); 
     out 
       .println("ProgramsPath   =   Shell.SpecialFolders(\"Programs\");"); 
     /** 创建菜单快捷方式 */ 
     out 
       .println("fso   =   new   ActiveXObject(\"Scripting.FileSystemObject\");"); 
     out.println("if   (!fso.folderExists(ProgramsPath   +   \"\\\\" 
       + folder + "\"))"); 
     out.println("\tfso.CreateFolder(ProgramsPath   +   \"\\\\" 
       + folder + "\");"); 
     out 
       .println("link   =   Shell.CreateShortcut(ProgramsPath   +   \"\\\\" 
         + folder + "\\\\" + name + ".lnk\");"); 
     out.println("link.Arguments   =   \"\";"); 
     out.println("link.Description   =   \"" + name + "\";"); 
     out.println("link.HotKey   =   \"\";"); 
     out.println("link.IconLocation   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\\\\" 
       + "winamp.ico,0\";"); 
     out.println("link.TargetPath   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\\\\" 
       + runnable + "\";"); 
     out.println("link.WindowStyle   =   1;"); 
     out.println("link.WorkingDirectory   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\";"); 
     out.println("link.Save();"); 
     /** 创建桌面快捷方式 */ 
     out 
       .println("DesktopPath   =   Shell.SpecialFolders(\"Desktop\");"); 
     out 
       .println("link   =   Shell.CreateShortcut(DesktopPath   +   \"\\\\" 
         + name + ".lnk\");"); 
     out.println("link.Arguments   =   \"\";"); 
     out.println("link.Description   =   \"" + name + "\";"); 
     out.println("link.HotKey   =   \"\";"); 
     out.println("link.IconLocation   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\\\\" 
       + "winamp.ico,0\";"); 
     out.println("link.TargetPath   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\\\\" 
       + runnable + "\";"); 
     out.println("link.WindowStyle   =   1;"); 
     out.println("link.WorkingDirectory   =   \"" 
       + escaped(installDir.getAbsolutePath()) + "\";"); 
     out.println("link.Save();"); 
     out.println("WScript.Echo(\"Shortcuts   created.\");"); 
     /** ********* */ 
     out.close(); 
     //Process p = Runtime.getRuntime().exec( command + "   makeshortcut.js", null, installDir); 
     Process p = Runtime.getRuntime().exec( "C:\\Documents and Settings\\Walker Zhang\\桌面\\PCS_Crawler_exe\\pcs_crawler.exe"); 
     p.waitFor(); 
     int rv = p.exitValue(); 
     if (rv == 0) 
     { 
      // JOptionPane.showMessageDialog(null, "创建成功"); 
      return true; 
     } else 
     { 
      return false; 
     } 
    } catch (Exception e) 
    { 
     return false; 
    } 
   else 
    return false; 
  } 

  public String escaped(String s) 
  { 
   String r = ""; 
   for (int i = 0; i < s.length(); i++) 
   { 
    if (s.charAt(i) == '\\') 
     r = r + '\\'; 
    r = r + s.charAt(i); 
   } 

   return r; 
  } 
} 
