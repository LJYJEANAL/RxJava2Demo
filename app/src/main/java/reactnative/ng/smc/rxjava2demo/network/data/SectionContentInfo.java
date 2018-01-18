package reactnative.ng.smc.rxjava2demo.network.data;

public class SectionContentInfo {
    private String horizontalPic;
    /**
     * 内容id
     */
    private int id;
    /**
     * 内容类型
     */
    private int type;
    /**
     * 栏目id
     */
    private int sectionId;
    private int contentType;

    public String getHorizontalPic() {
        return horizontalPic;
    }

    public void setHorizontalPic(String horizontalPic) {
        this.horizontalPic = horizontalPic;
    }

    /**
     * 栏目名称
     */

    private String sectionName;
    /**
     * 所属栏目父栏目id
     */
    private int sectionParent;
    /**
     * 内容名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 内容描述（捉取回来的）
     */
    private String content;
    /**
     * 内容描述
     */
    private String description;
    /**
     * 内容所属分类
     */
    private int categoryId;
    /**
     * 状态
     */
    private int status;
    private String area;
    private String tags;
    private int feeFlag;
    private String PartentFeeFlag_tag;
    private int PartentFeeFlag;

    public String getPartentFeeFlag_tag() {
        return PartentFeeFlag_tag;
    }

    public void setPartentFeeFlag_tag(String partentFeeFlag_tag) {
        PartentFeeFlag_tag = partentFeeFlag_tag;
    }

    public int getPartentFeeFlag() {
        return PartentFeeFlag;
    }

    public void setPartentFeeFlag(int partentFeeFlag) {
        PartentFeeFlag = partentFeeFlag;
    }

    public int getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(int feeFlag) {
        this.feeFlag = feeFlag;
    }

    /**
     * 关键字
     */
    private String keywords;
    /**
     * 来源url
     */
    private String sourceUrl;
    /**
     * 封面（横图）
     */
    private String cover;
    /**
     * 封面（竖图）
     */
    private String cover2;
    /**
     * 访问地址
     */
    private String visitPath;
    private String createTime;
    /**
     * 是否火热
     */
    private int isHot;
    /**
     * 播放次数
     */
    private int playCount;
    /**
     * 作者名称（视频上传用户名称）
     */
    private String originalName;
    /**
     * 作者id（自媒体视频id）
     */
    private int originalId;
    /**
     * 作者肖像（视频上传用户肖像）
     */
    private String headImg;
    /**
     * 是否已查询数据库
     */
    private boolean query;
    /**
     * 是否已点赞
     */
    private boolean praise;
    /**
     * 是否已加载内容图片
     */
    private boolean loadContentImg;
    /**
     * 是否已加载用户头像
     */
    private boolean loadHeadimg;
    /**
     * 自媒体认证
     */
    private String autoName;

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionParent() {
        return sectionParent;
    }

    public void setSectionParent(int sectionParent) {
        this.sectionParent = sectionParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String content) {
        this.description = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover2() {
        return cover2;
    }

    public void setCover2(String cover2) {
        this.cover2 = cover2;
    }

    public String getVisitPath() {
        return visitPath;
    }

    public void setVisitPath(String visitPath) {
        this.visitPath = visitPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public boolean isPraise() {
        return praise;
    }

    public void setPraise(boolean praise) {
        this.praise = praise;
    }

    public boolean isLoadContentImg() {
        return loadContentImg;
    }

    public void setLoadContentImg(boolean loadContentImg) {
        this.loadContentImg = loadContentImg;
    }

    public boolean isLoadHeadimg() {
        return loadHeadimg;
    }

    public void setLoadHeadimg(boolean loadHeadimg) {
        this.loadHeadimg = loadHeadimg;
    }

    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }
}
