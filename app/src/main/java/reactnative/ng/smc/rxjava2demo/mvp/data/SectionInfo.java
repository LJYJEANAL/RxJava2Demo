package reactnative.ng.smc.rxjava2demo.mvp.data;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class SectionInfo {
   private int id;
    private String sectionName;
    private int sectionType;
    private String jsonPrameters;
    private String secondQuery;
    private int sortNo;
    private String description;
    private List<SectionContents> sectionContents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionType() {
        return sectionType;
    }

    public void setSectionType(int sectionType) {
        this.sectionType = sectionType;
    }

    public String getJsonPrameters() {
        return jsonPrameters;
    }

    public void setJsonPrameters(String jsonPrameters) {
        this.jsonPrameters = jsonPrameters;
    }

    public String getSecondQuery() {
        return secondQuery;
    }

    public void setSecondQuery(String secondQuery) {
        this.secondQuery = secondQuery;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SectionContents> getSectionContents() {
        return sectionContents;
    }

    public void setSectionContents(List<SectionContents> sectionContents) {
        this.sectionContents = sectionContents;
    }
}
