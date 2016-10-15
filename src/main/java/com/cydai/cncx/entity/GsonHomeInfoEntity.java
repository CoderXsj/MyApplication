package com.cydai.cncx.entity;

import java.util.List;

public class GsonHomeInfoEntity {


    /**
     * status : success
     * success : true
     * indexData : {"top":{"online_time":"3850453","receive_count":"3","finish_rate":"67%"},"ads":[{"url":"imgurl","redirect":"http://www.baidu.com"},{"url":"imgurl","redirect":"http://www.cydai.com"}],"articles":[{"id":"1","title":"约车合法化","content":"国家出台政策网约车合法化","created":"0","status":"0"}]}
     */

    private String status;
    private boolean success;
    /**
     * top : {"online_time":"3850453","receive_count":"3","finish_rate":"67%"}
     * ads : [{"url":"imgurl","redirect":"http://www.baidu.com"},{"url":"imgurl","redirect":"http://www.cydai.com"}]
     * articles : [{"id":"1","title":"约车合法化","content":"国家出台政策网约车合法化","created":"0","status":"0"}]
     */

    private IndexDataBean indexData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public IndexDataBean getIndexData() {
        return indexData;
    }

    public void setIndexData(IndexDataBean indexData) {
        this.indexData = indexData;
    }

    public static class IndexDataBean {
        /**
         * online_time : 3850453
         * receive_count : 3
         * finish_rate : 67%
         */

        private TopBean top;
        /**
         * url : imgurl
         * redirect : http://www.baidu.com
         */

        private List<AdsBean> ads;
        /**
         * id : 1
         * title : 约车合法化
         * content : 国家出台政策网约车合法化
         * created : 0
         * status : 0
         */

        private List<ArticlesBean> articles;

        public TopBean getTop() {
            return top;
        }

        public void setTop(TopBean top) {
            this.top = top;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlesBean> articles) {
            this.articles = articles;
        }

        public static class TopBean {
            private String online_time;
            private String receive_count;
            private String finish_rate;

            public String getOnline_time() {
                return online_time;
            }

            public void setOnline_time(String online_time) {
                this.online_time = online_time;
            }

            public String getReceive_count() {
                return receive_count;
            }

            public void setReceive_count(String receive_count) {
                this.receive_count = receive_count;
            }

            public String getFinish_rate() {
                return finish_rate;
            }

            public void setFinish_rate(String finish_rate) {
                this.finish_rate = finish_rate;
            }
        }

        public static class AdsBean {
            private String url;
            private String redirect;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getRedirect() {
                return redirect;
            }

            public void setRedirect(String redirect) {
                this.redirect = redirect;
            }
        }

        public static class ArticlesBean {
            private String id;
            private String title;
            private String content;
            private String created;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}