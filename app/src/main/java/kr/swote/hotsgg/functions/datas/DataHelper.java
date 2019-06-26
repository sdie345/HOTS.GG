package kr.swote.hotsgg.functions.datas;

import android.content.Context;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataHelper {

    private static List<HeroSuggestion> heroSuggestions = new ArrayList<>();

    public interface OnFindColorsListener {
        void onResults(List<HeroSuggestion> results);
    }
    public static void setHeroSuggestions(List<HeroSuggestion> mheroSuggestions) {
        heroSuggestions.addAll(mheroSuggestions);
    }
    public interface OnFindSuggestionsListener {
        void onResults(List<HeroSuggestion> results);
    }

    public static List<HeroSuggestion> getHistory(Context context, int count) {

        List<HeroSuggestion> suggestionList = new ArrayList<>();
        HeroSuggestion heroSuggestion;
        for (int i = 0; i < heroSuggestions.size(); i++) {
            heroSuggestion = heroSuggestions.get(i);
            heroSuggestion.setIsHistory(true);
            suggestionList.add(heroSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (HeroSuggestion heroSuggestion : heroSuggestions) {
            heroSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<HeroSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (HeroSuggestion suggestion : heroSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, (lhs, rhs) -> lhs.getIsHistory() ? -1 : 0);
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<HeroSuggestion>) results.values);
                }
            }
        }.filter(query);
    }
}