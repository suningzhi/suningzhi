package com.bypay.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

public  class RemoteAccessor {
	private HttpClient httpClient;
	private HttpGet httpGet;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	private HttpEntity httpEntity;
	private String content = new String();
	public RemoteAccessor(){
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 90000);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 120000);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 1024*4);
	}
	public String getSimpleResponse(String url)throws Exception{
		httpGet = new HttpGet(url); 
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		content = httpClient.execute(httpGet, responseHandler);
		return content;
	}
	public String getResponseByPost(String url,String encode,String[] params)throws Exception{
		httpPost = new HttpPost(url); 
		List<org.apache.http.NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
		for(int i=0; i<params.length/2;i++){
			formParams.add(new BasicNameValuePair(params[i*2], params[i*2+1]));
		}
		HttpEntity entityForm = new UrlEncodedFormEntity(formParams, encode);
		httpPost.setEntity(entityForm);
		
		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		
		
		content = httpClient.execute(httpPost, responseHandler);
		return content;
	}
	class StreamEntity implements ContentProducer{
		public void writeTo(OutputStream outstream) throws IOException {
	        Writer writer = new OutputStreamWriter(outstream, this.encode);
	        writer.write(this.data);
	        writer.flush();
	    }
		public String encode;
		public String data;
	}
	public String getResponseByStream(String url,String encode,String data,String sessionID)throws Exception{
		httpPost = new HttpPost(url); 
		httpPost.setHeader("sessionID", sessionID);
		StreamEntity se = new StreamEntity();
		se.data=data;
		se.encode=encode;
		HttpEntity entity = new EntityTemplate(se);
		httpPost.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		content = httpClient.execute(httpPost, responseHandler);
		return content;
	}
	public String getResponseByStream(String url,String encode,String data)throws Exception{
		httpPost = new HttpPost(url); 
		StreamEntity se = new StreamEntity();
		se.data=data;
		se.encode=encode;
		HttpEntity entity = new EntityTemplate(se);
		httpPost.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		content = httpClient.execute(httpPost, responseHandler);
		return content;
	}
	public int getResponseByStream1(String url,String encode,String data){
		try{
			httpPost = new HttpPost(url); 
			StreamEntity se = new StreamEntity();
			se.data=data;
			se.encode=encode;
			HttpEntity entity = new EntityTemplate(se);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			return  response.getStatusLine().getStatusCode();
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		
	}
	public String getResponseByProxy(String url)throws Exception{
		httpClient = new DefaultHttpClient();
		
		do{
			HttpHost proxy = new HttpHost((String)getProxy().get(0), (Integer)getProxy().get(1));
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			httpGet = new HttpGet(url); 
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			int count = 0;
			try{
					content = httpClient.execute(httpGet, responseHandler);
			}catch(Exception e){
				System.out.println("Remote accessed by proxy["+(String)getProxy().get(0)+":"+(Integer)getProxy().get(1)+"] had Error!Try next!");
			}
			count++;
			if(count>2){break;}
		}while(content.length()==0);
		return content;
	}
	
	//通过post方式获取流
	public InputStream getResponseStreamByPost(String url,String encode,String[] params)throws Exception{
		httpPost = new HttpPost(url); 
		List<org.apache.http.NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
		for(int i=0; i<params.length/2;i++){
			formParams.add(new BasicNameValuePair(params[i*2], params[i*2+1]));
		}
		HttpEntity entityForm = new UrlEncodedFormEntity(formParams, encode);
		httpPost.setEntity(entityForm);
		return  httpClient.execute(httpPost).getEntity().getContent();
	}
	public void shutdown(){
		httpClient.getConnectionManager().shutdown();   
	}
	
	
	public static ArrayList getProxy(){
		ArrayList array = new ArrayList();
		ArrayList proxy = null;
		proxy = new ArrayList();proxy.add(new String("221.130.7.72"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("218.59.169.109"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("222.161.137.199"));proxy.add(8080);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("222.162.105.110"));proxy.add(8080);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.73"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.82"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("220.248.3.203"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("60.217.248.150"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.176.88.73"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("211.138.124.198"));proxy.add(8080);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("211.138.124.197"));proxy.add(8080);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.69"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("219.153.71.171"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.176.88.94"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("222.77.14.55"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.176.88.92"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("220.248.3.202"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.70"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.93"));proxy.add(80);array.add(proxy);
		proxy = new ArrayList();proxy.add(new String("221.130.7.68"));proxy.add(80);array.add(proxy);
		
		int item=(int)(Math.random()*array.size());   
		ArrayList list = (ArrayList)array.get(item); 
		return (ArrayList)array.get(item);
	}
	
	public static void main(String[] args) {
		try {
			new RemoteAccessor().getResponseByPost("https://mt.bypay.cn/", "utf-8", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
