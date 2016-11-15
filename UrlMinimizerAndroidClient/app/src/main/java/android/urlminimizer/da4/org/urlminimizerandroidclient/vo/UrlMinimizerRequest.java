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
@Root(name="minimizeRequest")
public class UrlMinimizerRequest implements Serializable {
        @Element
        private String apiKey;
        @Element
        private String url;
        private String[] metadata;
        @Element
        private String client;
        @Element
        private String apiVersion;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setMetadata(String[] metadata) {
            this.metadata = metadata;
        }
        public String getApiKey() {
            return apiKey;
        }
        public String getUrl() {
            return url;
        }

        public String[] getMetadata() {
            return metadata;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }

    public UrlMinimizerRequest(String apiKey, String url, String client, String apiVersion) {
        this.apiKey = apiKey;
        this.url = url;
        this.client = client;
        this.apiVersion = apiVersion;
    }
}

