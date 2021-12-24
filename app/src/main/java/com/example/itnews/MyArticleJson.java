package com.example.itnews;

import java.util.List;

public class MyArticleJson {
    /**
     * code : 1000
     * msg : 一切正常
     * data : {"news":[{"id":83,"news_pics_set":["http://39.106.195.109/media/newspics/up_image/image-20210303144633961_H9EnGth.png","http://39.106.195.109/media/newspics/up_image/image-20210303144658311_dZFktMi.png","http://39.106.195.109/media/newspics/up_image/image-20210303144726687_Tnej8z8.png"],"title":"asdasdasd","content":"asdasda","create_time":"2021-12-19T14:13:04.753277","status":1,"like_num":0,"star_num":0,"bb_num":0,"brow_num":0,"tag_type":2,"author":{"id":24,"username":"yjh2021","password":"12345","gender":1,"avatar":"http://39.106.195.109/media/avatar/up_image/avatar2021-12-17_135750.417093.png","create_time":"2021-12-12T19:55:56.092167","email":"1544221620@qq.com","status":1,"nickname":"nickname","info":"info","like_num":0,"star_num":0,"bb_num":0,"follow_num":0,"fans_num":0}}]}
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
        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * id : 83
             * news_pics_set : ["http://39.106.195.109/media/newspics/up_image/image-20210303144633961_H9EnGth.png","http://39.106.195.109/media/newspics/up_image/image-20210303144658311_dZFktMi.png","http://39.106.195.109/media/newspics/up_image/image-20210303144726687_Tnej8z8.png"]
             * title : asdasdasd
             * content : asdasda
             * create_time : 2021-12-19T14:13:04.753277
             * status : 1
             * like_num : 0
             * star_num : 0
             * bb_num : 0
             * brow_num : 0
             * tag_type : 2
             * author : {"id":24,"username":"yjh2021","password":"12345","gender":1,"avatar":"http://39.106.195.109/media/avatar/up_image/avatar2021-12-17_135750.417093.png","create_time":"2021-12-12T19:55:56.092167","email":"1544221620@qq.com","status":1,"nickname":"nickname","info":"info","like_num":0,"star_num":0,"bb_num":0,"follow_num":0,"fans_num":0}
             */

            private int id;
            private String title;
            private String content;
            private String create_time;
            private int status;
            private int like_num;
            private int star_num;
            private int bb_num;
            private int brow_num;
            private int tag_type;
            private AuthorBean author;
            private List<String> news_pics_set;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getBb_num() {
                return bb_num;
            }

            public void setBb_num(int bb_num) {
                this.bb_num = bb_num;
            }

            public int getBrow_num() {
                return brow_num;
            }

            public void setBrow_num(int brow_num) {
                this.brow_num = brow_num;
            }

            public int getTag_type() {
                return tag_type;
            }

            public void setTag_type(int tag_type) {
                this.tag_type = tag_type;
            }

            public AuthorBean getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBean author) {
                this.author = author;
            }

            public List<String> getNews_pics_set() {
                return news_pics_set;
            }

            public void setNews_pics_set(List<String> news_pics_set) {
                this.news_pics_set = news_pics_set;
            }

            public static class AuthorBean {
                /**
                 * id : 24
                 * username : yjh2021
                 * password : 12345
                 * gender : 1
                 * avatar : http://39.106.195.109/media/avatar/up_image/avatar2021-12-17_135750.417093.png
                 * create_time : 2021-12-12T19:55:56.092167
                 * email : 1544221620@qq.com
                 * status : 1
                 * nickname : nickname
                 * info : info
                 * like_num : 0
                 * star_num : 0
                 * bb_num : 0
                 * follow_num : 0
                 * fans_num : 0
                 */

                private int id;
                private String username;
                private String password;
                private int gender;
                private String avatar;
                private String create_time;
                private String email;
                private int status;
                private String nickname;
                private String info;
                private int like_num;
                private int star_num;
                private int bb_num;
                private int follow_num;
                private int fans_num;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public int getBb_num() {
                    return bb_num;
                }

                public void setBb_num(int bb_num) {
                    this.bb_num = bb_num;
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
            }
        }
    }
}
