package com.github.binarywang.demo.wechat.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weixindev.micro.serv.common.bean.EchartSeries;

@RestController
public class TestController {

	@RequestMapping("gettestdata")
    public Map<String,Object> getTestData(HttpServletResponse response) {
        List<String> legend = new ArrayList<String>(Arrays.asList(new String[] { "图文阅读人数","图文分享人数","其他"}));
        List<String> axis = new ArrayList<String>(
                Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6" }));
        List<EchartSeries> series = new ArrayList<EchartSeries>();
       /* series.add(new EchartSeries("图文阅读人数", "line","图文阅读人数", new ArrayList<BigDecimal>(Arrays.asList(5, 20, 40, 10, 10, 20))));
        series.add(new EchartSeries("图文分享人数", "line","图文分享人数", new ArrayList<BigDecimal>(Arrays.asList(200, 30, 40, 10, 10, 20))));
        series.add(new EchartSeries("其他", "line","其他", new ArrayList<BigDecimal>(Arrays.asList(50, 40, 40, 10, 10, 20))));*/
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("legend", legend);
        map.put("axis", axis);
        map.put("series", series);
        return map;
    }
	
}
