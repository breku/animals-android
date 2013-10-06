package com.brekol.loader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Breku
 * Date: 06.10.13
 */
public class RecordsHandler extends DefaultHandler {

    private List<Integer> recordList = new ArrayList<Integer>();
    StringBuilder builder;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        builder = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        builder.append(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("record")){
            recordList.add(Integer.valueOf(builder.toString().trim()));
        }
    }

    public List getRecordList() {
        return recordList;
    }
}
