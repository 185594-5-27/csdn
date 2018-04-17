package com.mongo.sys.entity;

import net.sf.json.JSONObject;
import org.bson.types.ObjectId;

import java.util.List;

/**
 *@author linzf
 **/
public class Tree implements Comparable<Tree> {

	public Tree(){
		super();
	}

	public Tree(String id){
		this.id = new ObjectId(id);
	}



	private ObjectId id;
	private String code;
	private String icon;
	private String name;
	private ObjectId parentId;
	private long treeOrder;
	private String url;
	private String state;
	private boolean checked;
	private List<Tree> child;
	private Tree tree;

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public String getId() {
		if(id!=null){
			return id.toString();
		}else{
			return "";
		}
	}

	public void setId(String id) {
		this.id = new ObjectId(id);;
	}

	public String getParentId() {
		if(parentId!=null){
			return parentId.toString();
		}else{
			return "";
		}
	}

	public void setParentId(String parentId) {
		this.parentId = new ObjectId(parentId);;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Tree> getChild() {
		return child;
	}

	public void setChild(List<Tree> child) {
		this.child = child;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTreeOrder() {
		return treeOrder;
	}

	public void setTreeOrder(long treeOrder) {
		this.treeOrder = treeOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 功能描述：实现集合根据treeOrder字段进行排序的功能
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Tree o) {
		long i = this.getTreeOrder() - o.getTreeOrder();
		return Integer.parseInt(i+"");
	}
}
