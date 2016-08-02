package com.fangqing.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.taobao.diamond.client.Diamond;

/**
 * @功能 SFTP工具类
 *
 * @author zhangfangqing
 * @date 2016年7月6日
 * @time 下午3:25:19
 */
public class SFTPUpLoadTool {
	public static String separator;
	public static String saveFilePath;// 下载中信文件，保存在工程根目录下
	public static String host;
	public static int port;
	public static String nameUpload;// 中信上传用户名
	public static String pwdUpload;// 中信上传密码
	private Session sshSession = null;
	private ChannelSftp sftp = null;
	private static SFTPUpLoadTool instance = null;
	private final static Logger logger = LoggerFactory.getLogger(SFTPUpLoadTool.class);

	/**
	 * 初始化，读取diamond配置
	 */
	static {
		try {
			separator = System.getProperty("file.separator");
			saveFilePath = System.getProperty("user.dir");
			host = Diamond.getConfig("com.zhongan.badasset.ftp.host", "DEFAULT_GROUP", 6000);
			port = Integer.valueOf(Diamond.getConfig("com.zhongan.badasset.ftp.port", "DEFAULT_GROUP", 6000));
			nameUpload = Diamond.getConfig("com.zhongan.badasset.ftp.nameUpload", "DEFAULT_GROUP", 6000);
			pwdUpload = Diamond.getConfig("com.zhongan.badasset.ftp.pwdUpload", "DEFAULT_GROUP", 6000);
		} catch (Exception e) {
			logger.error("=========初始化SFTPUpLoadTool，获取用户名异常error:{}，nameUpload-{}", e, nameUpload);
		}
	}

	/**
	 * @功能    双重锁机制，生成单例
	 *
	 * @author zhangfangqing 
	 * @date 2016年7月17日 
	 * @time 下午5:38:38
	 */
	public static SFTPUpLoadTool getInstance() {
		if (instance == null) {
			synchronized (SFTPUpLoadTool.class) {
				if (instance == null) {
					instance = new SFTPUpLoadTool(host, port, nameUpload, pwdUpload);
				}
			}
		}
		return instance;
	} 
	
	private SFTPUpLoadTool() {
	}

	private SFTPUpLoadTool(String host, int port, String username, String password) {
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
			jsch.getSession(nameUpload, host, port);
			sshSession = jsch.getSession(nameUpload, host, port);
			sshSession.setPassword(pwdUpload);
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
	 * 上传文件
	 * 
	 * @param directory
	 *            进入上传的目录,支持多层用“/”来表示层级关系，如果没有则自动创建文件
	 * @param fileName
	 *            要创建的文件名称
	 * @param out
	 * @author 郁岩生 要上传的文件流
	 * @throws Exception
	 */
	public void upload(String directory, String fileName, InputStream in, int mode) throws Exception {
		if (null != directory) {
			try {
				mkDir(directory);
			} catch (Exception e) {
				logger.error("上传文件异常： " , e);
			}
		}
		// 如果是空流，则创建一个空的流文件
		if (null == in) {
			in = new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray());
		}
		// TODO 正式环境的时候放开 sftp.put(in, fileName);
		sftp.put(in, fileName, mode);
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            进入上传的目录,支持多层用“/”来表示层级关系，如果没有则自动创建文件
	 * @param fileName
	 *            要创建的文件名称
	 * @param out
	 * @author 郁岩生 要上传的文件流
	 * @throws Exception
	 */
	public void upload(String fileName, InputStream in, int mode) throws Exception {
		// 如果是空流，则创建一个空的流文件
		if (null == in) {
			in = new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray());
		}
		// TODO 正式环境的时候放开 sftp.put(in, fileName);
		sftp.put(in, fileName, mode);
	}

	/**
	 * 创建指定文件夹,返回到最里面的文件夹
	 * 
	 * @param dirName
	 * @author 郁岩生 dirName
	 */
	public void mkDir(String dirName) throws SftpException {
		String[] dirs = dirName.split("/");
		try {
			for (int i = 0; i < dirs.length; i++) {
				boolean dirExists = existDir(dirs[i]);
				if (!dirExists) {
					sftp.mkdir(dirs[i]);
				}
				sftp.cd(dirs[i]);
			}
		} catch (SftpException e) {
			logger.warn("mkdir error," + dirName, e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean existDir(String directory) throws SftpException {
		Vector<LsEntry> vector = sftp.ls(".");
		Iterator<LsEntry> it = vector.iterator();
		while (it.hasNext()) {
			LsEntry lsEntry = it.next();
			SftpATTRS t = lsEntry.getAttrs();
			if (t.isDir() && lsEntry.getFilename().equals(directory)) {
				return true;
			}
		}
		return false;
	}

}
