package com.whw.util;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;

import com.alibaba.dubbo.common.utils.CollectionUtils;

public class Main{
  private static final String ROOT_PATH = "/dubbo";
  
  private static final String COMMON_PATH = "com.htt.app.";
  
  private static final Integer TIME_OUT = 5000;

  public static void main(String[] args){
	  String url = args[0];
	  String key = args[1];
	  ZkClient zkClient = null;
	  try{
		  zkClient = new ZkClient(url,TIME_OUT);
	  }catch(ZkException e){
		  System.out.println(e.getMessage());
	  }
	  try{
		  if(zkClient == null){
			  System.out.println("连接失败");
		  }else{
			  System.out.println("连接成功。。。。");
			  List<String> nodes = zkClient.getChildren(ROOT_PATH);
			  if(CollectionUtils.isEmpty(nodes)){
				  System.out.println("节点为空");
			  }else{
				  for(String node : nodes){
					  if(node.indexOf(key) == 0){
						  boolean isDelete = zkClient.deleteRecursive(ROOT_PATH+"/"+node);
						  if(isDelete){
							  System.out.println("删除节点 " + node + " 成功");
						  }
					  }
				  }
			  }
		  }
	  }finally{
		  if(zkClient != null){
			  zkClient.close();
		  }
	  }
	  
  }
  
}