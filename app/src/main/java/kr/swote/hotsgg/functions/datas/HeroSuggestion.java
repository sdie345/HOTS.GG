package kr.swote.hotsgg.functions.datas;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class HeroSuggestion implements SearchSuggestion {

    private String heroName;
    private boolean mIsHistory = false;

    public HeroSuggestion(String suggestion) {
        this.heroName = suggestion.toLowerCase();
    }

    public HeroSuggestion(Parcel source) {
        this.heroName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return heroName;
    }

    public static final Creator<HeroSuggestion> CREATOR = new Creator<HeroSuggestion>() {
        @Override
        public HeroSuggestion createFromParcel(Parcel in) {
            return new HeroSuggestion(in);
        }

        @Override
        public HeroSuggestion[] newArray(int size) {
            return new HeroSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(heroName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}