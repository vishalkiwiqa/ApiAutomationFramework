package com.MBS.Pojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Output {

	@SerializedName("MatchOutput")
	@Expose
	private List<MatchOutput> matchOutput = null;

	public List<MatchOutput> getMatchOutput() {
		return matchOutput;
	}

	public void setMatchOutput(List<MatchOutput> matchOutput) {
		this.matchOutput = matchOutput;
	}

}
