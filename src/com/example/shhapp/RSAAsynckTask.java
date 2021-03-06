
/*
 * Copyright 2013 Swapnil Kotwal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.shhapp;

import java.math.BigInteger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * AsynckTask for encryption and Decryption.
 * 
 */
public class RSAAsynckTask extends AsyncTask<Integer, Boolean, String> {
  private ProgressDialog progress;
  private Activity shhActivity;
  private BigInteger encrypt = null;
  private BigInteger decrypt = null;
  private EditText messageTxt;
  private RSA key;

  /**
   * Constructor.
   * 
   * @param shhActivity
   */
  public RSAAsynckTask(final ShhActivity shhActivity) {
    this.shhActivity = shhActivity;
    this.messageTxt = shhActivity.messageTxt;
  }
/**
 * on Pre Execute.
 */
  protected final void onPreExecute() {
    // Show progress Dialog here
    super.onPreExecute();

    // create ProgressDialog here ...
    progress = new ProgressDialog(shhActivity);
    progress.setTitle("Shh.. ");
    progress.setMessage("Shh.. Encrytion in Progress!!");
    // set other progressbar attributes
    progress.setIndeterminate(true);
    progress.setCancelable(false);
    progress.show();

  }

  @Override
  protected final String doInBackground(final Integer... params) {
    key = new RSA();
    System.out.println(key);
    // create random message, encrypt.
    String s = messageTxt.getText().toString();
    BigInteger message = new BigInteger(s.getBytes());
    encrypt = key.encrypt(message);
    System.out.println("message   = " + message);
    System.out.println("hexa dicimal form of message " + message.toString(16));
    System.out.println("encrpyted = " + encrypt);
    shhActivity.runOnUiThread(new Runnable() {
      public void run() {
        messageTxt.setText(encrypt + "");
      }
    });
    return encrypt + "";
  }

  /**
   * Decrypt inpute text box message.
   */
  public final void decryptSMS() {
    if (key != null) {
      decrypt = key.decrypt(encrypt);
      messageTxt.setText(new String(decrypt.toByteArray()));
      System.out.println("decrypted = " + decrypt);
      System.out.println("after decrypt the message is "
          + new String(decrypt.toByteArray()));
    }
  }

  @Override
  protected final void onPostExecute(final String success) {
    if (progress.isShowing()) {
      progress.dismiss();
    }
    if (success != null) {
      Toast.makeText(shhActivity, "Encryption Success", Toast.LENGTH_LONG)
      .show();
    } else {
      Toast.makeText(shhActivity, "Encryption fails", Toast.LENGTH_LONG).show();
    }
  }
}
