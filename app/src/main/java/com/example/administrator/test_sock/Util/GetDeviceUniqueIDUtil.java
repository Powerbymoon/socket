package com.example.administrator.test_sock.Util;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @说明 获取设备唯一的标识
 * @需要权限：READ_PHONE_STATE 和 ACCESS_WIFI_STATE
 * @author adrainy
 */
public class GetDeviceUniqueIDUtil {

	private static String deviceID = null;
	private static SharedPreferences share = null;

	/**
	 * @规则 
	 *     首先取imei，如果没有取macAddress，再没有就自定义一个变量（规则：id开头的一个32位字符串，根据java.util.UUID类生成
	 *     ， 防止重复 ）
	 * @漏洞 如果轮到java.util.uuid生成的时候，数据被清掉后会被清除。但对于这样的设备，比较罕见，模拟器是一类
	 * @param context
	 * @return
	 */
	public static String getDeviceUniqueId(Context context) {
		return getDeviceUniqueId(context, false);
	}

	public static String getDeviceUniqueId(Context context, boolean binding) {
		// 已经获取了设备id，直接返回
		if (!TextUtils.isEmpty(deviceID))
			return deviceID;

		if (share == null)
			share = context.getSharedPreferences("sharedPreference",
					Context.MODE_PRIVATE);

		// 没有设备id，检查share中是否有保存
		deviceID = share.getString("deviceUID", null);
		if (!TextUtils.isEmpty(deviceID))
			return deviceID;

		// 检测是不是原来设定的uuid依然存在，绑定成功后会清空此id（之所以放后面判断，主要是因为这个是少数手机）
		// String uuid = share.getString("unLoginUid", null);
		// if (!TextUtils.isEmpty(uuid) && !binding)
		// return uuid;

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceID = tm.getDeviceId();
		// 防止刷机和山寨机 http://www.lexun.cn/thread-22891880-1-1.html
		if (deviceID != null) {
			char[] chs = deviceID.toCharArray();
			int size = chs.length;
			boolean all0 = true;
			for (int i = 0; i < size; i++) {
				if (chs[i] != '0') {
					all0 = false;
					break;
				}
			}
			if (all0)
				deviceID = null;
		}

		if (TextUtils.isEmpty(deviceID)) {
			//deviceID = getMacAddress(context);
		}

		if (binding) {
			String tmp = deviceID;
			deviceID = null;
			return tmp;
		}

		share.edit().putString("deviceUID", deviceID).commit();
		return deviceID;
	}

	/*private static String getMacAddress(Context context) {
		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());

		if (info != null) {
			String macAddress = info.getMacAddress();
			if (macAddress == null) {
				String uuid = share.getString("uuidKey", null);
				if (uuid == null) {
					UUID u = UUID.randomUUID();
					macAddress = "id"
							+ u.toString().replaceAll("-", "").substring(2);
					share.edit().putString("uuidKey", macAddress).commit();
				} else {
					macAddress = uuid;
				}
			}
			macAddress = macAddress.toLowerCase();
			return macAddress.replaceAll(":", "").replaceAll("-", "");
		} else {
			String uuid = share.getString("uuidKey", null);
			if (uuid == null) {
				UUID u = UUID.randomUUID();
				uuid = "id" + u.toString().replaceAll("-", "").substring(2);
				share.edit().putString("uuidKey", uuid).commit();
			}
			return uuid;
		}
	}*/

}
