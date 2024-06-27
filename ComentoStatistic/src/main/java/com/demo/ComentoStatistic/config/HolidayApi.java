    package com.demo.ComentoStatistic.config;

    import org.springframework.stereotype.Component;
    import lombok.extern.slf4j.Slf4j;
    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import org.w3c.dom.*;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.util.ArrayList;
    import java.util.List;

    @Component
    @Slf4j
    public class HolidayApi {
        private static final String SERVICE_KEY = "I560qeEm8UUIiAJOHv4MW4OBmB9I1%2BZxMcSaPI63EaurDVJhv4TfW%2BpWqrlXVxRFCbj0jrkTaGdAtA8r5fZvRw%3D%3D";

        public List<String> getHolidays(int year) throws IOException {
            List<String> holidays = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getAnniversaryInfo");
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY);
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=1");
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=100");
                urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(year), "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(String.format("%02d", month), "UTF-8"));
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/xml");

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                conn.disconnect();

                String response = sb.toString();
                log.info("API 응답: " + response);

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(new java.io.ByteArrayInputStream(response.getBytes("UTF-8")));

                    NodeList itemList = document.getElementsByTagName("item");

                    for (int i = 0; i < itemList.getLength(); i++) {
                        Node item = itemList.item(i);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            Element itemElement = (Element) item;
                            String locdate = itemElement.getElementsByTagName("locdate").item(0).getTextContent();
                            holidays.add(locdate);
                        }
                    }

                    log.info(holidays.toString());
                } catch (Exception e) {
                    log.error("XML 파싱 중 오류 발생", e);
                }
            }

            log.info("Holidays"+holidays.toString());

            return holidays;
        }
    }
