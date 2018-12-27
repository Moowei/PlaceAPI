import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moowei.map.utils.HttpUrlAnalysis;
import com.moowei.map.utils.FileUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author moowei
 * date 2018/10/24 0024 15:07
 * @version V1.0
 */
public class HttpUrlAnalysisTest {
    @Test
    public void analysisHttpUrlTest() throws Exception {

    }

    @Test
    public void dateFormattedTest() throws ParseException {
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHH").parse("endDateStr"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = dateFormat.parse(endTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.HOUR, -1);
        Date startDate = calendar.getTime();
        String startTime = dateFormat.format(startDate);
        System.out.println(startTime);
        System.out.println(endTime);
    }
    @Test
    public void urlTest() throws IOException {
        String url = "http://api.map.baidu.com/place/v2/suggestion?query=中国人民解放军第二〇二医院&region=北京&city_limit=false&output=json&ak=PTKrw6tO6yi9EDfmWXzCiMVvvy0g6Ed8";
        System.out.println(HttpUrlAnalysis.analysisHttpUrl(url).toJSONString());
    }

    @Test
    public void bssUrlTest() throws IOException, InterruptedException {

        String ak1 = "I0w9IMeg931NnmOSWK6dP39tbN1m6EWg";
        String ak2 = "WaNYK7GihrlKtYqQbjkFA8O0WgyNcwgX";
        String ak3 = "tVZditiSwXHNFREpMGcOWmydVZ2UnBLy";
        String ak4 = "kcpv55EgonTlO7DDLpnGHaGAGfLcnGso";
        String ak5 = "Hs6EI3QMw0jMHdvzHMyfuvm6LEbYpRuw";
        String ak6 = "PTKrw6tO6yi9EDfmWXzCiMVvvy0g6Ed8";
        String ak = ak2;
        String fex = "0";
        String fileName = "D:\\USER\\MOOWEI\\Desktop\\bss\\hospital.txt";
        String outputName = "D:\\USER\\MOOWEI\\Desktop\\bss\\hospital_output_"+fex+".txt";
        File outputFileError = new File("D:\\USER\\MOOWEI\\Desktop\\bss\\hospital_error_"+fex+".txt");
        File outputFile = new File(outputName);
        File file = new File(fileName);
        BufferedReader reader = null;
        JSONObject jsonResultObj = null;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBufferError = new StringBuffer();
        String url = null;
        String getResult = null;
        String getMessage = null;
        int count = 0;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String hospitalName = null;
            // 一次读入一行，直到读入null为文件结束
            while ((hospitalName = reader.readLine()) != null) {
                url = "http://api.map.baidu.com/place/v2/suggestion?query="+hospitalName.trim()+"&region=北京&city_limit=false&output=json&ak="+ak;
                //调用Hybase数据接口获取JSON数据（获取第一页数据）
                Thread.sleep(900);
                jsonResultObj = HttpUrlAnalysis.analysisHttpUrl(url);
                getResult = jsonResultObj.get("status").toString();
                getMessage = jsonResultObj.get("message").toString();

                if ("ok".equals(getMessage)) {
                    JSONArray jsonArray = jsonResultObj.getJSONArray("result");
                    //清空Stringbuffer用于下次存储
                    if(jsonArray.size()>0){
                        JSONObject jsonObj = JSON.parseObject(jsonArray.get(0).toString());
//                    String name = jsonObj.getString("name");
                        String province = jsonObj.getString("province");
                        String city = jsonObj.getString("city");
                        String hospitAdress = hospitalName+"\t"+province+"\t"+city+"\n";
                        stringBuffer.append(hospitAdress);
                    }else{
                        System.out.println("无结果：result\"+getResult+\"：messgae：\"+getMessage+\"：：："+hospitalName);
                    }
                }else{
                    System.out.println("错误：result"+getResult+"：messgae："+getMessage+"："+hospitalName);
                    stringBufferError.append(hospitalName+"\n");
                }
            }
            //将本次数据写入文件
            FileUtils.bufferedWriteAndFileWriter(stringBufferError, outputFileError, "UTF-8");
            //将本次数据写入文件
            FileUtils.bufferedWriteAndFileWriter(stringBuffer, outputFile, "UTF-8");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println(HttpUrlAnalysis.analysisHttpUrl(url).toJSONString());
    }
}