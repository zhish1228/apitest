package com.tongdao.cases;

import com.alibaba.fastjson.JSON;
import com.tongdao.bean.HttpParam;
import com.tongdao.utils.DataProviderUtil;
import com.tongdao.utils.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

/**
 * Created by zhengda on 2017/9/11.
 */
public class ResourceTest extends BaseTest{

    private HttpUtil httpUtil = new HttpUtil();

    @Test(dataProvider = "httpParamsDataProvider", dataProviderClass = DataProviderUtil.class)
    public void getFunctionsTest(HttpParam httpParam) throws Exception{

        String respBody = httpUtil.doGet(httpParam.getPath(),httpParam.getRequestParams());
        Map mapType = JSON.parseObject(respBody,Map.class);
        int code = (Integer)mapType.get("code");
        Assert.assertEquals(code , 0);
    }

}
