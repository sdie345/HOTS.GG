package kr.swote.hotsgg.functions.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class HeroData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("role")
    @Expose
    private Role role;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("circleIcon")
    @Expose
    private String circleIcon;
    @SerializedName("cardPortrait")
    @Expose
    private String cardPortrait;
    @SerializedName("franchise")
    @Expose
    private String franchise;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("isNew")
    @Expose
    private Boolean isNew;
    @SerializedName("winRate")
    @Expose
    private float winRate;
    @SerializedName("pickRate")
    @Expose
    private float pickRate;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("trait")
    @Expose
    private Trait trait;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("abilities")
    @Expose
    private List<Ability> abilities = null;
    @SerializedName("heroicAbilities")
    @Expose
    private List<HeroicAbility> heroicAbilities = null;
    @SerializedName("otherAbilities")
    @Expose
    private List<OtherAbility> otherAbilities = null;
    @SerializedName("expandedRole")
    @Expose
    private ExpandedRole expandedRole;
    @SerializedName("characteristic")
    @Expose
    private List<Characteristic> characteristic = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCircleIcon() {
        return circleIcon;
    }

    public void setCircleIcon(String circleIcon) {
        this.circleIcon = circleIcon;
    }

    public String getCardPortrait() {
        return cardPortrait;
    }

    public void setCardPortrait(String cardPortrait) {
        this.cardPortrait = cardPortrait;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public float getWinRate() { return winRate; }

    public void setWinRate(float winRate) { this.winRate = winRate; }

    public float getPickRate() { return pickRate; }

    public void setPickRate(float pickRate) { this.pickRate = pickRate; }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Trait getTrait() {
        return trait;
    }

    public void setTrait(Trait trait) {
        this.trait = trait;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<HeroicAbility> getHeroicAbilities() {
        return heroicAbilities;
    }

    public void setHeroicAbilities(List<HeroicAbility> heroicAbilities) {
        this.heroicAbilities = heroicAbilities;
    }

    public List<OtherAbility> getOtherAbilities() {
        return otherAbilities;
    }

    public void setOtherAbilities(List<OtherAbility> otherAbilities) {
        this.otherAbilities = otherAbilities;
    }

    public ExpandedRole getExpandedRole() {
        return expandedRole;
    }

    public void setExpandedRole(ExpandedRole expandedRole) {
        this.expandedRole = expandedRole;
    }

    public List<Characteristic> getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(List<Characteristic> characteristic) {
        this.characteristic = characteristic;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("title", title).append("role", role).append("type", type).append("description", description).append("shortDescription", shortDescription).append("difficulty", difficulty).append("nova_circle_icon", circleIcon).append("winRate", winRate).append("pickRate").append("cardPortrait", cardPortrait).append("franchise", franchise).append("release", release).append("isNew", isNew).append("slug", slug).append("trait", trait).append("href", href).append("abilities", abilities).append("heroicAbilities", heroicAbilities).append("otherAbilities", otherAbilities).append("expandedRole", expandedRole).append("characteristic", characteristic).toString();
    }
}