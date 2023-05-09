 

import cartago.*;
import java.util.*;
import ora4mas.nopl.JasonTermWrapper;

public class Recovery extends Artifact {
	 
	
	void init() {	 
	}

    @OPERATION
    void recovery (String agent, String device, OpFeedbackParam<Boolean> success)
    {
        //do diagnosis based on fault KG

        // do recovery

        // return result
        success.set(false);
      
    }
}
