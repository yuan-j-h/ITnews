package com.example.itnews;

import java.util.List;

public class StarJson {
    /**
     * code : 1000
     * msg : 一切正常
     * data : {"news":[{"id":63,"title":"清明节","content":"清明，是二十四节气之一，也是中华民族重要的传统节日之一。又是一年清明，正因为万物生长、天清地明，才有了这个唯一既是节气名又是节日名的\u201c清明\u201d。\n\n清明，这是一个忧伤与欢乐的交织的时节。很多人会按照习俗去到郊外扫墓祭祖，祭奠那些不逝的哀思；很多人会在春光里去田间踏春，不管是清清的河畔沐浴春色，还是在在溪边宴饮，曲水流觞都是是欢乐的雅集。\n\n清明就是这样一个是哀思中蕴藏着生机与希望的诗意存在，独一无二充满着不尽的魅力。\n\n\u201c佳节清明桃李笑，野田荒垄只生愁\u201d是对故人的怀念；\u201c素衣莫起风尘叹，犹及清明可到家\u201d是游子思乡心切；\u201c梨花风起正清明，游子寻春半出城\u201d是欢快地畅游。这些都是关于清明飞扬在宋诗里最恰当的表达。\n\n而宋词作为那个时代最为瑰丽的文学形式，又怎么会缺少了最巅峰的表达。今天就和大家分享4首有关清明的宋词，去领悟哀思背后的生机和希望。\n\n没有什么可以阻挡春天的脚步：桃李依依春暗度\n\n《蝶恋花·遥夜亭皋闲信步》\n\n唐·李煜\n\n遥夜亭皋闲信步，乍过清明，早觉伤春暮。\n\n数点雨声风约住，朦胧淡月云来去。\n\n桃李依依春暗度，谁在秋千，笑里低低语。\n一片芳心千万绪，人间没个安排处。\n清明时节已经感觉到了暮春的气息，那漫长的夜久久不曾成眠，风止雨住，月色朦胧，他信步亭皋，忽闻秋千架上，笑语轻盈。桃李灼灼其华，春天仍在有序地推进，这心中的万种愁绪，千种相思，又能向何人诉说。\n这首词是南唐后主李煜所写，看似伤春怀人，其实是后主对故国的怀恋。清明时江南的春天应该更美，有杏花雨，有杨柳风，可惜的是他如今只能在汴梁城中无限怀念而已。\n每个人都会有不同的人生际遇，从而有了不同的身心感受。但时间从来都是最为公平的，就像那句经典的\u201c冬天来了，春天还会远吗\u201d，没有什么可以阻挡春天的脚步，因为时间是最公正的裁判。\n1000多个春天已然过去，多少成王败寇淹没在历史的烟尘中，但\u201c词中皇帝\u201d却成为不朽的传奇。","create_time":"2021-04-04T19:03:01.028289","status":1,"like_num":2,"star_num":1,"bb_num":5,"brow_num":102,"tag_type":2,"author":{"id":4,"username":"zhangkai","password":"123456789","gender":1,"avatar":"http://39.106.195.109/media/avatar/up_image/avatar2021-03-28_150537.475694.jpg","create_time":"2021-03-21T07:33:53.544075","email":"2993258983@qq.com","status":1,"nickname":"噜啦噜啦嘞","info":"打得开吗你喜欢谁呢","like_num":15,"star_num":15,"bb_num":41,"follow_num":4,"fans_num":3},"news_pics_set":["http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110236.346004.jpg","http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110248.114299.jpg","http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110258.873725.jpg"]},{"id":60,"title":"橙子🍊","content":"🍊🍊🍊🍊🍊🍊🍊🍊","create_time":"2021-04-04T18:43:23.873453","status":1,"like_num":1,"star_num":3,"bb_num":0,"brow_num":43,"tag_type":1,"author":{"id":12,"username":"cyx0706","password":"cyx0706","gender":0,"avatar":"http://39.106.195.109/media/avatar/default.jpeg","create_time":"2021-04-04T18:22:24.540675","email":"3519161997@qq.com","status":1,"nickname":"Ctwo","info":"这个人很懒，什么都没写","like_num":1,"star_num":0,"bb_num":1,"follow_num":2,"fans_num":3},"news_pics_set":["http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_104322.431017.jpg"]}]}
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
             * id : 63
             * title : 清明节
             * content : 清明，是二十四节气之一，也是中华民族重要的传统节日之一。又是一年清明，正因为万物生长、天清地明，才有了这个唯一既是节气名又是节日名的“清明”。

             清明，这是一个忧伤与欢乐的交织的时节。很多人会按照习俗去到郊外扫墓祭祖，祭奠那些不逝的哀思；很多人会在春光里去田间踏春，不管是清清的河畔沐浴春色，还是在在溪边宴饮，曲水流觞都是是欢乐的雅集。

             清明就是这样一个是哀思中蕴藏着生机与希望的诗意存在，独一无二充满着不尽的魅力。

             “佳节清明桃李笑，野田荒垄只生愁”是对故人的怀念；“素衣莫起风尘叹，犹及清明可到家”是游子思乡心切；“梨花风起正清明，游子寻春半出城”是欢快地畅游。这些都是关于清明飞扬在宋诗里最恰当的表达。

             而宋词作为那个时代最为瑰丽的文学形式，又怎么会缺少了最巅峰的表达。今天就和大家分享4首有关清明的宋词，去领悟哀思背后的生机和希望。

             没有什么可以阻挡春天的脚步：桃李依依春暗度

             《蝶恋花·遥夜亭皋闲信步》

             唐·李煜

             遥夜亭皋闲信步，乍过清明，早觉伤春暮。

             数点雨声风约住，朦胧淡月云来去。

             桃李依依春暗度，谁在秋千，笑里低低语。
             一片芳心千万绪，人间没个安排处。
             清明时节已经感觉到了暮春的气息，那漫长的夜久久不曾成眠，风止雨住，月色朦胧，他信步亭皋，忽闻秋千架上，笑语轻盈。桃李灼灼其华，春天仍在有序地推进，这心中的万种愁绪，千种相思，又能向何人诉说。
             这首词是南唐后主李煜所写，看似伤春怀人，其实是后主对故国的怀恋。清明时江南的春天应该更美，有杏花雨，有杨柳风，可惜的是他如今只能在汴梁城中无限怀念而已。
             每个人都会有不同的人生际遇，从而有了不同的身心感受。但时间从来都是最为公平的，就像那句经典的“冬天来了，春天还会远吗”，没有什么可以阻挡春天的脚步，因为时间是最公正的裁判。
             1000多个春天已然过去，多少成王败寇淹没在历史的烟尘中，但“词中皇帝”却成为不朽的传奇。
             * create_time : 2021-04-04T19:03:01.028289
             * status : 1
             * like_num : 2
             * star_num : 1
             * bb_num : 5
             * brow_num : 102
             * tag_type : 2
             * author : {"id":4,"username":"zhangkai","password":"123456789","gender":1,"avatar":"http://39.106.195.109/media/avatar/up_image/avatar2021-03-28_150537.475694.jpg","create_time":"2021-03-21T07:33:53.544075","email":"2993258983@qq.com","status":1,"nickname":"噜啦噜啦嘞","info":"打得开吗你喜欢谁呢","like_num":15,"star_num":15,"bb_num":41,"follow_num":4,"fans_num":3}
             * news_pics_set : ["http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110236.346004.jpg","http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110248.114299.jpg","http://39.106.195.109/media/newspics/up_image/newspics2021-04-04_110258.873725.jpg"]
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
                 * id : 4
                 * username : zhangkai
                 * password : 123456789
                 * gender : 1
                 * avatar : http://39.106.195.109/media/avatar/up_image/avatar2021-03-28_150537.475694.jpg
                 * create_time : 2021-03-21T07:33:53.544075
                 * email : 2993258983@qq.com
                 * status : 1
                 * nickname : 噜啦噜啦嘞
                 * info : 打得开吗你喜欢谁呢
                 * like_num : 15
                 * star_num : 15
                 * bb_num : 41
                 * follow_num : 4
                 * fans_num : 3
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
