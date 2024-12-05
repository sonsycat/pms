package com.zjry.pms.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author
 */
public class HttpConnectionUtil {
	private static String https;
	/**
	 * http的get请求
	 * 
	 * @param url
	 */
	public static String get(String url) {
		return get(url, "UTF-8");
	}

	/**
	 * http的get请求
	 * 
	 * @param url
	 */
	public static String get(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		return executeRequest(httpGet, charset);
	}

	/**
	 * http的get请求，增加异步请求头参数
	 * 
	 * @param url
	 */
	public static String ajaxGet(String url) {
		return ajaxGet(url, "UTF-8");
	}

	/**
	 * http的get请求，增加异步请求头参数
	 * 
	 * @param url
	 */
	public static String ajaxGet(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		return executeRequest(httpGet, charset);
	}

	/**
	 * http的post请求，传递map格式参数
	 */
	public static String post(String url, Map<String, String> dataMap) {
		return post(url, dataMap, "UTF-8");
	}

	/**
	 * http的异步post请求，传递map格式参数
	 */
	public static String asynPost(String url, Map<String, String> dataMap, FutureCallback<HttpResponse> clazz) {
		return asynPost(url, dataMap, "UTF-8", clazz);
	}

	/**
	 * http的post请求，传递map格式参数
	 */
	public static String post(String url, Map<String, String> dataMap, String charset) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (dataMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, charset);
	}

	/**
	 * http的post请求，传递map格式参数
	 */
	public static String asynPost(String url, Map<String, String> dataMap, String charset,
			FutureCallback<HttpResponse> clazz) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (dataMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeAsynRequest(httpPost,clazz);
	}

	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static String ajaxPost(String url, Map<String, String> dataMap) {
		return ajaxPost(url, dataMap, "UTF-8");
	}

	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static String ajaxPost(String url, Map<String, String> dataMap, String charset) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		try {
			if (dataMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, charset);
	}

	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static String ajaxPostJson(String url, String jsonString) {
		return ajaxPostJson(url, jsonString, "UTF-8");
	}

	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static String ajaxPostJson(String url, String jsonString, String charset) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		// try {
		// 解决中文乱码问题
		StringEntity stringEntity = new StringEntity(jsonString, charset);
		stringEntity.setContentEncoding(charset);
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return executeRequest(httpPost, charset);
	}

	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static String executeRequest(HttpUriRequest httpRequest) {
		return executeRequest(httpRequest, "UTF-8");
	}

	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static String executeRequest(HttpUriRequest httpRequest, String charset) {
		CloseableHttpClient httpclient;
		/*if (https.equals(httpRequest.getURI().getScheme())) {
			httpclient = createSSLInsecureClient();
		} else {*/
			httpclient = HttpClients.createDefault();
		/*}*/
		String result = "";
		try {
			try {
				CloseableHttpResponse response = httpclient.execute(httpRequest);
				HttpEntity entity = null;
				try {
					entity = response.getEntity();
					result = EntityUtils.toString(entity, charset);
				} finally {
					EntityUtils.consume(entity);
					response.close();
				}
			} finally {
				httpclient.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String executeAsynRequest(HttpUriRequest httpRequest,
			FutureCallback<HttpResponse> clazz) {
		CloseableHttpAsyncClient httpclient;
		try {
			httpclient = HttpAsyncClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
			httpclient.start();
			httpclient.execute(httpRequest, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "send_success";
	}

	/**
	 * 创建 SSL连接
	 */
	/*public static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		}
	}*/

}
