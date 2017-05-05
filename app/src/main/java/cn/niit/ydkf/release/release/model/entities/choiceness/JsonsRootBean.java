package cn.niit.ydkf.release.release.model.entities.choiceness;



public class JsonsRootBean {
    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBody showapi_res_body;
    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }
    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }
    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_body(ShowapiResBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
    public ShowapiResBody getShowapi_res_body() {
        return showapi_res_body;
    }

}
