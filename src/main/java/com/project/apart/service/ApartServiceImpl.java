package com.project.apart.service;

import com.project.apart.dto.ApartDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.json.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApartServiceImpl implements ApartService {

    @Override
    public List<ApartDTO> getCode(String code, String selectedValue) throws Exception {


        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=AUt1PgERnQjip9T6WQbWEbfXPiTEgHJZ%2BYH1bx6BO4KyBvVUI0wkiitgyS5tnm72uyPwrgXKHCaE2mE%2FKZMtXw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*호출문서(xml, json) default : xml*/
        urlBuilder.append("&" + URLEncoder.encode("locatadd_nm", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")); /*지역주소명*/

        String result = getApi(urlBuilder);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(result);

        JSONArray jArr = (JSONArray) object.get("StanReginCd");

        JSONObject jObj = (JSONObject) jArr.get(1);
        JSONArray head = (JSONArray) jObj.get("row");
        JSONObject headObj = (JSONObject) head.get(0);
        String region = (String) headObj.get("region_cd");
        String trance = region.substring(0, 5);

        urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=AUt1PgERnQjip9T6WQbWEbfXPiTEgHJZ%2BYH1bx6BO4KyBvVUI0wkiitgyS5tnm72uyPwrgXKHCaE2mE%2FKZMtXw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(trance, "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(selectedValue, "UTF-8")); /*계약월*/

        String result2 = getApi(urlBuilder);

        org.json.JSONObject json = XML.toJSONObject(result2);

        String jsonResult = json.toString();

        System.out.println(jsonResult);

        JSONParser parser2 = new JSONParser();

        JSONObject jsonResult2 = (JSONObject) parser2.parse(jsonResult);

        JSONObject response = (JSONObject) jsonResult2.get("response");

        JSONObject body = (JSONObject) response.get("body");

        JSONObject items = (JSONObject) body.get("items");

        JSONArray item = (JSONArray) items.get("item");

        List<ApartDTO> results = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            JSONObject arr = (JSONObject) item.get(i);

            Long year = (Long) arr.get("년");
            Long month = (Long) arr.get("월");
            Long day = (Long) arr.get("일");

            Long regionCode = (Long) arr.get("지역코드");

            Object name = arr.get("아파트");
            String apartName = Objects.toString(name);
            Object zip = arr.get("지번");
            String road = (String) arr.get("도로명");
            Long constYear = (Long) arr.get("건축년도");
            Object dec = arr.get("전용면적");
            String apartConst = (String) arr.get("거래금액");
            String bjd = (String) arr.get("법정동");
            Object floor = arr.get("층");

            ApartDTO dto = ApartDTO.builder()
                    .day(day)
                    .floor(String.valueOf(floor))
                    .region_code(regionCode)
                    .year(year)
                    .month(month)
                    .post_zip(String.valueOf(zip))
                    .road_zip(road)
                    .const_year(constYear)
                    .dedicated_area(dec)
                    .apart_coast(apartConst)
                    .bjd(bjd)
                    .apart_name(apartName)
                    .id(i+1)
                    .selected(code)
                    .build();


            results.add(dto);


        }
        System.out.println("result list===="+results.size());
        return results;
    }
}
