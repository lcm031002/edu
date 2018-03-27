/**  
 * @Title: HttpReponse.java
 * @Package com.edu.cas.client.common.model
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 上午11:52:58
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.model;

import java.io.Serializable;

/**
 * @ClassName: HttpReponse
 * @Description: http rest响应对象模型
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 上午11:52:58
 *
 */
public class HttpReponse implements Serializable {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 6693008511025393433L;

    private String message;

    private boolean error;

    private Object data;

    private boolean isChangeTeam = false; // 切换团队标识

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final boolean isError() {
        return error;
    }

    public final void setError(boolean error) {
        this.error = error;
    }

    public final Object getData() {
        return data;
    }

    public final void setData(Object data) {
        this.data = data;
    }

    public boolean isChangeTeam() {
        return isChangeTeam;
    }

    public void setChangeTeam(boolean isChangeTeam) {
        this.isChangeTeam = isChangeTeam;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + (error ? 1231 : 1237);
        result = prime * result + ((message == null) ? 0 : message.hashCode());
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
        HttpReponse other = (HttpReponse) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (error != other.error)
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HttpReponse [message=" + message + ", error=" + error + ", data=" + data + "]";
    }

}
