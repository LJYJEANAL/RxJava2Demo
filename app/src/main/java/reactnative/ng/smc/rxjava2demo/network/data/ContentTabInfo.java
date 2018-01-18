package reactnative.ng.smc.rxjava2demo.network.data;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ContentTabInfo {
    private int id;
    private String  sectionName;
    private int sectionType;
    private String jsonPrameters;
    private int sortNo;
    private int parentId;
    private String description;
    private String createTime;
    private int portalId;

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

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPortalId() {
        return portalId;
    }

    public void setPortalId(int portalId) {
        this.portalId = portalId;
    }
}
