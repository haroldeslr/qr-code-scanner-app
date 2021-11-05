package com.year3project.qrcodescanner;

import java.io.Serializable;

public class LogData implements Serializable {
    public String fullName;
    public String contactNumber;
    public String address;
    public String age;
    public String temperature;
    public String gender;
    public String reason;
    public String dateAndTime;

    public boolean validQRData;
}
