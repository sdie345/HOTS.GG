package kr.swote.hotsgg.functions.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Characteristic {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("tier")
    @Expose
    private String tier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("text", text).append("tier", tier).toString();
    }

}