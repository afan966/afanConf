package com.afan.conf.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afan.conf.config.ConfigResponse;
import com.afan.conf.websocket.WebSocketServer;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {

    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}/{instance}")
    public ConfigResponse pushToWeb(@PathVariable String appId,@PathVariable String instance,String message) {
    	ConfigResponse response = new ConfigResponse();
    	response.setMessage(message);
        try {
            WebSocketServer.sendInfo(message,appId, instance);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(1);
            response.setMessage(appId+"#"+e.getMessage());
        }  
        response.setMessage(appId);
        return response;
        
    } 
} 