/*
 * Copyright (c) 2016.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package android.urlminimizer.da4.org.urlminimizerandroidclient.vo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Darren Blaber on 11/12/16.
 */
@Root(name="minimizeResponse")
public class UrlMinimizerResponse implements Serializable {
    @Element(required = false)
    private String miniUrl;
    @Element(required = false)
    private String originalUrl;
    @Element(required = false)
    private String correctedUrl;
    @Element(required = false)
    private String error;
    @Element
    private int errorCode;
    @Element(required = false)
    private String apiKey;

    public String getCorrectedUrl() {
        return correctedUrl;
    }

    public void setCorrectedUrl(String correctedUrl) {
        this.correctedUrl = correctedUrl;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMiniUrl() {
        return miniUrl;
    }
    public void setMiniUrl(String miniUrl) {
        this.miniUrl = miniUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}

