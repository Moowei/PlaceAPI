package com.moowei.map.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moowei.map.utils.FileUtils;
import com.moowei.map.utils.HttpUrlAnalysis;
import com.moowei.map.utils.JavaSwing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Package com.moowei.map.services
 * Description: TODO
 *
 * @author moowei
 * date 2018/12/24 0024 10:47
 * @version V1.0
 */
public class PlaceSearchForBaiduMap {

    private static String akStr = "PTKrw6tO6yi9EDfmWXzCiMVvvy0g6Ed8";

    public static void main(String[] args){
        PlaceSearchForBaiduMap placeSearchForBaiduMap = new PlaceSearchForBaiduMap();
        placeSearchForBaiduMap.getProvenceAndCity();
    }

    /**
     * JAVA图形化界面
     */
     public void getProvenceAndCity(){
        final PlaceSearchForBaiduMap placeSearchForBaiduMap = new PlaceSearchForBaiduMap();
        final JFrame jf = new JFrame("解析成省份和地市");
        jf.setSize(850, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(25, 30);
        msgTextArea.setBounds(28, 209, 790, 265);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);

        JButton saveBtn = new JButton("关闭");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(saveBtn);

        JButton openAndParseBtn = new JButton("解析");
        openAndParseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File inputFile = JavaSwing.showFileOpenDialog(jf, msgTextArea);
                msgTextArea.append("输入文件: " + inputFile.getAbsolutePath() + "\n");
                try {
                    placeSearchForBaiduMap.getAdress(inputFile,msgTextArea);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(openAndParseBtn);
        jf.setContentPane(panel);
        jf.setVisible(true);
    }


    /**
     * 读取文件中的查询关键词，并调用接口进行查询
     * @param inputFile
     */
    public void getAdress(File inputFile,JTextArea msgTextArea) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer errorStringBuffer = new StringBuffer();
        File errorOutputFile = null;
        File outputFile = null;
        BufferedReader reader = null;
        String queryStr = null;
        String addressStr = null;
        String uidStr = null;
        int count = 0;
        try {
            errorOutputFile = createErrorFile(inputFile);
            outputFile = createOutputFile(inputFile);
            reader = new BufferedReader(new FileReader(inputFile));
            while ((queryStr = reader.readLine()) != null) {
                count++;
                Thread.sleep(900);
                uidStr = analysisJson(queryStr,akStr,errorStringBuffer,false);
                if(!"".equals(uidStr) && uidStr != null){
                    addressStr = analysisJson(uidStr,akStr,errorStringBuffer,true);
                    if(addressStr != null){
                        stringBuffer.append(queryStr + "\t" + addressStr);
                        FileUtils.bufferedWriteAndFileWriter(stringBuffer, outputFile, "UTF-8");
                        stringBuffer.setLength(0);
                        FileUtils.bufferedWriteAndFileWriter(errorStringBuffer, errorOutputFile, "UTF-8");
                        errorStringBuffer.setLength(0);
                    }else {
                        System.out.println("获取详细数据失败！！");
                    }
                }else{
                    System.out.println("初步解析失败 OR 查询无结果");
                }
            }
            msgTextArea.append("解析完成！！！点击关闭结束任务: \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 调用接口获取解析结果数据，拼接成String
     * @param queryStr 查询的关键字，当为详细查询时使用uid,否则使用地标名
     * @param isDetail 是否查询详细
     * @return
     * @throws IOException
     */
    public String analysisJson(String queryStr,String akStr, StringBuffer resultStringBuffer,boolean isDetail) throws IOException {
        String name = null;
        String uid = null;
        String province = null;
        String city = null;
        String address = null;
        String area = null;
        String addressDetail = null;
        String getResultStr = null;
        String getMessageStr = null;
        JSONObject jsonResultObj = null;
        jsonResultObj = HttpUrlAnalysis.analysisHttpUrl(getUrl(queryStr,akStr,isDetail));
        System.out.println("调用："+getUrl(queryStr,akStr,isDetail));
        getResultStr = jsonResultObj.get("status").toString();
        getMessageStr = jsonResultObj.get("message").toString();
        if ("ok".equals(getMessageStr) && "0".equals(getResultStr)) {
            //当不是详细数据时，返回的是多个JSON对象
            if(!isDetail){
                JSONArray jsonArray = jsonResultObj.getJSONArray("result");
                if (jsonArray.size() > 0) {//查询的内容存在
                    JSONObject jsonObj = JSON.parseObject(jsonArray.get(0).toString());
                    name = jsonObj.getString("name");
                    uid = jsonObj.getString("uid");
                    province = jsonObj.getString("province");
                    city = jsonObj.getString("city");
                    addressDetail = uid + "\t" + province + "\t" + city + "\n";
                    return uid;
                }else {//查询的内容为空
                    resultStringBuffer.append("无查询结果：isDetail="+isDetail +"；关键字="+queryStr + "\n");
                    return "";
                }
            }else{ //当需要详细数据是返回的是一个JSON对象
                JSONObject jsonObj = jsonResultObj.getJSONObject("result");
                name = jsonObj.getString("name");
                uid = jsonObj.getString("uid");
                province = jsonObj.getString("province");
                city = jsonObj.getString("city");
                area = jsonObj.getString("area");
                address = jsonObj.getString("address");
                addressDetail = uid + "\t" + province + "\t" + city + "\t" + area + "\t" + address +"\n";
                return addressDetail;
            }
        } else {//接口调用报错
            resultStringBuffer.append("接口调用错误：getResultStr="+getResultStr+"；getMessageStr="+getMessageStr+"；isDetail="+isDetail +"；关键字="+queryStr + "\n");
            return null;
        }
    }

    /**
     * 在输入文件的统同级目录下创建*_output.txt
     * @param fileName 输入文件
     * @return 调用结果
     * @throws IOException IOException
     */
    public File createOutputFile(File fileName) throws IOException {
        File outputFile = new File(fileName.getAbsolutePath().replaceAll(".txt", "_output.txt"));
        if(outputFile.exists()){
            outputFile.delete();
            outputFile.createNewFile();
        }
        return outputFile;
    }

    /**
     * 创建错误文件
     * @param fileName 输入文件
     * @return 调用结果
     * @throws IOException IOException
     */
    public static File createErrorFile(File fileName) throws IOException {
        File errorOutputFile = new File(fileName.getAbsolutePath().replaceAll(".txt", "_error.txt"));
        if(errorOutputFile.exists()){
            errorOutputFile.delete();
            errorOutputFile.createNewFile();
        }
        return errorOutputFile;
    }




    /**
     * 拼接接口请求地址
     * @param queryStr 要查询的关键词
     * @param akStr 百度地图接口提供的ak
     * @param isDetail 是否获取详细数据
     * @return
     */
    public static String getUrl(String queryStr, String akStr, boolean isDetail){
        String urlStr = null;
       if(!isDetail){
           urlStr = "http://api.map.baidu.com/place/v2/suggestion?query=" + queryStr + "&region=北京&city_limit=false&output=json&ak=" + akStr;
       }else {
           urlStr = "http://api.map.baidu.com/place/v2/detail?uid=" + queryStr + "&output=json&scope=2&ak=" + akStr;
       }
       return urlStr;
    }

}

