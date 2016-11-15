/*
 * Copyright (c) 2016 Darren Blaber.
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

package org.da4.urlminimizer.android.client;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.EXTRA_TEXT;

/**
 * Main and only activity for URL Minimizer. Simple activity for minimizing a url
 */
public class MainUrlMinimizerActivity extends AppCompatActivity {
    MinimizeUrlService urlService = null;


    public MainUrlMinimizerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_url_minimizer);
        urlService = new MinimizeUrlService(getString(R.string.api_key),getString(R.string.endpoint_url),getString(R.string.client),getString(R.string.api_version));
        Button btn = (Button) findViewById(R.id.minimize_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ((TextView) findViewById(R.id.url_text)).getText().toString();
                new HandleUrlCreationThread().execute(url);
            }
        });

        if (Intent.ACTION_SEND.equals(getIntent().getAction()) && getIntent().getType() != null) {
            if ("text/plain".equals(getIntent().getType())) {
                String sharedText = getIntent().getStringExtra(EXTRA_TEXT);
                if (sharedText != null) {
                    ((TextView) findViewById(R.id.url_text)).setText(sharedText);
                    new HandleUrlCreationThread().execute(sharedText);
                }

            }
        }
    }
    protected void quickNotify(String text)
    {
        Toast.makeText(this, text,
                Toast.LENGTH_LONG).show();
    }
    protected void addUrlToUi(String url)
    {
        TextView aliasBox = (TextView)findViewById(R.id.aliasView);
        TextView clipboardMessage = (TextView)findViewById(R.id.clipboardNoticeView);
        aliasBox.setText(url);

        android.content.ClipboardManager clipboard = ( android.content.ClipboardManager ) getSystemService(Context.CLIPBOARD_SERVICE );
        android.content.ClipData clip = android.content.ClipData.newPlainText( "Minimizer Alias", url);
        clipboard.setPrimaryClip( clip );
        clipboardMessage.setVisibility(View.VISIBLE);
        quickNotify("'" + url + "' Added to clipboard!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.about: {
                PackageInfo pInfo = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String version = "";
                if(pInfo != null)
                    version = pInfo.versionName;

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("URL Minimizer Version " + version + " License: Apache License 2.0");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
        return true;

    }

    private class HandleUrlCreationThread extends AsyncTask<String, Void, String> {
        Exception e = null;

        @Override
        protected String doInBackground(String... strings) {
            for (String url : strings) {
                try {
                    return urlService.minimizeUrl(url);
                } catch (URLServiceException e) {
                    this.e = e;
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (e != null)
                quickNotify(e.getMessage());
            if (s == null)
                return;
            Log.d(HandleUrlCreationThread.class.getName(), s);
            addUrlToUi(s);

        }
    }
}
