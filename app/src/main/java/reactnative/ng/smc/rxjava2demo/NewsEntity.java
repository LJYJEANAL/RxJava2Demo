package reactnative.ng.smc.rxjava2demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewsEntity {
    private boolean error;
    private List<NewsResultEntity> results = new ArrayList<>();

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsResultEntity> getResults() {
        return results;
    }

    public void setResults(List<NewsResultEntity> results) {
        this.results = results;
    }

}
