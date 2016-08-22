/*******************************************************************************
 * Software Name : RCS IMS Stack
 *
 * Copyright (C) 2010-2016 Orange.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.gsma.rcs.ri.sharing.image;

import com.gsma.rcs.api.connection.utils.RcsActivity;
import com.gsma.rcs.ri.R;
import com.gsma.rcs.ri.RiApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A class to view the persisted information for image sharing<br>
 * Created by Philippe LEMORDANT.
 */
public class ImageSharingLogView extends RcsActivity {
    private static final String EXTRA_SHARING_ID = "id";
    private String mSharingId;
    private TextView mTxtViewContact;
    private TextView mTxtViewDate;
    private TextView mTxtViewDir;
    private TextView mTxtViewFileSize;
    private TextView mTxtViewFilename;
    private TextView mTxtViewMime;
    private TextView mTxtViewReason;
    private TextView mTxtViewState;
    private TextView mTxtViewTransferred;
    private TextView mTxtViewUri;
    private SimpleDateFormat sDateFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_log_image_item);
        mSharingId = getIntent().getStringExtra(EXTRA_SHARING_ID);
        initialize();
    }

    private void initialize() {
        mTxtViewContact = (TextView) findViewById(R.id.history_log_item_contact);
        mTxtViewState = (TextView) findViewById(R.id.history_log_item_state);
        mTxtViewReason = (TextView) findViewById(R.id.history_log_item_reason);
        mTxtViewDir = (TextView) findViewById(R.id.history_log_item_direction);
        mTxtViewDate = (TextView) findViewById(R.id.history_log_item_date);
        mTxtViewMime = (TextView) findViewById(R.id.history_log_item_mime);
        mTxtViewFilename = (TextView) findViewById(R.id.history_log_item_filename);
        mTxtViewFileSize = (TextView) findViewById(R.id.history_log_item_size);
        mTxtViewUri = (TextView) findViewById(R.id.history_log_item_uri);
        mTxtViewTransferred = (TextView) findViewById(R.id.history_log_item_transferred);
    }

    private String getDateFromDb(long timestamp) {
        if (0 == timestamp) {
            return "";
        }
        if (sDateFormat == null) {
            sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        }
        return sDateFormat.format(new Date(timestamp));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageSharingDAO dao = ImageSharingDAO.getImageSharingDAO(this, mSharingId);
        if (dao == null) {
            showMessageThenExit(R.string.error_item_not_found);
            return;
        }
        mTxtViewContact.setText(dao.getContact().toString());
        mTxtViewState.setText(RiApplication.sImageSharingStates[dao.getState().toInt()]);
        mTxtViewReason.setText(RiApplication.sImageSharingReasonCodes[dao.getReasonCode().toInt()]);
        mTxtViewDir.setText(RiApplication.getDirection(dao.getDirection()));
        mTxtViewDate.setText(getDateFromDb(dao.getTimestamp()));
        mTxtViewMime.setText(dao.getMimeType());
        mTxtViewFilename.setText(dao.getFilename());
        mTxtViewFileSize.setText(String.valueOf(dao.getSize()));
        mTxtViewUri.setText(dao.getFile().toString());
        mTxtViewTransferred.setText(String.valueOf(dao.getSizeTransferred()));
    }

    /**
     * Start activity to view details of image sharing record
     * 
     * @param context the context
     * @param sharingId the sharing ID
     */
    public static void startActivity(Context context, String sharingId) {
        Intent intent = new Intent(context, ImageSharingLogView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_SHARING_ID, sharingId);
        context.startActivity(intent);
    }
}
