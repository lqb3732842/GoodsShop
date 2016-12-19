package goods.lqb.com.goodsshop.comutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 类:  <code> SP </code>
 * 功能描述:  SharedPerences保存数据和读取数据封装之后的工具类
 * 创建人:  葛建锋
 * 创建日期: 2014年7月15日 下午4:07:22
 * 开发环境: JDK7.0
 */
public class SP {

	// save shared preference values
	public static void putSP(Context context, String fileName, String key, Object value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		}
		editor.apply();
	}

	// save float shared preference
	public static void putFloatSP(Context context, String fileName, String key, float value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat(key, value).apply();
	}

	// save int shared preference
	public static void putIntSP(Context context, String fileName,String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value).apply();
	}

	// save long shared preference
	public static void putLongSP(Context context, String fileName, String key, long value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putLong(key, value).apply();
	}

	// get shared preference values
	public static Object getSP(Context context, String fileName,String key, Class clazz,
			Object defaultValues) {
		Object object = null;
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		String name = clazz.getName().substring(10);
		if (name.equals("Boolean")) {
			object = sp.getBoolean(key, (Boolean) defaultValues);
		} else if (name.equals("Float")) {
			object = sp.getFloat(key, (Float) defaultValues);
		} else if (name.equals("Integer")) {
			object = sp.getInt(key, (Integer) defaultValues);
		} else if (name.equals("Long")) {
			object = sp.getLong(key, (Long) defaultValues);
		} else if (name.equals("String")) {
			object = sp.getString(key, (String) defaultValues);
		}
		return object;
	}

	// get float shared preference
	public static float getFloatSP(Context context, String fileName,String key,
			float defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.getFloat(key, defaultValue);
	}

	// get int shared preference
	public static int getIntSP(Context context, String fileName, String key, int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	// get long shared preference
	public static long getLongSP(Context context, String fileName, String key, long defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.getLong(key, defaultValue);
	}

	// put boolean value
	public static void putBooleanSP(Context context,String fileName, String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value).apply();
	}

	// get boolean value
	public static boolean getBooleanSP(Context context, String fileName, String key,
			boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	// put string value
	public static void putStringSP(Context context, String fileName, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value).apply();
	}

	// get string value
	public static String getStringSP(Context context,String fileName, String key,
			String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}
	
	public static boolean isContains(Context context,String fileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.contains(key.toString().trim());
	}
	
	public static void clearSP(Context context,String fileName){
		try {
			SharedPreferences sp = context.getSharedPreferences(fileName,
					Context.MODE_PRIVATE);
			sp.edit().clear().apply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定key 的shape
	 * @param context
	 * @param fileName
     * @param key
     */
	public static void removeSp(Context context,String fileName,String key){
		try {
			SharedPreferences sp = context.getSharedPreferences(fileName,
					Context.MODE_PRIVATE);
			sp.edit().remove(key).apply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
