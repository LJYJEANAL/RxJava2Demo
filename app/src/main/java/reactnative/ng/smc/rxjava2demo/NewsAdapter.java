package reactnative.ng.smc.rxjava2demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewsAdapter {
    private List<NewsResultEntity> mNewsResultEntities = new ArrayList<>();

    public NewsAdapter(List<NewsResultEntity> mNewsResultEntities) {
        this.mNewsResultEntities = mNewsResultEntities;
    }
}
