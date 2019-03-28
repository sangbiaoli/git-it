package com.sangbill.test;

import java.util.List;

import org.apache.log4j.Logger;

import com.sangbill.entity.Book;
import com.sangbill.entity.Books;
import com.sangbill.service.SolrService;
import com.sangbill.util.JsonUtil;
import com.sangbill.util.ResourceUtils;
import com.sangbill.util.SolrClient;
import com.sangbill.util.SolrConfig;
import com.sangbill.util.XStreamUtil;

public class Test {
	public static void main(String[] args) {
//		addIndex();
//		delIndex();
		query();
	}

	@SuppressWarnings("unchecked")
	private static void query() {
		Book book = new Book();
		List<Book> books = SolrService.queryBook(book,"Java对象和XML",true);
		System.out.println(JsonUtil.toJson(books));
	}
	private static void delIndex() {
		String xmlString = ResourceUtils.readFile("books.xml");
        Books books = XStreamUtil.toBean(xmlString, Books.class);
        List<Book> list = books.getList();
        for (int i = 0; i < list.size(); i++) {			
        	SolrClient.getInstance().remove(list.get(i).getId());
		}
        System.out.println("delIndex:done!");
	}
	/**
	 * 添加索引
	 * @author liqiangbiao
	 * 2016年6月25日
	 * void
	 */
	private static void addIndex() {
		String xmlString = ResourceUtils.readFile("books.xml");
        Books books = XStreamUtil.toBean(xmlString, Books.class);
		SolrClient.getInstance().addBeans(books.getList());
		System.out.println("addIndex:done!");
	}
}
