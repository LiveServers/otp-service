package com.mobilit.otpservice.response;

public class OtpResponse {
    private Object body;
    private OtpHeaders headers;

    public OtpResponse(Object body) {
        this.body = body;
    }

    public OtpResponse(OtpHeaders headers, Object body) {
        this.body = body;
        this.headers = headers;
    }

    public OtpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(OtpHeaders headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
