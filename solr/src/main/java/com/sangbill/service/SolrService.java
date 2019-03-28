package com.sangbill.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.common.SolrDocumentList;

import com.sangbill.entity.Book;
import com.sangbill.util.SolrClient;
import com.sangbill.util.SolrConfig;

public class SolrService {
	static Logger logger = Logger.getLogger(SolrService.class);
	private static String BOOK_ID_PRE = "book_";	//solr中book id前缀
	/**
	 * 设置全文查询条件
	 * 匹配字段按list顺序，比如list=[col1,col2,col3,col4]
	 * key按空格分割后，得到keys=[val1,val2,...]，比较list和keys的长度，按小的去匹配。
	 * 
	 * @author liqiangbiao
	 * @param key
	 * @return
	 * 2016-6-14
	 */
	private static String setQParam(String key,List<String> list) {
		if(StringUtils.isBlank(key))
			return "*:*";
		String[] fls = key.split("\\s+");
		int min = Math.min(list.size(), fls.length);//查询条件空格分隔后，字符串个数与list的个数比较，取小值
		StringBuffer sb = new StringBuffer(list.get(0)+":"+fls[0]);
		for(int i = 1;i < min; i++){
			sb.append(" AND "+list.get(i)+":"+fls[i]);
		}
		return sb.toString();
	}
	/**
	 * 设置全文高亮字段，如果该字段分析后，没有分词，则该字段不能高亮
	 * 匹配字段按list顺序，比如list=[col1,col2,col3,col4]
	 * key按空格分割后，得到keys=[val1,val2,...]，比较list和keys的长度，按小的去匹配。
	 * 并且key经过分词后，没有分词结果，则对应字段不高亮，比如val1分词没结果，则col1不高亮
	 * @author liqiangbiao
	 * @param key
	 * @return
	 * 2016-6-14
	 */
	private static String setHlParam(String key,List<String> list) {
		if(StringUtils.isBlank(key))
			return "";
		String[] fls = key.split("\\s+");
		int min = Math.min(list.size(), fls.length);//查询条件空格分隔后，字符串个数与list的个数比较，取小值
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < min; i++){
			List<String> terms = getAnalysis(fls[i], (String)list.get(i));
			if(terms != null && terms.size() >0){	//该字段有分词				
				sb.append(list.get(i)+" ");
			}
		}
		return sb.toString();
	}
	/**
     * 给指定的语句分词。
     * 
     * @param sentence 被分词的语句
     * @return 分词结果
     */
	private static List<String> getAnalysis(String sentence,String field) {
        FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
        request.addFieldName(field);// 字段名，随便指定一个支持中文分词的字段
        request.setFieldValue("");// 字段值，可以为空字符串，但是需要显式指定此参数
        request.setQuery(sentence);
       
        FieldAnalysisResponse response = null;
        try {
            response = request.process(SolrClient.getInstance().getServer());
        } catch (Exception e) {
        	logger.error("获取查询语句的分词时遇到错误", e);
            return null;
        }

        List<String> results = new ArrayList<String>();
        Iterator<AnalysisPhase> it = response.getFieldNameAnalysis(field).getQueryPhases().iterator();
        while(it.hasNext()) {
          AnalysisPhase pharse = (AnalysisPhase)it.next();
          List<TokenInfo> list = pharse.getTokens();
          for (TokenInfo info : list) {
              results.add(info.getText());
          }
        }     
        return results;
    }
	/**
	 * 
	 * @param book：查询对象
	 * @param key：关键字
	 * @param hl：是否高亮
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static List queryBook(Book book, String key, boolean hl) {
		//设置过滤条件
		List<String> fields = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		fields.add("id");
		values.add(BOOK_ID_PRE+"*");
		//设置排序
		String[] sortfield = new String[]{};
		Boolean[] flag = new Boolean[]{};
		List<String> qList = SolrConfig.getListParam("solr_qorder_book");
		SolrDocumentList sdlist =  SolrClient.query(setQParam(key, qList),fields,values,sortfield, flag,hl,setHlParam(key, qList));
		List list = (List) SolrClient.getInstance().toBeanList(sdlist,Book.class);
		return list;
	}
}
