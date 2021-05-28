//package com.MBS.Utility;
//
//
//import com.MBS.Init.*;
//import com.atlogys.APIResources.HTTPMethodsFactory;
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.jayway.jsonpath.JsonPath;
//import io.restassured.response.Response;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//
//public class JsonFormatValidator {
//
//    public static boolean isJSONValid(String jsonString) {
//
//        try {
//            new JSONObject(jsonString);
//        } catch (JSONException ex) {
//
//            try {
//                new JSONArray(jsonString);
//            } catch (JSONException ex1) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    APIResponse response= APIResponse.getInstance();
//
//    public JsonObject createJSON(String jsonFile, String key, String value){
//
//                Gson gson = new Gson();
//
//                JsonObject inputJsonObj  = gson.fromJson(jsonFile, JsonObject.class);
//
//                inputJsonObj.getAsJsonObject().addProperty(key,value);
//
//            return inputJsonObj;
//
//        }
//
//
//        public String appendJson(String jsonAsString, String ...param ){
//
//        System.out.println("Param 1: "+param[0] +" Param 2 :   "+ param[1]+ " Param 3 :  "+param[2]);
//
//            Gson gson = new Gson();
//
//            JsonObject inputJsonObj  = gson.fromJson(jsonAsString, JsonObject.class);
//            JsonElement element = new JsonParser().parse(inputJsonObj.toString());
//
//            try {
//
//                System.out.println("Before appending>> "+element);
//                element.getAsJsonObject().getAsJsonObject(param[0]).addProperty(param[1], param[2]);
//                System.out.println("After Appending-->>"+element);
//
//            }catch (NullPointerException nulExc){
//                nulExc.getMessage();
//                System.out.println("Null pointer exception found, Please provide parameter in method of class - "
//                        + this.getClass().getName()+"  as : Key , subKey and its Value , this is for nested JSON object");
//                return null;
//            }
//
//            return element.toString();
//
//        }
//
//    public static void main(String[] args) {
//
//
//        JsonFormatValidator obj = new JsonFormatValidator();
//
//        String json= "{\n" +
//                "   \"authentication\": {\n" +
//                "\t       \"userId\": \"8c93b7eb326b11e8b43e0245fcfcd0b5\",\n" +
//                "\t       \"password\": \"secret123\",\n" +
//                "\t       \"entityId\": \"asdfr123456trdter6453e\"\n" +
//                "   },\n" +
//                "   \"merchantTransactionId\": \"rajdeep1345612346134651346513461\",\n" +
//                "   \"amount\": -50.00,\n" +
//                "   \"currency\": \"123\",\n" +
//                "   \"paymentBrand\": \"masterpass\",\n" +
//                "   \"paymentType\": \"DB\"\n" +
//                "}";
//
//        String inputString = "{\n" +
//                "    \"result\": {\n" +
//                "        \"code\": \"200.300.404\",\n" +
//                "        \"description\": \"invalid or missing parameter\",\n" +
//                "        \"parameterErrors\": [\n" +
//                "            {\n" +
//                "                \"name\": \"amount\",\n" +
//                "                \"value\": null,\n" +
//                "                \"message\": \"may not be empty\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\": \"paymentBrand\",\n" +
//                "                \"value\": null,\n" +
//                "                \"message\": \"card properties must be set\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\": \"currency\",\n" +
//                "                \"value\": null,\n" +
//                "                \"message\": \"may not be empty\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\": \"paymentType\",\n" +
//                "                \"value\": null,\n" +
//                "                \"message\": \"may not be null\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"name\": \"authentication\",\n" +
//                "                \"value\": null,\n" +
//                "                \"message\": \"may not be null\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    },\n" +
//                "    \"buildNumber\": \"b18370597d5469517026bddda660913e660f2fcf@2018-06-07 09:37:10 +0000\",\n" +
//                "    \"timestamp\": \"2018-06-07 10:54:13+0000\",\n" +
//                "    \"ndc\": \"f0ae45efbd1a485ca4d015378fe390e9\"\n" +
//                "}";
//
//        //System.out.println( obj.createJSON(json,"paymentType","555555"));
//
//
//
//        HTTPMethodsFactory methods = new HTTPMethodsFactory();
//        APIResponse.getInstance().setResponse(methods.POST(json));
//        Response response = APIResponse.getInstance().getResponse();
//
//
//        System.out.println(response.prettyPrint());
//        //System.out.println( "TEST++++"+JsonPath.read(response.asString(),"result.parameterErrors[?(@.name=='amou')].message"));
//
//        String s = JsonPath.read(response.asString(),"result.parameterErrors[?(@.name=='amount')].message").toString();
//        if (s .equals("[]")){
//            System.out.println("Nothing found ");
//        }else {
//            System.out.println("Value is  "+s.substring(2,s.length()-2));
//        }
//
//
//
//    }
//
//
//}
//
//
//
//
//
