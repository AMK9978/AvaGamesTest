package Models.Request;

import com.google.gson.Gson;

public class Response<T> {
    private T data;
    private int message;
    private Status status;
    public static final int ERROR_COOL_DOWN = 100;
    public static final int BIN_AVAILABLE = 101;
    public static final int ERROR_HANDLE = 102;
    public static final int SUCCESSFUL_ASSIGN = 103;
    public static final int SUCCESSFUL_LOGIN = 104;
    public static final int SUCCESSFUL_LOGOUT = 105;
    public static final int ERROR_LOGIN = 106;
    public static final int ERROR_NO_BIN = 107;
    public static final int ERROR_LOGOUT = 108;
    public static final int EXIT = 109;

    private Response(T data, int message, Status status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> Response successful(T data, int message) {
        return new Response(data, message, Status.Successful);
    }

    public static <T> Response failed(T data, int message) {
        return new Response(data, message, Status.Failed);
    }

    public static <T> Response loading(T data, int message) {
        return new Response<T>(data, message, Status.Loading);
    }

    private static String toJson(Response response){
        return new Gson().toJson(response);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
