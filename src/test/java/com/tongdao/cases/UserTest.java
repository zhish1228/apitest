package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.bean.HttpParam;
import com.tongdao.conf.Config;
import com.tongdao.utils.Common;
import com.tongdao.utils.DataProviderUtil;
import com.tongdao.utils.HttpUtil;
import com.tongdao.utils.UserInfoUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;


public class UserTest extends BaseTest {


    /**
     * 新建用户操作在beforeSuit中操作
     */

//    private String userName = UserInfoUtil.getUserName();
//    private String realName = UserInfoUtil.getRealName();
//    private String mobile = UserInfoUtil.getMobile();
//    private String email = UserInfoUtil.getEmailAddress();
//    private int userId;
//    @BeforeClass()
//    public void createUserTest() throws Exception{
//
//        Common.log("ssssssssss");
//        String reqBody = "{\"username\":\"$username\",\"realName\":\"$realname\",\"email\":\"$email\",\"mobile\":\"$mobile\"}";
//
//        reqBody = reqBody.replace("$username", userName).
//                replace("$realname", realName).
//                replace("$mobile", mobile).
//                replace("$email", email);
//
//        String respBody = httpUtil.doPost("/api/v1/auth", "", reqBody);
//        System.out.println(respBody);
//        Map mapType = JSON.parseObject(respBody,Map.class);
//        int code = (Integer)mapType.get("code");
//        Assert.assertEquals(code , 0);
//
//        // 设置新建用户的userId
//        Map mapType2 = (Map)mapType.get("data");
//        userId = (Integer) mapType2.get("userId");
//    }

    private HttpUtil httpUtil = new HttpUtil();

    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void getCurrentUserTest(HttpParam httpParam) throws Exception {

    String respBody = httpUtil.doGet(httpParam.getPath(),"");
    Map mapType = JSON.parseObject(respBody,Map.class);
    int code = (Integer)mapType.get("code");
    Assert.assertEquals(code , 0);

    }

    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void getUserListTest(HttpParam httpParam) throws Exception{

    String respBody = httpUtil.doGet(httpParam.getPath(),"");
    Map mapType = JSON.parseObject(respBody,Map.class);
    int code = (Integer)mapType.get("code");
    Assert.assertEquals(code , 0);

    }


    /**
     *  依赖@BeforeClass
     * @param httpParam
     * @throws Exception
     */
    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void modifyUserTest(HttpParam httpParam) throws Exception{

        String email = UserInfoUtil.getEmailAddress();
        String mobile = UserInfoUtil.getMobile();

        String reqBody = httpParam.getRequestBody().replace("$email", email).
                replace("$mobile", mobile).replace("$userId", String.valueOf(Config.userId));
        Common.log(reqBody);
        String respBody = httpUtil.doPost(httpParam.getPath(), httpParam.getRequestParams(), reqBody);

        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);

    }

    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void enableUserTest(HttpParam httpParam){





    }



}
