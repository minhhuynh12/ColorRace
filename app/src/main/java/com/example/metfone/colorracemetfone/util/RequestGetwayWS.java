package com.example.metfone.colorracemetfone.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import org.simpleframework.xml.Serializer;

import com.example.metfone.colorracemetfone.commons.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class RequestGetwayWS {
    private DefaultHttpClient client;
    private String resultXML;
    private StringBuilder params;
    private StringBuilder params2;
    private StringBuilder paramObj;
    private static Context act;

    public RequestGetwayWS(Context act) {
        params = new StringBuilder();
        params2 = new StringBuilder();
        paramObj = new StringBuilder();
        RequestGetwayWS.act = act;
    }

    public int sendRequestWS(String linkWS, String wsName, String className, String methodName, Activity act) {
//        userLogin = convertUserName(act);
        String entityRes = null;
        String request = null;
        request = createXMLRequest(wsName);

        client = new DefaultHttpClient();
        HttpPost post = new HttpPost(linkWS);
        Log.i("11", "request : " + request);
//        LogUtil.d("requestttttt", "request : " + request);
        try {
            // Set connection timeout
            int timeoutConnection = 60000;
            HttpConnectionParams.setConnectionTimeout(post.getParams(),
                    timeoutConnection);
            // set socket timeout
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(post.getParams(), timeoutSocket);
            StringEntity entity = new StringEntity(request, "UTF-8");
            post.setHeader("Content-Type", "text/xml; charset=UTF-8");
            post.setEntity(entity);
            HttpResponse res = client.execute(post);

            if (res != null && res.getEntity() != null) {
                entityRes = EntityUtils.toString(res.getEntity(), "UTF-8");
                entityRes = convertSpecialCharacter(entityRes);
                Log.i("11", "result : " + entityRes);
                resultXML = entityRes;

                Log.d("responseaaa", "values " + resultXML);

                String errorGW = getErrorCodeGW();
                String errorWS = "";
                String errorMessage = "";
                if (errorGW != null && errorGW.equals("0")) {
                    errorWS = getErrorCode();
                    errorMessage = getErrorDecription();
                    if (errorWS != null) {

                        if (errorWS.equals("0")) {

                        } else if (errorWS.equals("3")) {

                        } else {

                        }
                        errorWS = errorWS.replaceAll("-", "");
                    } else {

                    }

                }

                clearParams();
                if (errorGW == null) {
                    return -1;
                }
                return 1;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.i("4", "Error : " + e.getMessage());
        }
        return -1;
    }

    public String createXMLRequest(String wsName) {
        StringBuilder envelope = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\" >");
        envelope.append("<soapenv:Header/>");
        envelope.append("<soapenv:Body>");
        envelope.append("<web:gwOperation>");
        envelope.append("<Input>");
        envelope.append("<wscode>" + wsName + "</wscode>");
        envelope.append("<username>" + Constant.BCCSGW_USER + "</username>");
        envelope.append("<password>" + Constant.BCCSGW_PASS + "</password>");
//        if (!wsName.equals("doLoginEncryptRSA")) {
//            addParam("token", MainActivity.token);
//        }
//        addParam("locale", getLanguageCode());
        envelope.append(params.toString());
        if (paramObj.length() > 0 || !params2.toString().isEmpty()) {
            envelope.append("<rawData><![CDATA[");
            envelope.append("<ws:" + wsName + ">");
            envelope.append(paramObj.toString());
            envelope.append(params2.toString());
//            envelope.append("<token>" + MainActivity.token + "</token>");
//            envelope.append("<locale>" + getLanguageCode() + "</locale>");
            envelope.append("</ws:" + wsName + ">");
            envelope.append("]]></rawData>");
        }
        envelope.append("</Input>");
        envelope.append("</web:gwOperation>");
        envelope.append("</soapenv:Body>");
        envelope.append("</soapenv:Envelope>");
        return envelope.toString();
    }

    public void addParam(String tag, String val) {
        params.append("<param name=\"" + tag + "\" value=\"" + val + "\"/>");
    }

    private String convertSpecialCharacter(String entityRes) {
        entityRes = entityRes.replaceAll("&quot;", "\"");
        entityRes = entityRes.replaceAll("&lt;", "<");
        entityRes = entityRes.replaceAll("&gt;", ">");
        entityRes = entityRes.replaceAll("\n", "");
        entityRes = entityRes.replaceAll("&amp;", "&");
        return entityRes;
    }

    public String getErrorCodeGW() {
        return parseXMLToErrorGW(resultXML);
    }

    public static String parseXMLToErrorGW(String xml) {
        if (xml != null && !xml.isEmpty()) {
            String strTag = "<error>";
            int startIndex = xml.indexOf("<error>");
            int endIndex = xml.indexOf("</error>");
            if (startIndex > -1 && endIndex > -1) {
                xml = xml.substring(startIndex, endIndex);
                xml = xml.substring(strTag.length());
                return xml.trim();
            }
        }
        return null;
    }

    public String getErrorCode() {
        return parseXMLToErrorCode(resultXML);
    }

    public static String parseXMLToErrorCode(String xml) {
        if (xml != null && !xml.isEmpty()) {
            String strTag = "<errorCode>";
            int startIndex = xml.indexOf("<errorCode>");
            int endIndex = xml.indexOf("</errorCode>");
            if (startIndex > -1 && endIndex > -1) {
                xml = xml.substring(startIndex, endIndex);
                xml = xml.substring(strTag.length());
                return xml.trim();
            }
        }
        return null;
    }

    public String getErrorDecription() {
        return parseXMLToErrorDecription(resultXML);
    }

    public static String parseXMLToErrorDecription(String xml) {
        if (xml != null && !xml.isEmpty()) {
            String strTag = "<errorDecription>";
            int startIndex = xml.indexOf("<errorDecription>");
            int endIndex = xml.indexOf("</errorDecription>");
            if (startIndex > -1 && endIndex > -1) {
                xml = xml.substring(startIndex, endIndex);
                xml = xml.substring(strTag.length());
                return xml.trim();
            }
        }
        return null;
    }

    public void clearParams() {
        params = new StringBuilder();
        params2 = new StringBuilder();
        paramObj = new StringBuilder();
    }

    public Object parseXMLToObject(String xml, Class c) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Serializer serializer = new Persister();
            Object obj = serializer.read(c, is);
            return c.cast(obj);
        } catch (Exception ex) {
            Log.i("11", "Ex : " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public String getResultXML() {
        return resultXML;
    }

    public <T> ArrayList<T> parseXMLToListObject(String objName,
                                                 Class<T> className) {
        String resultOriginal = "";
        int start = resultXML.indexOf("<original>");
        int end = resultXML.indexOf("</original>");
        if (start > -1 && end > -1)
            resultOriginal = resultXML.substring(start, end);
        String xml = resultOriginal;
        String strFind = objName;
        String strEndTag = "</" + strFind + ">";
        String strStartTag = "<" + strFind + ">";
        T obj = null;
        ArrayList<T> lstRes = new ArrayList<T>();
        if (xml != null && !xml.isEmpty()) {
            int startIndex = xml.indexOf(strStartTag);
            int endIndex = xml.lastIndexOf(strEndTag);
            if (startIndex > -1 && endIndex > -1) {
                xml = xml.substring(startIndex, endIndex);
                if (!xml.isEmpty()) {
                    String[] arrFind = xml.split(strEndTag);
                    for (int i = 0; i < arrFind.length; i++) {
                        arrFind[i] += strEndTag;
                        arrFind[i] = arrFind[i].trim();
                        obj = (T) parseXMLToObject(arrFind[i], className);
                        if (obj != null) {
                            lstRes.add(obj);
                        }
                    }
                }
            }
        }
        return lstRes;
    }

}
