package client_server.domain;


public class JsonResponse{

    String reply;

    public JsonResponse() {
    }

    public void putField(String value){
        reply = "{\"message\":\""+value+"\"}";
    }

    public void putObject(String value){
        reply = "{\"object\":"+value+"}";
    }

    public String toString(){
        return reply;
    }
}
