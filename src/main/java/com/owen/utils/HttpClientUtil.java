import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 
import ytx.org.apache.http.conn.ssl.SSLSocketFactory;
 
import com.jfinal.log.Logger;
 
public class Remote {
    private static Logger log = Logger.getLogger(Remote.class);
    /**
     * ¿?Post¿?¿?¿?¿?
     * @param url ¿?¿?¿?¿?
     * @param argsMap ¿?¿?¿?¿?¿?
     * @param content ¿?¿?
     * @return  String ¿?¿?¿?¿?
     * @throws Exception
     */
    public static String POSTMethod(String url,Map<String, Object> argsMap,String content) throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(argsMap)) {
            //¿?¿?¿?¿?
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
            httpPost.setEntity(encodedFormEntity);
        }
        if (StringUtils.isNotEmpty(content)) {
            httpPost.setEntity(new ByteArrayEntity(content.getBytes()));
        }
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPost.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    /**
     * ¿?¿?¿?Cookie¿?Post¿?¿?
     * @param url POST¿?¿?¿?¿?url 
     * @param argsMap ¿?¿?¿?¿?
     * @param content ¿?¿?
     * @param cookies cookies
     * @return
     * @throws Exception
     */
    public static String POSTMethodWithFiles(String url, Map<String, Object> argsMap,List<String> filePaths) throws Exception {
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
         
        MultipartEntity multipartEntity = new MultipartEntity();
        //¿?¿?¿?¿?¿?¿?
        if (CollectionUtils.isNotEmpty(filePaths)) {
            for (String filePath: filePaths) {
                File file = new File(filePath);
                ContentBody fileCont = new FileBody(file, file.getName(), "image/jpeg", "utf-8");
                FormBodyPart formBodyPart = new FormBodyPart("media", fileCont);
                multipartEntity.addPart(formBodyPart);
            }
        }
         
        //¿?¿?Form¿?¿?¿?¿?
        if (MapUtils.isNotEmpty(argsMap)) {
            Set<Entry<String, Object>> entrySet = argsMap.entrySet();
            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            while(iterator.hasNext()){
                Entry<String, Object> entry = iterator.next();
                String name = entry.getKey();
                Object value = entry.getValue();
//              StringBody strBody = new StringBody(value.toString(), "utf-8");
                StringBody strBody = new StringBody(value.toString(),Charset.forName("utf-8"));
                multipartEntity.addPart(name,strBody);
            }
        }
        httpPost.setEntity(multipartEntity);
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPost.abort();
        }
        // ¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
     
    /**
     * ¿?¿?¿?Cookie¿?Post¿?¿?
     * @param url POST¿?¿?¿?¿?url 
     * @param argsMap ¿?¿?¿?¿?
     * @param content ¿?¿?
     * @param cookies cookies
     * @return
     * @throws Exception
     */
    public static String POSTMethodWithFilesContentType(String url, Map<String, Object> argsMap,List<String[]> files) throws Exception {
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
         
        MultipartEntity multipartEntity = new MultipartEntity();
        //¿?¿?¿?¿?¿?¿?
        if (CollectionUtils.isNotEmpty(files)) {
            for (String[] fileInfos: files) {
                String paramName = fileInfos[0];
                String contentType = fileInfos[1];
                String filePath = fileInfos[2];
                File file = new File(filePath);
                ContentBody fileCont = new FileBody(file, file.getName(),contentType, "utf-8");
                FormBodyPart formBodyPart = new FormBodyPart(paramName, fileCont);
                multipartEntity.addPart(formBodyPart);
            }
        }
         
        //¿?¿?Form¿?¿?¿?¿?
        if (MapUtils.isNotEmpty(argsMap)) {
            Set<Entry<String, Object>> entrySet = argsMap.entrySet();
            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            while(iterator.hasNext()){
                Entry<String, Object> entry = iterator.next();
                String name = entry.getKey();
                Object value = entry.getValue();
//              StringBody strBody = new StringBody(value.toString(), "utf-8");
                StringBody strBody = new StringBody(value.toString(),Charset.forName("utf-8"));
                multipartEntity.addPart(name,strBody);
            }
        }
        httpPost.setEntity(multipartEntity);
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPost.abort();
        }
        // ¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
 
    /**
     * ¿?¿?Header¿?¿?¿?POST¿?¿?
     * @param url
     * @param argsMap
     * @param headers
     * @param content
     * @return
     * @throws Exception
     */
    public static String POSTMethodWidthHeader(String url,Map<String, Object> argsMap,Map<String, String> headers,String content,boolean isSSL)throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        //¿?¿?¿?¿?Https¿?¿?¿?¿?
        if (isSSL) {
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
 
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { xtm }, null);
                SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443,(SchemeSocketFactory) socketFactory));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
         
        if (MapUtils.isNotEmpty(argsMap)) {
            //¿?¿?¿?¿?
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
            httpPost.setEntity(encodedFormEntity);
        }
        //¿?¿?Header¿?¿?
        if (MapUtils.isNotEmpty(headers)) {
            Set<Entry<String, String>> entrySet = headers.entrySet();
            Iterator<Entry<String, String>> iterator = entrySet.iterator();
            while(iterator.hasNext()){
                Entry<String, String> entry = iterator.next();
                String headerName = entry.getKey();
                String headerValue = entry.getValue();
                httpPost.setHeader(headerName, headerValue);
            }
        }
        if (StringUtils.isNotEmpty(content)) {
            httpPost.setEntity(new ByteArrayEntity(content.getBytes()));
        }
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPost.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    /**
     * ¿?Get¿?¿?¿?¿?
     * @param url ¿?¿?¿?¿?
     * @param argsMap ¿?¿?¿?¿?¿?¿?
     * @return String
     * @throws Exception
     */
    public static String GETMethod(String url,Map<String, Object> argsMap) throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        //¿?GET¿?¿?¿?¿?¿?¿?¿?¿?
        url = formatGetParameter(url,argsMap);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpGet.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    /**
     * PUT¿?¿?
     * @param url ¿?¿?¿?¿?
     * @param argsMap ¿?¿?¿?¿?
     * @param cookies cookies
     * @param content ¿?¿?
     * @return
     * @throws Exception
     */
    public static String PUTMethod(String url,Map<String, Object> argsMap,String cookies,String content)throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        //¿?¿?¿?¿?
        if (StringUtils.isNotEmpty(content)) {
            httpPut.setEntity(new ByteArrayEntity(content.getBytes()));
        }
        //¿?¿?Cookies
        if(StringUtils.isNotEmpty(cookies)){
            httpPut.setHeader("Cookie", cookies);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-Type", "application/json");
        }
        //¿?¿?¿?¿?
        if (MapUtils.isNotEmpty(argsMap)) {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
            httpPut.setEntity(encodedFormEntity);
        }
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPut);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPut.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    /**
     * PUT¿?¿?¿?¿?
     * @param url ¿?¿?¿?¿?
     * @param argsMap ¿?¿?¿?¿?
     * @param headerParam header¿?¿?
     * @param content ¿?¿?
     * @return
     * @throws Exception
     */
    public static String PUTMethod(String url,Map<String, Object> argsMap,Map<String,String> headerParam,String content)throws Exception{
        byte[] dataByte = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        //¿?¿?¿?¿?
        if (StringUtils.isNotEmpty(content)) {
            httpPut.setEntity(new ByteArrayEntity(content.getBytes()));
        }
        //¿?¿?Cookies
        if(MapUtils.isNotEmpty(headerParam)){
            Set<Entry<String, String>> entrySet = headerParam.entrySet();
            Iterator<Entry<String, String>> entryIter = entrySet.iterator();
            while(entryIter.hasNext()){
                Entry<String,String> entry = entryIter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                httpPut.setHeader(key, value);
            }
        }
        //¿?¿?¿?¿?
        if (MapUtils.isNotEmpty(argsMap)) {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(setHttpParams(argsMap), "UTF-8");
            httpPut.setEntity(encodedFormEntity);
        }
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpPut);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpPut.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    public static String DELETEMethod(String url,Map<String, Object> argsMap,Map<String,String> headerParam)throws Exception{
        byte[] dataByte = null;
        url = formatGetParameter(url, argsMap);
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(url);
        //¿?¿?Cookies
        if(MapUtils.isNotEmpty(headerParam)){
            Set<Entry<String, String>> entrySet = headerParam.entrySet();
            Iterator<Entry<String, String>> entryIter = entrySet.iterator();
            while(entryIter.hasNext()){
                Entry<String,String> entry = entryIter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                httpDelete.setHeader(key, value);
            }
        }
        // ¿?¿?¿?¿?
        HttpResponse httpResponse = httpClient.execute(httpDelete);
        // ¿?¿?¿?¿?¿?¿?¿?
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            byte[] responseBytes = getData(httpEntity);
            dataByte = responseBytes;
            httpDelete.abort();
        }
        //¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
        String result = bytesToString(dataByte);
        return result;
    }
     
    /**
     * ¿?¿?GET¿?¿?¿?¿?¿?¿?¿?¿?¿?
     * @param url ¿?¿?
     * @param argsMap ¿?¿?
     * @return String
     */
    public static String formatGetParameter(String url,Map<String, Object> argsMap)throws Exception{
        if (url!=null && url.length()>0 && MapUtils.isNotEmpty(argsMap)) {
            if (!url.endsWith("?")) {
                url = url +"?";
            }
            if (argsMap!=null && !argsMap.isEmpty()) {
                Set<Entry<String, Object>> entrySet = argsMap.entrySet();
                Iterator<Entry<String, Object>> iterator = entrySet.iterator();
                while(iterator.hasNext()){
                    Entry<String, Object> entry = iterator.next();
                    if (entry!=null) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
//                      Object value = URLEncoder.encode(entry.getValue().toString(), "UTF-8");
                        url = url + key + "=" + value;
                        if (iterator.hasNext()) {
                            url = url +"&";
                        }
                    }
                }
            }
        }
        return url;
    }
     
    /**
     * ¿?¿?Entity¿?¿?¿?
     * @param httpEntity
     * @return
     * @throws Exception
     */
    public static byte[] getData(HttpEntity httpEntity) throws Exception{
        BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedHttpEntity.writeTo(byteArrayOutputStream);
        byte[] responseBytes = byteArrayOutputStream.toByteArray();
        return responseBytes;
    }
     
    /**
     * ¿?¿?HttpPost¿?¿?¿?¿?
     * @param argsMap
     * @return BasicHttpParams
     */
    private static List<BasicNameValuePair> setHttpParams(Map<String, Object> argsMap){
        List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
        //¿?¿?¿?¿?¿?¿?
        if (argsMap!=null && !argsMap.isEmpty()) {
            Set<Entry<String, Object>> set = argsMap.entrySet();
            Iterator<Entry<String, Object>> iterator = set.iterator();
            while(iterator.hasNext()){
                Entry<String, Object> entry = iterator.next();
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                nameValuePairList.add(basicNameValuePair);
            }
        }
        return nameValuePairList;
    }
     
    /**
     * ¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String bytesToString(byte[] bytes) throws UnsupportedEncodingException{
        if (bytes!=null) {
            String returnStr = new String(bytes,"utf-8");
            returnStr = StringUtils.trim(returnStr);
            return returnStr;
        }
        return null;
    }
}
