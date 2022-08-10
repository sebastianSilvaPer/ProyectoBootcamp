package com.proyecto.bootcamp.Exceptions;

public class JsonResponse {
    private String code;
    private String message;
    private String descripction;

    public JsonResponse() {
    }

    public JsonResponse(String code, String message, String descripction) {
        this.code = code;
        this.message = message;
        this.descripction = descripction;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescripction() {
        return this.descripction;
    }

    public void setDescripction(String descripction) {
        this.descripction = descripction;
    }

    @Override
    public String toString() {
        return "{" +
                " code='" + getCode() + "'" +
                ", message='" + getMessage() + "'" +
                ", descripction='" + getDescripction() + "'" +
                "}";
    }
}