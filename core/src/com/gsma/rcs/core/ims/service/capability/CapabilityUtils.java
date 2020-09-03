/*******************************************************************************
 * Software Name : RCS IMS Stack
 *
 * Copyright (C) 2010-2016 Orange.
 * Copyright (C) 2015 Sony Mobile Communications Inc.
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
 *
 * NOTE: This file has been modified by Sony Mobile Communications Inc.
 * Modifications are licensed under the License.
 ******************************************************************************/

package com.gsma.rcs.core.ims.service.capability;

import com.gsma.rcs.core.ims.network.sip.FeatureTags;
import com.gsma.rcs.core.ims.protocol.rtp.MediaRegistry;
import com.gsma.rcs.core.ims.protocol.sdp.MediaAttribute;
import com.gsma.rcs.core.ims.protocol.sdp.MediaDescription;
import com.gsma.rcs.core.ims.protocol.sdp.SdpParser;
import com.gsma.rcs.core.ims.protocol.sip.SipMessage;
import com.gsma.rcs.provider.settings.RcsSettings;
import com.gsma.rcs.utils.MimeManager;
import com.gsma.rcs.utils.StringUtils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Capability utility functions
 * 
 * @author jexa7410
 */
public class CapabilityUtils {

    /**
     * Get supported feature tags for capability exchange
     * 
     * @param rcsSettings the accessor to RCS settings
     * @return List of tags
     */
    public static String[] getSupportedFeatureTags(RcsSettings rcsSettings) {
        List<String> tags = new ArrayList<>();
        List<String> icsiTags = new ArrayList<>();
        List<String> iariTags = new ArrayList<>();

        // Chat support
        if (rcsSettings.isImSessionSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_CHAT);
        }
        // FT support
        if (rcsSettings.isFileTransferSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_FT);
        }
        // FT over HTTP support
        if (rcsSettings.isFileTransferHttpSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_FT_HTTP);
        }

        // Presence discovery support
        if (rcsSettings.isPresenceDiscoverySupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_PRESENCE_DISCOVERY);
        }
        // Social presence support
        if (rcsSettings.isSocialPresenceSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_SOCIAL_PRESENCE);
        }
        // Geolocation push support
        if (rcsSettings.isGeoLocationPushSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_GEOLOCATION_PUSH);
        }
        // FT thumbnail support
        if (rcsSettings.isFileTransferThumbnailSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_FT_THUMBNAIL);
        }
        // FT S&F support
        if (rcsSettings.isFileTransferStoreForwardSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_FT_SF);
        }
        // Group chat S&F support
        if (rcsSettings.isGroupChatStoreForwardSupported()) {
            iariTags.add(FeatureTags.FEATURE_RCSE_GC_SF);
        }
        // Automata flag
        if (rcsSettings.isSipAutomata()) {
            tags.add(FeatureTags.FEATURE_SIP_AUTOMATA);
        }

        // Add IARI prefix
        if (!iariTags.isEmpty()) {
            tags.add(FeatureTags.FEATURE_RCSE + "=\"" + TextUtils.join(",", iariTags) + "\"");
        }
        // Add ICSI prefix
        if (!icsiTags.isEmpty()) {
            tags.add(FeatureTags.FEATURE_3GPP + "=\"" + TextUtils.join(",", icsiTags) + "\"");
        }
        return tags.toArray(new String[tags.size()]);
    }

    /**
     * Extract features tags
     * 
     * @param msg Message
     * @return Capabilities
     */
    public static Capabilities extractCapabilities(SipMessage msg) {
        /* Analyze feature tags */
        Capabilities.CapabilitiesBuilder capaBuilder = new Capabilities.CapabilitiesBuilder();
        Set<String> tags = msg.getFeatureTags();
        boolean ipCall_RCSE = false;
        boolean ipCall_3GPP = false;
        for (String tag : tags) {
            if (tag.contains(FeatureTags.FEATURE_RCSE_CHAT)) {
                capaBuilder.setImSession(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_FT)) {
                capaBuilder.setFileTransferMsrp(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_FT_HTTP)) {
                capaBuilder.setFileTransferHttp(true);

            } else if (tag.contains(FeatureTags.FEATURE_OMA_IM)) {
                /* Support both IM & FT services */
                capaBuilder.setImSession(true).setFileTransferMsrp(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_PRESENCE_DISCOVERY)) {
                capaBuilder.setPresenceDiscovery(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_SOCIAL_PRESENCE)) {
                capaBuilder.setSocialPresence(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_FT_THUMBNAIL)) {
                capaBuilder.setFileTransferThumbnail(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_FT_SF)) {
                capaBuilder.setFileTransferStoreForward(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_GC_SF)) {
                capaBuilder.setGroupChatStoreForward(true);

            } else if (tag.contains(FeatureTags.FEATURE_RCSE_IARI_EXTENSION + ".ext")
                    || tag.contains(FeatureTags.FEATURE_RCSE_IARI_EXTENSION + ".mnc")
                    || tag.contains(FeatureTags.FEATURE_RCSE_ICSI_EXTENSION + ".gsma")) {
            } else if (tag.contains(FeatureTags.FEATURE_SIP_AUTOMATA)) {
                capaBuilder.setSipAutomata(true);
            }
        }

        long timestamp = System.currentTimeMillis();
        capaBuilder.setTimestampOfLastResponse(timestamp);
        capaBuilder.setTimestampOfLastRequest(timestamp);
        return capaBuilder.build();
    }

    /**
     * Build supported SDP part
     * 
     * @param ipAddress Local IP address
     * @param richcall Rich call supported
     * @param rcsSettings RCS settings accessor
     * @return SDP
     */
    public static String buildSdp(String ipAddress, boolean richcall, RcsSettings rcsSettings) {
        String sdp = null;
        return sdp;
    }

    /**
     * Extract service ID from feature tag extension
     * 
     * @param featureTag Feature tag
     * @return Service ID
     */
    public static String extractServiceId(String featureTag) {
        String[] values = featureTag.split("=");
        String value = StringUtils.removeQuotes(values[1]);
        if (featureTag.contains(FeatureTags.FEATURE_RCSE_IARI_EXTENSION)) {
            return value.substring(FeatureTags.FEATURE_RCSE_IARI_EXTENSION.length() + 1,
                    value.length());
        } else {
            return value.substring(FeatureTags.FEATURE_RCSE_ICSI_EXTENSION.length() + 1,
                    value.length());
        }
    }
}
