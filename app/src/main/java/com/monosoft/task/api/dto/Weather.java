package com.monosoft.task.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @Expose
    @SerializedName("icon")
    private String icon;

    public String getIcon() {
        return icon;
    }
}
