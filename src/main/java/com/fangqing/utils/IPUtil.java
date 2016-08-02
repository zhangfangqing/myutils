package com.fangqing.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @功能   IP工具类
 *
 * @author zhangfangqing 
 * @date 2016年8月2日 
 * @time 下午2:18:53
 */
public class IPUtil {
    private static Logger logger   = Logger.getLogger(IPUtil.class);
    private static String serverIP = "";

    /**
     * 兼容linux 获取本地IP（服务器）
     * 
     * @return
     */
    public static String getServerIP() {
        if (serverIP == null || "".equals(serverIP)) {
            try {
                Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress ine = null;
                while (allNetInterfaces.hasMoreElements() && serverIP.equals("")) {
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    if (!netInterface.isVirtual()) {
                        Enumeration<?> addresses = netInterface.getInetAddresses();
                        while (addresses.hasMoreElements() && serverIP.equals("")) {
                            ine = (InetAddress) addresses.nextElement();
                            if (ine != null && ine instanceof Inet4Address) {
                                if (!ine.getHostAddress().equals("127.0.0.1") && !netInterface.isVirtual()) {
                                    serverIP = ine.getHostAddress();
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("获取服务器IP发生异常：", e);
            }
        }

        return serverIP.trim();
    }

    /**
     * 获取本地IP（服务器）
     * 
     * @return
     */
    public static String getLocalIP() {
        if (serverIP == null || "".equals(serverIP)) {
            try {
                InetAddress inet = InetAddress.getLocalHost();
                serverIP = inet.getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("获取服务器IP发生异常：", e);
            }
        }

        return serverIP;
    }

    /**
     * 获取客户端IP
     * 
     * @return 客户端IP
     */
    public static String getClientIP(HttpServletRequest httpServletRequest) {
        String clientIP = "";

        if (httpServletRequest.getHeader("x-forwarded-for") == null) {
            clientIP = httpServletRequest.getRemoteAddr();
        } else {
            clientIP = httpServletRequest.getHeader("x-forwarded-for");
        }

        int index = clientIP.indexOf(",");
        if (index > 0) {
            return clientIP.substring(0, index);
        }
        return clientIP;
    }

    /**
     * 将IP地址转换成十进制整数
     * 
     * @return long类型ip -1:IP无效
     */
    public static long ipToLong(String strIp) {
        if (isboolIP(strIp)) {
            return -1l;
        }
        long[] ip = new long[4];
        //先找到IP地址字符串中.的位置  
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        //将每个.之间的字符串转换成整型  
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * 将十进制整数转换成ip地址
     * 
     * @return ip地址
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        //直接右移24位  
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        //将高8位置0，然后右移16位  
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        //将高16位置0，然后右移8位  
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        //将高24位置0  
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    /**
     * 判断是否为ip地址
     * 
     * @return boolean
     */
    public static boolean isboolIP(String ipAddress) {
        String ip = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

}
