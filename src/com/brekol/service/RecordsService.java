package com.brekol.service;

import com.brekol.loader.RecordsHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class RecordsService extends BaseService{

    public List<Integer> getSortedRecordsList(){
        List<Integer> result = null;
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            RecordsHandler recordsHandler = new RecordsHandler();
            InputStream inputStream = activity.getAssets().open("records/records.xml");
            saxParser.parse(inputStream, recordsHandler);
            result = recordsHandler.getRecordList();
            Collections.sort(result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
