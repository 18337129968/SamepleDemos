package com.example.administrator.samepledemos.bean.request;

import java.util.List;

/**
 * Created by xie on 2018/3/29.
 */

public class TestBean {

    public String code;
    public String msg;
    public PagerBean pager;
    public String success;
    public long time;

    public static class PagerBean {
        public List<ContentBeanX> content;

        public static class ContentBeanX {
            /**
             * aclcodeFlag : false
             * content : [{"title":"核验结果","type":0,"value":"无抵押通过"},{"title":"核验编号","type":0,"value":"2102654"},{"title":"密码","type":0,"value":"72591890"},{"title":"北京市住房与城乡建设委员会网址","type":1,"value":"http://www.bjjs.gov.cn"}]
             * state : 已完成
             * stateInt : 3
             * title : 房源核验审核
             */

            public boolean aclcodeFlag;
            public String state;
            public int stateInt;
            public String title;
            public List<ContentBean> content;

            public static class ContentBean {
                /**
                 * title : 核验结果
                 * type : 0
                 * value : 无抵押通过
                 */

                public String title;
                public int type;
                public String value;
            }
        }
    }
}
