package com.ce.cechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
/**
 *
* <p>Title: SharedPreferencesUtils</p>
* <p>Description: SharedPreferences工具类</p>
* <p>Company: </p>
* @author    CEChan
* @date       Oct 30, 2015 9:32:26 AM
 */
public class SharedPreferencesUtils {
	/**
	 * 默认保存在手机里的文件名 share_data
	 */
	public static final String FILE_NAME = "share_data";

	private SharedPreferencesUtils() {
		throw new UnsupportedOperationException("Connot be instantiated.");
	}

	/**
	 *
	* <p>Title: put</p>
	* <p>Description: 保存数据的方法，拿到保存数据的具体类型，然后根据类型调用相应的保存方法 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:37:53 AM<P>
	* @param context 上下文
	* @param key 键
	* @param object 值
	*/
	public static void put(Context context, String key, Object object) {
		put(context, FILE_NAME, key, object);
	}

	/**
	 *
	* <p>Title: put</p>
	* <p>Description: 保存数据的方法，拿到保存数据的具体类型，然后根据类型调用相应的保存方法</p>
	* <p>CreateTime: Dec 2, 2015 3:13:48 PM<P>
	* @param pFileName 文件名
	* @param context 上下文
	* @param key 键
	* @param object 值
	 */
	public static void put(Context context, String pFileName, String key, Object object) {
		SharedPreferences sp = context.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}
		editor.apply();
	}

	/**
	 *
	* <p>Title: get</p>
	* <p>Description: 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:38:45 AM<P>
	* @param context 上下文
	* @param key 键
	* @param defaultObject 默认值
	* @return 值
	*/
	public static Object get(Context context, String key, Object defaultObject) {
		return get(context, FILE_NAME, key, defaultObject);
	}

	/**
	 *
	* <p>Title: get</p>
	* <p>Description: 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值</p>
	* <p>CreateTime: Dec 2, 2015 3:16:17 PM<P>
	* @param context 上下文
	* @param pFileName 文件名
	* @param key 键
	* @param defaultObject 默认值
	* @return 值
	 */
	public static Object get(Context context, String pFileName, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}


	/**
	 *
	* <p>Title: remove</p>
	* <p>Description: 移除一个key值已相对应的值 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:39:39 AM<P>
	* @param context 上下文
	* @param key 键
	 */
	public static void remove(Context context, String key) {
		remove(context, FILE_NAME, key);
	}

	/**
	 *
	* <p>Title: remove</p>
	* <p>Description: 移除一个key值已相对应的值</p>
	* <p>CreateTime: Dec 2, 2015 3:18:02 PM<P>
	* @param context 上下文
	* @param pFileName 文件名
	* @param key 键
	 */
	public static void remove(Context context, String pFileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.apply();
	}

	/**
	 *
	* <p>Title: clear</p>
	* <p>Description: 清除所有数据 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:40:22 AM<P>
	* @param context 上下文
	 */
	public static void clear(Context context) {
		clear(context, FILE_NAME);
	}

	/**
	 *
	* <p>Title: clear</p>
	* <p>Description:  清除所有数据</p>
	* <p>CreateTime: Dec 2, 2015 3:22:24 PM<P>
	* @param context 上下文
	* @param pFileName 文件名
	 */
	public static void clear(Context context, String pFileName) {
		SharedPreferences sp = context.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.apply();
	}

	/**
	 *
	* <p>Title: contains</p>
	* <p>Description: 查询一个key是否已经存在 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:40:47 AM<P>
	* @param context 上下文
	* @param key 键
	* @return ture / false
	 */
	public static boolean contains(Context context, String key) {
		return contains(context, FILE_NAME, key);
	}

	/**
	 *
	* <p>Title: contains</p>
	* <p>Description: 查询一个key是否已经存在</p>
	* <p>CreateTime: Dec 2, 2015 3:25:27 PM<P>
	* @param context 上下文
	* @param pFileName 文件名
	* @param key 键
	* @return ture / false
	 */
	public static boolean contains(Context context, String pFileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 *
	* <p>Title: getAll</p>
	* <p>Description: 返回所有的键值对 , 使用文件名 {@link #FILE_NAME}</p>
	* <p>CreateTime: Oct 30, 2015 9:45:07 AM<P>
	* @param context 上下文
	* @return Map<String, ?>
	 */
	public static Map<String, ?> getAll(Context context) {
		return getAll(context, FILE_NAME);
	}

	/**
	 *
	* <p>Title: getAll</p>
	* <p>Description: 返回所有的键值对</p>
	* <p>CreateTime: Dec 2, 2015 3:27:24 PM<P>
	* @param context 上下文
	* @param pFileName 文件名
	* @return 所有的键值对
	 */
	public static Map<String, ?> getAll(Context context, String pFileName) {
		SharedPreferences sp = context.getSharedPreferences(pFileName, Context.MODE_PRIVATE);
		return sp.getAll();
	}
}
