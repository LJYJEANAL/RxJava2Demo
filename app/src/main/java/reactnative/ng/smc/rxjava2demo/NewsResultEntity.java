package reactnative.ng.smc.rxjava2demo;

/**
 * Created by Administrator on 2017/9/1.
 */

public class NewsResultEntity {
    private String type;
    private String publishedAt;
    private String desc;
    private String who;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "NewsResultEntity{" +
                "type='" + type + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", who='" + who + '\'' +
                '}';
    }
}
