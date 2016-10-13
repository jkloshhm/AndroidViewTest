package com.jkloshhm.headlinenews;

/**
 * Created by guojian on 10/12/16.
 */

/** "title":"在农村，卖这个看着暴利，其实能赔死！",
      "date":"2016-10-12 13:27",
      "author_name":"快乐农人驿站",
      "thumbnail_pic_s":"http://01.imgmini.eastday.com/mobile/20161012/20161012132717_4c88e122ef1244951a73f7c23c9815c0_1_mwpm_03200403.jpeg",
      "thumbnail_pic_s02":"http://01.imgmini.eastday.com/mobile/20161012/20161012132717_4c88e122ef1244951a73f7c23c9815c0_1_mwpl_05500201.jpeg",
      "thumbnail_pic_s03":"http://01.imgmini.eastday.com/mobile/20161012/20161012132717_4c88e122ef1244951a73f7c23c9815c0_1_mwpl_05500201.jpeg",
      "url":"http://mini.eastday.com/mobile/161012132717864.html?qid=juheshuju",
      "uniquekey":"161012132717864",
      "type":"头条",
      "realtype":"财经"
*/

public class NewsBean {
    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String url;
    private String real_type;

    public NewsBean(String author_name, String date, String real_type, String thumbnail_pic_s, String title, String url) {
        this.author_name = author_name;
        this.date = date;
        this.real_type = real_type;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.title = title;
        this.url = url;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReal_type() {
        return real_type;
    }

    public void setReal_type(String real_type) {
        this.real_type = real_type;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
