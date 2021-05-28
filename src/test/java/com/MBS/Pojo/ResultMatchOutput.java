package com.MBS.Pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ResultMatchOutput {

	@SerializedName("ResultText")
	@Expose
	private String resultText;
	@SerializedName("Output")
	@Expose
	private Output output;

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

}