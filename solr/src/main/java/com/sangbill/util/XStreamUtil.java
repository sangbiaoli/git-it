package com.sangbill.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamUtil {
	/**
	 * obj to xml
	 * @param obj
	 * @return
	 */
    public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }
    /**
     * xml to obj
     * @param xmlStr
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
    
}