package com.example.itnews;

import java.util.List;

public class DetailJson {

    /**
     * code : 1000
     * msg : 一切正常
     * data : {"author_id":17,"title":"剪头日","content":"salam","tag":4,"pics":["http://39.106.195.109/media/newspics/up_image/newspics2021-04-05_032702.593749.jpg"],"like_num":2,"comment_num":0,"star_num":1,"create_time":"2021-04-05T11:28:02.685718","isLike":0,"isStar":0,"isFollow":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * author_id : 17
         * title : 剪头日
         * content : salam
         * tag : 4
         * pics : ["http://39.106.195.109/media/newspics/up_image/newspics2021-04-05_032702.593749.jpg"]
         * like_num : 2
         * comment_num : 0
         * star_num : 1
         * create_time : 2021-04-05T11:28:02.685718
         * isLike : 0
         * isStar : 0
         * isFollow : 0
         */

        private int author_id;
        private String title;
        private String content;
        private int tag;
        private int like_num;
        private int comment_num;
        private int star_num;
        private String create_time;
        private int isLike;
        private int isStar;
        private int isFollow;
        private List<String> pics;

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getStar_num() {
            return star_num;
        }

        public void setStar_num(int star_num) {
            this.star_num = star_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsStar() {
            return isStar;
        }

        public void setIsStar(int isStar) {
            this.isStar = isStar;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
