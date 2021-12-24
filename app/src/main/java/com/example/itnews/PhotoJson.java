package com.example.itnews;

public class PhotoJson {
    /**
     * code : 1000
     * msg : 一切正常
     * data : {"img_url":"http://39.106.195.109/media/up_image/avatar2021-12-16_125504.910181.png","img_id":197}
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
         * img_url : http://39.106.195.109/media/up_image/avatar2021-12-16_125504.910181.png
         * img_id : 197
         */

        private String img_url;
        private int img_id;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getImg_id() {
            return img_id;
        }

        public void setImg_id(int img_id) {
            this.img_id = img_id;
        }
    }
}
