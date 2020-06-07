package com.jq.cartoon1.screen.javaBean;

import java.util.ArrayList;
import java.util.List;

public class SearchAllEntity {
	private String searchName="";//查询名
	private String next="";//下章链接
	private int lastNumber=0;
	private int all=0;//总共查到多少
	private int nowNumber=0;
	private List<BasicEntity> listSearch=new ArrayList<BasicEntity>();
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	
	public int getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(int lastNumber) {
		this.lastNumber = lastNumber;
	}
	public int getAll() {
		return all;
	}
	public void setAll(int all) {
		this.all = all;
	}
	public int getNowNumber() {
		return nowNumber;
	}
	public void setNowNumber(int nowNumber) {
		this.nowNumber = nowNumber;
	}
	public List<BasicEntity> getListSearch() {
		return listSearch;
	}
	public void setListSearch(List<BasicEntity> listSearch) {
		this.listSearch = listSearch;
	}
	
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("查询关键字："+searchName);
		buffer.append("\n下章链接："+next);
		buffer.append("\n当前页："+nowNumber);
		buffer.append("\n最后页："+lastNumber);
		buffer.append("\n总共查到："+all+" 章");
		buffer.append("\n本页的漫画：\n"+listSearch.toString());
		return buffer.toString();
	}
	
}
