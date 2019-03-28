package com.sangbill.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrClient {
	private static final Logger LOGGER = Logger.getLogger(SolrClient.class);
	private static String solrUrl;
	private static String solrOpen;
	private static HttpSolrServer solrServer = null;
	private volatile static SolrClient solrHelper = null;
	 /** 
     * SolrServerClient 是线程安全的 需要采用单例模式 
     * 此处实现方法适用于高频率调用查询 
     * 
     * @return SolrServerClient 
     */  
    public static SolrClient getInstance() {  
        if (null == solrHelper) {  
            synchronized (SolrClient.class) {  
                if (null == solrHelper) {
                	solrHelper = new SolrClient();  
                }  
            }  
        }  
        return solrHelper;  
    }  
  
  
    /** 
     * 初始化的HttpSolrServer 对象,并获取此唯一对象 
     * 配置按需调整 
     * @return 此server对象 
     */  
    public HttpSolrServer getServer() {
        if (null == solrServer) {
        	solrUrl = SolrConfig.getStrParam("solr_url");
        	solrOpen = SolrConfig.getStrParam("solr_open");
        	
        	solrServer = new HttpSolrServer(solrUrl);  
        	solrServer.setConnectionTimeout(3000);
        	solrServer.setDefaultMaxConnectionsPerHost(100);  
        	solrServer.setMaxTotalConnections(100);  
        }
        return solrServer;  
    }  
    
    public SolrClient() {
		getServer();
	}
    
    public void destory() {
		solrServer = null;
		System.runFinalization();
		System.gc();
	}

	/**
	 * 添加单个Bean到索引库
	 * 
	 * @param obj
	 */
	public void addBean(Object obj) {
		if (solrOpen.equalsIgnoreCase("Y")) {
			try {
				// 添加索引库
				solrServer.addBean(obj);
				solrServer.commit();// commit后才保存到索引库
			} catch (SolrServerException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 添加Bean集合到solr中
	 * 
	 * @param obj
	 */
	public <T> void addBeans(Collection<T> beans) {
		if (solrOpen.equalsIgnoreCase("Y")) {
			try {
				// 添加索引库
				solrServer.addBeans(beans);
				solrServer.commit();// commit后才保存到索引库
			} catch (SolrServerException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}


	/**
	 * <b>function:</b> 根据id删除索引操作
	 */
	public void remove(String id) {
		if (solrOpen.equalsIgnoreCase("Y")) {
			try {
				solrServer.deleteById(id);
				solrServer.commit();
			    LOGGER.info("删除索引库中的id:" + id + "索引成功");
			} catch (SolrServerException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public void remove(List<String> ids) {
		if (solrOpen.equalsIgnoreCase("Y")) {
			try {
				solrServer.deleteById(ids);
				solrServer.commit(true, true);
			} catch (SolrServerException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static  Object toBean( SolrDocument record , Class clazz){       
       Object o = null;
       try {
           o = clazz.newInstance();
       } catch (InstantiationException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       } catch (IllegalAccessException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       }
        Field[] fields =   clazz.getDeclaredFields();
        for(Field field:fields){
            Object value = record.get(field.getName());
            try {
               if(value instanceof Boolean){
            	   value = (value == Boolean.TRUE)?Byte.valueOf("1"):Byte.valueOf("0");
               }       
               BeanUtils.setProperty(o, field.getName(), value);
           } catch (IllegalAccessException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
        }
       return o;
   }
    
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public Object toBeanList(SolrDocumentList records, Class  clazz){
       List  list = new ArrayList();
       for(SolrDocument record : records){
           list.add(toBean(record,clazz));
       }
       return list;
   }
	
	public void removeAll(){
		try {
			solrServer.deleteByQuery("*:*");
			solrServer.commit(true, true);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <b>function:</b> 查询所有产品资源索引信息
	 * 
	 */
	public SolrDocumentList queryAllDocument() {
		SolrQuery params = new SolrQuery();
		// 查询关键词，*:*代表所有属性、所有值，即所有index
		params.set("q", "*:*");
		params.set("start", 0);
		params.set("rows", Integer.MAX_VALUE);
		SolrDocumentList solrList = null;
		try {
			QueryResponse response = solrServer.query(params);
			solrList = response.getResults();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return solrList;
	}

	/**
	 * 
	 * @param key:全文匹配字符串
	 * @param fields：精确匹配的字段
	 * @param values：精确匹配的字段值
	 * @param sortfield：排序字段
	 * @param flag：排序字段是正序还是逆序
	 * @param hl：是否高亮
	 * @param hlField：高亮的字符串
	 * @return 
	 */
	public static SolrDocumentList query(String key, List<String> fields, List<String> values,String[] sortfield, Boolean[] flag,Boolean hl, String hlField) {
		if (null != fields && null != values && fields.size() == values.size()) {
			SolrQuery query = new SolrQuery();
			try {
				// 添加从结果中过滤条件
				query.set("q", key);
				for (int i = 0; i < fields.size(); i++) {
					query.addFilterQuery(fields.get(i) + ":" + values.get(i));
				}
				query.setStart(0);
				// 设置排序
				if (null != sortfield && null != flag && sortfield.length == flag.length) {
					for (int i = 0; i < sortfield.length; i++) {
						if (flag[i]) {
							query.addSort(sortfield[i], SolrQuery.ORDER.asc);
						} else {
							query.addSort(sortfield[i], SolrQuery.ORDER.desc);
						}
					}
				}
				if (null != hl && hl) {
					query.setHighlight(true);// 开启高亮组件
					query.addHighlightField(hlField);// 高亮字段
					query.setHighlightSimplePre("<font color='red'>");// 标记，高亮关键字前缀
					query.setHighlightSimplePost("</font>");// 后缀
					query.setHighlightSnippets(1); // 获取高亮分片数，一般搜索词可能分布在文章中的不同位置，其所在一定长度的语句即为一个片段，默认为1，但根据业务需要有时候需要多取出几个分片。
													// - 此处设置决定下文中titleList,
													// contentList中元素的个数
					query.setHighlightFragsize(150);// 每个分片的最大长度，默认为100。适当设置此值，如果太小，高亮的标题可能会显不全；设置太大，摘要可能会太长。
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			QueryResponse rsp = null;
			try {
				long e = System.currentTimeMillis();
				rsp = solrServer.query(query);
				SolrDocumentList list = rsp.getResults();
				List<String> hlfList = Arrays.asList(hlField.split("\\s+"));
				if (null != hl && hl && hlfList != null && hlfList.size() > 0) { // 如果高亮，要重置高亮的字段
					Map<String, Map<String, List<String>>> highlightMap = rsp.getHighlighting();
					for (int i = 0; i < list.size(); i++) {
						SolrDocument doc = list.get(i);
						Map<String, List<String>> mapList = highlightMap.get(doc.getFieldValue("id"));
						for (String field : hlfList) {
							if (mapList.containsKey(field)) {
								doc.setField(field, mapList.get(field).get(0));
							}
						}
					}
				}
				System.out.println("执行耗时:" + (System.currentTimeMillis() - e));
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
