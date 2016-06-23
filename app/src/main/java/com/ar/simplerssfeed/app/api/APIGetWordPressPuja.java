package com.ar.simplerssfeed.app.api;

import android.content.Context;

import com.ar.simplerssfeed.app.model.ModelWordPressFeed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.radyalabs.async.AsyncHttpResponseHandler;
import com.radyalabs.irfan.util.AppUtility;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


abstract public class APIGetWordPressPuja extends BaseApi{

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    protected ModelWordPressFeed data;
    private JsonObject object;
    private JsonObject json;
    private String rawContent;
    private Gson gson;
    private GsonBuilder gsonBuilder;

    public APIGetWordPressPuja(Context context) {
        super(context);

        ajaxType = AjaxType.GET;
        endpointApi = "limaapril.wordpress.com/posts/";
        //aderifaldi.wordpress.com
        //3294206

        responseHandler = new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int codeReturn, Header[] headers, byte[] content) {
                try {
                    rawContent = new String(content);
                    json = new JsonParser().parse(rawContent).getAsJsonObject();
                    object = json.getAsJsonObject();
                    gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer());
                    gson = gsonBuilder.create();
                    data = gson.fromJson(object, ModelWordPressFeed.class);

                    AppUtility.logD("APIGetWordPressFeed", "Success-Result : " + rawContent);

                    onFinishRequest(true, rawContent);
                } catch(Exception e) {
                    e.printStackTrace();
                    AppUtility.logD("APIGetWordPressFeed", "Exception-Result : " + rawContent);
                    onFinishRequest(false, rawContent);
                }
            }

            @Override
            public void onFailure(int codeReturn, Header[] headers, byte[] content, Throwable error) {
                String textContent = null;
                if (content != null){
                    textContent = new String(content);
                    AppUtility.logD("APIGetWordPressFeed", "Failed-Result : " + textContent);
                }
                onFinishRequest(false, textContent);
            }
        };
    }

    private static final String[] DATE_FORMATS = new String[] {
            DATE_FORMAT
    };

    private class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format).parse(jsonElement.getAsString());
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }

}
