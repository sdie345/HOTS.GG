package kr.swote.hotsgg.functions.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OtherAbility {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("displayText")
    @Expose
    private String displayText;
    @SerializedName("cooldown")
    @Expose
    private String cooldown;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("description", description).append("displayText", displayText).append("cooldown", cooldown).append("slug", slug).append("icon", icon).toString();
    }
}
