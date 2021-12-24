package com.example.itnews;

public class UserJson {
    /**
     * code : 1000
     * msg : 一切正常
     * data : {"username":"salam0207","nickname":"巴拉啦小仙女2021-04-05 11:25:00.544936","avatar":"http://39.106.195.109/media/avatar/default.jpeg","avatar_90x90":"http://39.106.195.109/media/CACHE/images/avatar/default/727342cf0ca515d53ac45e4e06074d7d.jpeg","info":"这个人很懒，什么都没写","gender":"未知","is_followed":0,"is_fans":0}
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
         * username : salam0207
         * nickname : 巴拉啦小仙女2021-04-05 11:25:00.544936
         * avatar : http://39.106.195.109/media/avatar/default.jpeg
         * avatar_90x90 : http://39.106.195.109/media/CACHE/images/avatar/default/727342cf0ca515d53ac45e4e06074d7d.jpeg
         * info : 这个人很懒，什么都没写
         * gender : 未知
         * is_followed : 0
         * is_fans : 0
         */

        private String username;
        private String nickname;
        private String avatar;
        private String avatar_90x90;
        private String info;
        private String gender;
        private int is_followed;
        private int is_fans;

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

        public int getIs_followed() {
            return is_followed;
        }

        public void setIs_followed(int is_followed) {
            this.is_followed = is_followed;
        }

        public int getIs_fans() {
            return is_fans;
        }

        public void setIs_fans(int is_fans) {
            this.is_fans = is_fans;
        }
    }
}
