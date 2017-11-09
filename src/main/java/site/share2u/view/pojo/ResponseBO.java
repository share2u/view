package site.share2u.view.pojo;

/**
 * Created by xiajl on 2017/7/13.
 */
public class ResponseBO {
	 public static final int ERROR = 0;
	    public static final int SUCCESS = 1;

	    private int completeCode = SUCCESS;

	    private String reasonCode = "200";

	    private String reasonMessage = "";

	    private Object Data = null;

	    public int getCompleteCode () {
	        return completeCode;
	    }

	    public void setCompleteCode (int completeCode) {
	        this.completeCode = completeCode;
	    }

	    public String getReasonCode () {
	        return reasonCode;
	    }

	    public void setReasonCode (String reasonCode) {
	        this.reasonCode = reasonCode;
	    }

	    public String getReasonMessage () {
	        return reasonMessage;
	    }

	    public void setReasonMessage (String reasonMessage) {
	        this.reasonMessage = reasonMessage;
	    }


	    public Object getData () {
	        return Data;
	    }

	    public void setData (Object data) {
	        Data = data;
	    }

		@Override
		public String toString() {
			return "ResponseBO [completeCode=" + completeCode + ", reasonCode=" + reasonCode + ", reasonMessage="
					+ reasonMessage + ", Data=" + Data + "]";
		}
	    
}
