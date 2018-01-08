package heyhou.com.baseproject.network.ex;

/**
 * Created by admin on 2017/2/22.
 * <p>
 * Http 网络请求的返回对象。
 */

public class HttpResponseData<T> {
    //网络请求的返回值
    private int ret;
    //网络请求的返回值描述
    private String msg;
    //网络请求返回的数据
    private T data;

    //结束字段， 1 表示结束了； 0 表示未结束
    private int end;

    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
