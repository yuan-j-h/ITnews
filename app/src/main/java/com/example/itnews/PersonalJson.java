package com.example.itnews;

public class PersonalJson {
    /**
     * code : 1000
     * msg : 一切正常
     * data : {"selfid":24,"username":"yjh2021","nickname":"???","info":"yingyuzuowen","gender":"男","like_num":0,"star_num":0,"follow_num":0,"fans_num":0,"status":1,"avatar":"http://39.106.195.109/media/avatar/default.jpeg","avatar_90x90":"http://39.106.195.109/media/CACHE/images/avatar/default/727342cf0ca515d53ac45e4e06074d7d.jpeg"}
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
         * selfid : 24
         * username : yjh2021
         * nickname : ???
         * info : yingyuzuowen
         * gender : 男
         * like_num : 0
         * star_num : 0
         * follow_num : 0
         * fans_num : 0
         * status : 1
         * avatar : http://39.106.195.109/media/avatar/default.jpeg
         * avatar_90x90 : http://39.106.195.109/media/CACHE/images/avatar/default/727342cf0ca515d53ac45e4e06074d7d.jpeg
         */

        private int selfid;
        private String username;
        private String nickname;
        private String info;
        private String gender;
        private int like_num;
        private int star_num;
        private int follow_num;
        private int fans_num;
        private int status;
        private String avatar;
        private String avatar_90x90;

        public int getSelfid() {
            return selfid;
        }

        public void setSelfid(int selfid) {
            this.selfid = selfid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public int getStar_num() {
            return star_num;
        }

        public void setStar_num(int star_num) {
            this.star_num = star_num;
        }

        public int getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(int follow_num) {
            this.follow_num = follow_num;
        }

        public int getFans_num() {
            return fans_num;
        }

        public void setFans_num(int fans_num) {
            this.fans_num = fans_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar_90x90() {
            return avatar_90x90;
        }

        public void setAvatar_90x90(String avatar_90x90) {
            this.avatar_90x90 = avatar_90x90;
        }
    }
}
