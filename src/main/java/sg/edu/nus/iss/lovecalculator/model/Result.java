package sg.edu.nus.iss.lovecalculator.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Result implements Serializable{
    private String fname;
    private String sname;
    private int percentage;
    private String result;
    private String id;

    public Result() {
        this.id = UUID.randomUUID().toString();
    }

    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public int getPercentage() {
        return percentage;
    }
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Result jsonStringToJavaObject(String json) throws IOException {
        Result r = new Result();

        if (json!=null) {
            InputStream is = new ByteArrayInputStream(json.getBytes());
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            
            // remove encoding chars from API (eg. %20 for space)
            String decodedSname = URLDecoder.decode(jsonObject.getString("fname"), "UTF-8");
            String decodedFname = URLDecoder.decode(jsonObject.getString("sname"), "UTF-8");

            r.setFname(decodedSname);
            r.setSname(decodedFname);
            r.setPercentage(jsonObject.getJsonNumber("percentage").intValue());
            r.setResult(jsonObject.getString("result"));
        }
        return r;
    }

    public static Result ApiResponseToJavaObject(String json) throws IOException {
        Result r = new Result();

        if (json!=null) {
            InputStream is = new ByteArrayInputStream(json.getBytes());
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            
            // remove encoding chars from API (eg. %20 for space)
            String decodedSname = URLDecoder.decode(jsonObject.getString("fname"), "UTF-8");
            String decodedFname = URLDecoder.decode(jsonObject.getString("sname"), "UTF-8");

            r.setFname(decodedSname);
            r.setSname(decodedFname);
            r.setPercentage(Integer.parseInt(jsonObject.getString("percentage")));
            r.setResult(jsonObject.getString("result"));
        }
        return r;
    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                    .add("sname", this.getSname())
                    .add("fname", this.getFname())
                    .add("percentage", this.getPercentage())
                    .add("result", this.getResult())
                    .add("id", this.getId())
                    .build();
    }
}
