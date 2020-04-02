import java.util.ArrayList;
import java.util.List;

public class TestReferencing {
	List<String> names = new ArrayList<>();
	private boolean useOmid;

	public boolean isUseOmid() {
		return useOmid;
	}

	public void setUseOmid(boolean useOmid) {
		this.useOmid = useOmid;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
	
	

}
