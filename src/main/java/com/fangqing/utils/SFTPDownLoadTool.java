package com.fangqing.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.taobao.diamond.client.Diamond;

/**
 * @功能 SFTP工具类
 *
 * @author zhangfangqing
 * @date 2016年7月6日
 * @time 下午3:25:19
 */
public class SFTPDownLoadTool {
	public static String host;
	public static int port;
	public static String nameDownload;// 中信下载用户名
	public static String pwdDownload;// 中信下载密码
	private Session sshSession = null;
	private ChannelSftp sftp = null;
	private static SFTPDownLoadTool instance = null;
	private final static Logger logger = LoggerFactory.getLogger(SFTPDownLoadTool.class);

	/**
	 * 初始化，读取diamond配置
	 */
	static {
		try {
			host = Diamond.getConfig("com.zhongan.badasset.ftp.host", "DEFAULT_GROUP", 6000);
			port = Integer.valueOf(Diamond.getConfig("com.zhongan.badasset.ftp.port", "DEFAULT_GROUP", 6000));
			nameDownload = Diamond.getConfig("com.zhongan.badasset.ftp.nameDownload", "DEFAULT_GROUP", 6000);
			pwdDownload = Diamond.getConfig("com.zhongan.badasset.ftp.pwdDownload", "DEFAULT_GROUP", 6000);
		} catch (Exception e) {
			logger.error("=========初始化SFTPTool，获取用户名异常error:{}，nameDownload-{}", e, nameDownload);
		}
	}

	/**
	 * @功能    双重锁机制，生成单例
	 *
	 * @author zhangfangqing 
	 * @date 2016年7月17日 
	 * @time 下午5:38:38
	 */
	public static SFTPDownLoadTool getInstance() {
		if (instance == null) {
			synchronized (SFTPDownLoadTool.class) {
				if (instance == null) {
					instance = new SFTPDownLoadTool(host, port, nameDownload, pwdDownload);
				}
			}
		}
		return instance;
	} 
	
	private SFTPDownLoadTool() {
	}

	private SFTPDownLoadTool(String host, int port, String username, String password) {
		super();
	}

	/**
	 * @功能 连接ftp
	 *
	 * @author zhangfangqing
	 * @date 2016年7月6日
	 * @time 下午3:25:44
	 */
	public void connect() {
		logger.info("====================================连接ftp");
		try {
			JSch jsch = new JSch();
			jsch.getSession(nameDownload, host, port);
			sshSession = jsch.getSession(nameDownload, host, port);
			sshSession.setPassword(pwdDownload);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			throw new RuntimeException("ftp connect exception ", e);
		}
	}

	/**
	 * 断开连接
	 */
	public void disconnect() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
			sftp = null;
		}
		if (sshSession != null) {
			if (sshSession.isConnected()) {
				sshSession.disconnect();
			}
			sshSession = null;
		}
		logger.info("====================================断开ftp连接");
	}

	/**
	 * 获得目录的所有文件
	 * 
	 * @param directory
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public Vector<LsEntry> listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 获得满足条件的待下载文件清单
	 * 
	 * @param directory
	 *            ftp目录
	 * @param saveFilePath
	 *            下载文件后缀
	 * @param filterFiles
	 *            过滤不下载的文件
	 * @return list 待下载文件名称
	 */
	public List<String> getDownloadFiles(String directory, String suffix, String[] filterFiles) throws Exception {
		List<String> fileList = new ArrayList<String>();
		if (null != directory) {
			try {
				sftp.cd(directory);
			} catch (Exception e) {
				logger.error("获得满足条件的待下载文件清单异常： " , e);
				return fileList;
			}
		}
		for (LsEntry file : listFiles(sftp.pwd())) {
			if (file.getFilename().equals(".") || file.getFilename().equals("..")) {
				continue;
			}

			if (suffix == null || (file.getFilename().endsWith(suffix.toLowerCase())
					|| file.getFilename().endsWith(suffix.toUpperCase()))) {
				if (filterFile(file.getFilename(), filterFiles)) {
					fileList.add(file.getFilename());
				}
			}
		}
		return fileList;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            ftp目录
	 * @param saveFile
	 *            保存目的文件
	 * @param saveFile
	 *            下载文件后缀
	 * @param filterFiles
	 *            过滤不下载的文件
	 * @return 下载成功数
	 */
	public int download(String directory, String saveFile, String suffix, String[] filterFiles) throws Exception {
		int result = 0;
		if (null != directory) {
			try {
				sftp.cd(directory);
			} catch (Exception e) {
				logger.error("================下载文件异常： " , e);
				return result;
			}
		}
		for (LsEntry file : listFiles(sftp.pwd())) {
			if (file.getFilename().equals(".") || file.getFilename().equals("..")) {
				continue;
			}

			if (suffix == null || (file.getFilename().endsWith(suffix.toLowerCase()) || file.getFilename().endsWith(suffix.toUpperCase()))) {
				if (filterFile(file.getFilename(), filterFiles)) {
					sftp.get(file.getFilename(), saveFile);
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * 判断待下载文件是否已下载过
	 * 
	 * @param fileName
	 *            待下载文件名
	 * @param filterFiles
	 *            过滤集合
	 * @return true 表示没有下载
	 */
	private boolean filterFile(String fileName, String[] filterFiles) {
		boolean result = true;
		if (filterFiles != null) {
			for (String temp : filterFiles) {
				if (temp.equals(fileName)) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

}
