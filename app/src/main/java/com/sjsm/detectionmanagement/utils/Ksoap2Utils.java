package com.sjsm.detectionmanagement.utils;

import android.content.Context;
import android.os.Looper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

/**
 * 调用服务端Webservice工具类
 */
public class Ksoap2Utils {
	private final static String SERVICE_NS = "http://tempuri.org/";// 命名空间

	// 响应接口
	public interface ResponseHandlerInterface {
		void onSuccess(String responseString);

		void onFailure(String responseString);
	}

	/**
	 * 异步请求
	 * 
	 * @param mContext
	 *            上下文对象
	 * @param methodName
	 *            方法名
	 * @param params
	 *            参数
	 * @param responseHandler
	 *            回调函数
	 */
	public static void doRequestAsync(Context mContext, String methodName, Map<String, Object> params,
									  final ResponseHandlerInterface responseHandler) {
		String serviceUrl = SpUtils.get(mContext, "ServerUrl", "").toString();
		final String SOAP_ACTION = SERVICE_NS + methodName;
		final HttpTransportSE ht = new HttpTransportSE(serviceUrl);
		ht.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			// String key = URLEncoder.encode(entry.getKey(), "utf-8");
			String key = entry.getKey();
			Object value = entry.getValue();
			soapObject.addProperty(key, value);
		}
		envelope.dotNet = true;
		envelope.bodyOut = soapObject;
		new Thread(new Runnable() {
			public void run() {
				try {

					ht.call(SOAP_ACTION, envelope);
					if (envelope.getResponse() != null) {
						SoapObject so = (SoapObject) envelope.bodyIn;
						String result = so.getPropertyAsString(0);
						responseHandler.onSuccess(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseHandler.onFailure(e.getMessage());
				}
			}
		}).start();
	}

	/**
	 * 异步请求
	 * 
	 * @param serviceUrl
	 *            请求地址
	 * @param methodName
	 *            方法名
	 * @param params
	 *            参数
	 * @param responseHandler
	 *            回调函数
	 */
	public static void doRequestAsync(String serviceUrl, String methodName, Map<String, Object> params,
									  final ResponseHandlerInterface responseHandler) {

		final String SOAP_ACTION = SERVICE_NS + methodName;
		final HttpTransportSE ht = new HttpTransportSE(serviceUrl);
		ht.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		if(params!=null){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				// String key = URLEncoder.encode(entry.getKey(), "utf-8");
				String key = entry.getKey();
				Object value = entry.getValue();
				soapObject.addProperty(key, value);
			}
		}
		
		envelope.dotNet = true;
		envelope.bodyOut = soapObject;
		new Thread(new Runnable() {
			public void run() {
				try {

					ht.call(SOAP_ACTION, envelope);
					if (envelope.getResponse() != null) {
						SoapObject so = (SoapObject) envelope.bodyIn;
						String result = so.getPropertyAsString(0);
						Looper.prepare();
						responseHandler.onSuccess(result);
						Looper.loop();
					}
				} catch (Exception e) {
					e.printStackTrace();
					responseHandler.onFailure(e.getMessage());
				}
			}
		}).start();
	}

	/**
	 * 同步请求
	 * 
	 * @param serviceUrl
	 *            请求地址
	 * @param methodName
	 *            方法名
	 * @param params
	 *            参数
	 * @return 返回结果字符串
	 * @throws Exception
	 */
	public static String doRequest(String serviceUrl, String methodName, Map<String, Object> params) throws Exception {
		String SOAP_ACTION = SERVICE_NS + methodName;
		final HttpTransportSE ht = new HttpTransportSE(serviceUrl);
		ht.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			soapObject.addProperty(key, value);
		}
		envelope.dotNet = true;
		envelope.bodyOut = soapObject;
		ht.call(SOAP_ACTION, envelope);
		if (envelope.getResponse() != null) {
			SoapObject so = (SoapObject) envelope.bodyIn;
			String result = so.getPropertyAsString(0);
			return result;
		}
		return "";
	}

	/**
	 * 同步请求
	 * @param mContext 上下文对象
	 * @param methodName 方法名
	 * @param params 参数
	 * @return 返回结果字符串
	 * @throws Exception
	 */
	public static String doRequest(Context mContext, String methodName, Map<String, Object> params) throws Exception {
		String serviceUrl = SpUtils.get(mContext, "ServerUrl", "").toString();
		String SOAP_ACTION = SERVICE_NS + methodName;
		final HttpTransportSE ht = new HttpTransportSE(serviceUrl);
		ht.debug = true;
		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			soapObject.addProperty(key, value);
		}
		envelope.dotNet = true;
		envelope.bodyOut = soapObject;
		ht.call(SOAP_ACTION, envelope);
		if (envelope.getResponse() != null) {
			SoapObject so = (SoapObject) envelope.bodyIn;
			String result = so.getPropertyAsString(0);
			return result;
		}
		return "";
	}
}
