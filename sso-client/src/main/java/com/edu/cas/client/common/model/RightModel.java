package com.edu.cas.client.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @ClassName: RightModel
 * @Description: 用户权限树与用户可见菜单对应
 *
 */
public class RightModel implements Serializable, Cloneable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8538965277326268034L;

	private String index;
	private String name;
	private String key;
	private String icon;
	private String type;
	private String active;
	private String value;
	private String href;
	private Set<String> servicesSet = new HashSet<String>();
	private String path;
	private String showMenu;
	private List<RightModel> menus = new ArrayList<RightModel>();

	private String version;

	public final String getActive() {
		return active;
	}

	public final String getHref() {
		return href;
	}

	public final String getIcon() {
		return icon;
	}

	public final String getIndex() {
		return index;
	}

	public final String getVersion() {
		return version;
	}

	public final void setVersion(String version) {
		this.version = version;
	}

	public final String getKey() {
		return key;
	}

	public final String getName() {
		return name;
	}

	public final String getPath() {
		return path;
	}

	public final String getShowMenu() {
		return showMenu;
	}

	public final String getType() {
		return type;
	}

	public final String getValue() {
		return value;
	}

	public final void setActive(String active) {
		this.active = active;
	}

	public final void setHref(String href) {
		this.href = href;
	}

	public final void setIcon(String icon) {
		this.icon = icon;
	}

	public final void setIndex(String index) {
		this.index = index;
	}

	public final void setKey(String key) {
		this.key = key;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setPath(String path) {
		this.path = path;
	}

	public final void setShowMenu(String showMenu) {
		this.showMenu = showMenu;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final List<RightModel> getMenus() {
		return menus;
	}

	public final void setMenus(List<RightModel> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		return "RightModel [index=" + index + ", name=" + name + ", key=" + key
				+ ", icon=" + icon + ", type=" + type + ", active=" + active
				+ ", value=" + value + ", href=" + href + ", servicesSet="
				+ servicesSet + ", path=" + path + ", showMenu=" + showMenu
				+ ", menus=" + menus + ", version=" + version + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((menus == null) ? 0 : menus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result
				+ ((servicesSet == null) ? 0 : servicesSet.hashCode());
		result = prime * result
				+ ((showMenu == null) ? 0 : showMenu.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RightModel other = (RightModel) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (menus == null) {
			if (other.menus != null)
				return false;
		} else if (!menus.equals(other.menus))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (servicesSet == null) {
			if (other.servicesSet != null)
				return false;
		} else if (!servicesSet.equals(other.servicesSet))
			return false;
		if (showMenu == null) {
			if (other.showMenu != null)
				return false;
		} else if (!showMenu.equals(other.showMenu))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	public final Set<String> getServicesSet() {
		return servicesSet;
	}

	public final void setServicesSet(Set<String> servicesSet) {
		this.servicesSet = servicesSet;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Cloning not allowed.");
			return this;
		}
	}
}
