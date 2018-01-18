package reactnative.ng.smc.rxjava2demo.network.data;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ContentInfo {
    private List<SectionContentInfo> results;

    public List<SectionContentInfo> getResults() {
        return results;
    }

    public void setResults(List<SectionContentInfo> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ContentInfo{" +
                "results=" + results +
                '}';
    }
}
