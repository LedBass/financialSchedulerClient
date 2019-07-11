package com.marcio.financialSchedulerClient.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author marcio
 */
public class BadRequest extends JavaScriptObject {
    protected BadRequest() {}

    public native final String getMessage()/*-{return this.message}-*/;
}
