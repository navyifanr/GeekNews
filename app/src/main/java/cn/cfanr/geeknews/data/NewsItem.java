package cn.cfanr.geeknews.data;

import java.io.Serializable;

/**
 * Created by ifanr on 2015/5/3.
 */
public class NewsItem implements Serializable {
    private int id;
    private String userName;
    private String time;
    private String title;
    private int likeNum;
    private int readNum;
    private String hostName;
    private String link;
    private int type;

    public NewsItem() {

    }

    public NewsItem(int id, String userName, String time, int likeNum, String title, int readNum, String hostName, String link, int type) {
        this.id = id;
        this.userName = userName;
        this.time = time;
        this.likeNum = likeNum;
        this.title = title;
        this.readNum = readNum;
        this.hostName = hostName;
        this.link = link;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewsItem[ id=").append(id).append(", userName=").append(userName).append(", time=").append(time).append(", likeNum=").append(likeNum).append(", title=").append(
                title).append(", readNum=").append(readNum).append(", hostName=").append(hostName).append(", link=").append(link).append(", type=").append(type).append("]");
        return builder.toString();
    }
}
