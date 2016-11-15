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

package android.urlminimizer.da4.org.urlminimizerandroidclient;

import android.urlminimizer.da4.org.urlminimizerandroidclient.vo.UrlMinimizerRequest;
import android.urlminimizer.da4.org.urlminimizerandroidclient.vo.UrlMinimizerResponse;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * MinimizeUrlService class is responsible for handling communication to the minimize XML Service. TODO: Make service interface
 */
public class MinimizeUrlService {
    private String apiKey;
    private String endpointUrl = null;
    private String version;
    private String client = "OfficialAndroidClient";


    public MinimizeUrlService(String apiKey, String endpointUrl, String client, String version) {
        this.apiKey = apiKey;
        this.endpointUrl = endpointUrl;
        this.client = client;
        this.version = version;
    }

    /**
     * Minimize a url provided and return the minimized alias
     * @param url Url to be minimized
     * @return Full short url and alias (http://ne8.org/123)
     * @throws URLServiceException Exception returned from service
     */
    String minimizeUrl(String url) throws URLServiceException  {
        ByteArrayOutputStream xmlOutStream = new ByteArrayOutputStream();

        try {
            URL endpointUrlConnection = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) endpointUrlConnection.openConnection();
            UrlMinimizerRequest request = new UrlMinimizerRequest(apiKey,url,client,version);
            Serializer serializer = new Persister();

            serializer.write(request,xmlOutStream);
            String xml = xmlOutStream.toString("UTF-8");
            Log.d(MinimizeUrlService.class.getName(),xml);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("content-type", "application/xml");
            IOUtils.write(xml, connection.getOutputStream(),"UTF-8");
            String strResponse =  IOUtils.toString(connection.getInputStream(), "UTF-8");
            Log.d(MinimizeUrlService.class.getName(),strResponse);
            UrlMinimizerResponse urlResponse= serializer.read(UrlMinimizerResponse.class, strResponse);

            if(urlResponse.getError() != null && !urlResponse.getError().isEmpty())
                throw new URLServiceException("Error(" + urlResponse.getErrorCode()+"): "+ urlResponse.getError());
            if(urlResponse != null) {
                Log.d(MinimizeUrlService.class.getName(),urlResponse.getMiniUrl());
                return urlResponse.getMiniUrl();
            }else
                throw new URLServiceException("Could not get URL! Please try again later");


        }catch (URLServiceException e)
        {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new URLServiceException("Unknown Error. Please try again later!");
        } finally{
            try {
                xmlOutStream.close();
            } catch(IOException e)
            {

            }
        }
    }


}
