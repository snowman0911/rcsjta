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

package com.gsma.rcs.core.ims.service.capability;

import java.util.HashSet;
import java.util.Set;

/**
 * Capabilities
 * 
 * @author jexa7410
 * @author Philippe LEMORDANT
 */
public class Capabilities {

    /**
     * Invalid timestamp for capabilities
     */
    public static final long INVALID_TIMESTAMP = -1;

    private final boolean mImSession;
    private final boolean mFileTransferMsrp;
    private final boolean mCsVideo;
    private final boolean mPresenceDiscovery;
    private final boolean mSocialPresence;
    private final boolean mFileTransferHttp;
    private final boolean mFileTransferThumbnail;
    private final boolean mFileTransferStoreForward;
    private final boolean mGroupChatStoreForward;
    /**
     * SIP automata (@see RFC 3840)
     */
    private final boolean mSipAutomata;

    /**
     * Last timestamp capabilities was requested
     */
    private final long mTimestampOfLastRequest;

    /**
     * Timestamp of when this capabilities was received from network.
     */
    private final long mTimestampOfLastResponse;

    /**
     * The default capabilities applicable to non RCS contacts
     */
    public final static Capabilities sDefaultCapabilities = new Capabilities.CapabilitiesBuilder()
            .build();

    private Capabilities(CapabilitiesBuilder builder) {
        mImSession = builder.mImSession;
        mFileTransferMsrp = builder.mFileTransferMsrp;
        mCsVideo = builder.mCsVideo;
        mPresenceDiscovery = builder.mPresenceDiscovery;
        mSocialPresence = builder.mSocialPresence;
        mFileTransferHttp = builder.mFileTransferHttp;
        mFileTransferThumbnail = builder.mFileTransferThumbnail;
        mFileTransferStoreForward = builder.mFileTransferStoreForward;
        mGroupChatStoreForward = builder.mGroupChatStoreForward;
        mSipAutomata = builder.mSipAutomata;
        mTimestampOfLastRequest = builder.mTimestampOfLastRequest;
        mTimestampOfLastResponse = builder.mTimestampOfLastResponse;
    }

    /**
     * Is IM session supported
     * 
     * @return Boolean
     */
    public boolean isImSessionSupported() {
        return mImSession;
    }

    /**
     * Is file transfer MSRP supported
     * 
     * @return Boolean
     */
    public boolean isFileTransferMsrpSupported() {
        return mFileTransferMsrp;
    }

    /**
     * Is CS video supported
     * 
     * @return Boolean
     */
    public boolean isCsVideoSupported() {
        return mCsVideo;
    }

    /**
     * Is presence discovery supported
     * 
     * @return Boolean
     */
    public boolean isPresenceDiscoverySupported() {
        return mPresenceDiscovery;
    }

    /**
     * Is social presence supported
     * 
     * @return Boolean
     */
    public boolean isSocialPresenceSupported() {
        return mSocialPresence;
    }

    /**
     * Is file transfer over HTTP supported
     * 
     * @return Boolean
     */
    public boolean isFileTransferHttpSupported() {
        return mFileTransferHttp;
    }


    /**
     * Is file transfer thumbnail supported
     * 
     * @return Boolean
     */
    public boolean isFileTransferThumbnailSupported() {
        return mFileTransferThumbnail;
    }

    /**
     * Is file transfer S&F supported
     * 
     * @return Boolean
     */
    public boolean isFileTransferStoreForwardSupported() {
        return mFileTransferStoreForward;
    }

    /**
     * Is group chat S&F supported
     * 
     * @return Boolean
     */
    public boolean isGroupChatStoreForwardSupported() {
        return mGroupChatStoreForward;
    }

    /**
     * Is device an automata ?
     * 
     * @return True if automata
     */
    public boolean isSipAutomata() {
        return mSipAutomata;
    }

    /**
     * Get timestamp of last request
     * 
     * @return timetampOfLastRequest (in milliseconds)
     */
    public long getTimestampOfLastRequest() {
        return mTimestampOfLastRequest;
    }

    // @formatter:off
    @Override
    public String toString() {
        return "Caps{" +
                "IM=" + mImSession +
                ", FtMsrp=" + mFileTransferMsrp +
                ", FtHttp=" + mFileTransferHttp +
                ", FtThumbnail=" + mFileTransferThumbnail +
                ", FtSF=" + mFileTransferStoreForward +
                ", GcSF=" + mGroupChatStoreForward +
                ", SipAutomata=" + mSipAutomata +
                ", TimeOfLastRequest=" + mTimestampOfLastRequest +
                ", TimeOfLastResponse=" + mTimestampOfLastResponse +
                '}';
    }
    // @formatter:on

    /*
     * The equals method does not consider the 2 timestamps.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Capabilities other = (Capabilities) obj;
        if (mCsVideo != other.mCsVideo)
            return false;
        if (mFileTransferMsrp != other.mFileTransferMsrp)
            return false;
        if (mFileTransferHttp != other.mFileTransferHttp)
            return false;
        if (mFileTransferStoreForward != other.mFileTransferStoreForward)
            return false;
        if (mFileTransferThumbnail != other.mFileTransferThumbnail)
            return false;
        if (mGroupChatStoreForward != other.mGroupChatStoreForward)
            return false;
        if (mImSession != other.mImSession)
            return false;
        if (mPresenceDiscovery != other.mPresenceDiscovery)
            return false;
        if (mSipAutomata != other.mSipAutomata)
            return false;
        if (mSocialPresence != other.mSocialPresence)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (mCsVideo ? 1231 : 1237);
        result = prime * result + (mFileTransferHttp ? 1231 : 1237);
        result = prime * result + (mFileTransferMsrp ? 1231 : 1237);
        result = prime * result + (mFileTransferStoreForward ? 1231 : 1237);
        result = prime * result + (mFileTransferThumbnail ? 1231 : 1237);
        result = prime * result + (mGroupChatStoreForward ? 1231 : 1237);
        result = prime * result + (mImSession ? 1231 : 1237);
        result = prime * result + (mPresenceDiscovery ? 1231 : 1237);
        result = prime * result + (mSipAutomata ? 1231 : 1237);
        result = prime * result + (mSocialPresence ? 1231 : 1237);
        return result;
    }

    /**
     * Get timestamp of last response
     * 
     * @return timestampOfLastResponse (in milliseconds)
     */
    public long getTimestampOfLastResponse() {
        return mTimestampOfLastResponse;
    }

    /**
     * Capabilities builder class
     */
    public static class CapabilitiesBuilder {
        private boolean mImSession = false;
        private boolean mFileTransferMsrp = false;
        private boolean mCsVideo = false;
        private boolean mPresenceDiscovery = false;
        private boolean mSocialPresence = false;
        private boolean mFileTransferHttp = false;
        private boolean mFileTransferThumbnail = false;
        private boolean mFileTransferStoreForward = false;
        private boolean mGroupChatStoreForward = false;
        private boolean mSipAutomata = false;
        private long mTimestampOfLastRequest = INVALID_TIMESTAMP;
        private long mTimestampOfLastResponse = INVALID_TIMESTAMP;

        /**
         * Default constructor
         */
        public CapabilitiesBuilder() {
        }

        /**
         * Copy constructor
         * 
         * @param capabilities to copy or null if construct with default values
         */
        public CapabilitiesBuilder(Capabilities capabilities) {
            mImSession = capabilities.isImSessionSupported();
            mFileTransferMsrp = capabilities.isFileTransferMsrpSupported();
            mCsVideo = capabilities.isCsVideoSupported();
            mPresenceDiscovery = capabilities.isPresenceDiscoverySupported();
            mSocialPresence = capabilities.isSocialPresenceSupported();
            mFileTransferHttp = capabilities.isFileTransferHttpSupported();
            mFileTransferThumbnail = capabilities.isFileTransferThumbnailSupported();
            mFileTransferStoreForward = capabilities.isFileTransferStoreForwardSupported();
            mGroupChatStoreForward = capabilities.isGroupChatStoreForwardSupported();
            mSipAutomata = capabilities.isSipAutomata();
            mTimestampOfLastRequest = capabilities.getTimestampOfLastRequest();
            mTimestampOfLastResponse = capabilities.getTimestampOfLastResponse();
        }

        /**
         * Sets Instant Messaging support
         * 
         * @param support the Instant Messaging support
         * @return the current instance
         */
        public CapabilitiesBuilder setImSession(boolean support) {
            mImSession = support;
            return this;
        }

        /**
         * Is IM session supported
         * 
         * @return Boolean
         */
        public boolean isImSessionSupported() {
            return mImSession;
        }

        /**
         * Sets MSRP File Transfer support
         * 
         * @param support the File Transfer support
         * @return the current instance
         */
        public CapabilitiesBuilder setFileTransferMsrp(boolean support) {
            mFileTransferMsrp = support;
            return this;
        }

        /**
         * Is MSRP file transfer supported
         * 
         * @return Boolean
         */
        public boolean isFileTransferMsrpSupported() {
            return mFileTransferMsrp;
        }

        /**
         * Sets CS video support
         * 
         * @param support the CS video support
         * @return the current instance
         */
        public CapabilitiesBuilder setCsVideo(boolean support) {
            mCsVideo = support;
            return this;
        }

        /**
         * Is CS video supported
         * 
         * @return Boolean
         */
        public boolean isCsVideoSupported() {
            return mCsVideo;
        }

        /**
         * Sets Presence Discovery support
         * 
         * @param support the Presence Discovery support
         * @return the current instance
         */
        public CapabilitiesBuilder setPresenceDiscovery(boolean support) {
            mPresenceDiscovery = support;
            return this;
        }

        /**
         * Is Presence Discovery supported
         * 
         * @return Boolean
         */
        public boolean isPresenceDiscovery() {
            return mPresenceDiscovery;
        }

        /**
         * Sets Social Presence support
         * 
         * @param support the Social Presence support
         * @return the current instance
         */
        public CapabilitiesBuilder setSocialPresence(boolean support) {
            mSocialPresence = support;
            return this;
        }

        /**
         * Is Social Presence supported
         * 
         * @return Boolean
         */
        public boolean isSocialPresence() {
            return mSocialPresence;
        }

        /**
         * Sets File Transfer HTTP support
         * 
         * @param support the File Transfer HTTP support
         * @return the current instance
         */
        public CapabilitiesBuilder setFileTransferHttp(boolean support) {
            mFileTransferHttp = support;
            return this;
        }

        /**
         * Is file transfer over HTTP supported
         * 
         * @return Boolean
         */
        public boolean isFileTransferHttpSupported() {
            return mFileTransferHttp;
        }

        /**
         * Sets File Transfer Thumbnail support
         * 
         * @param support the File Transfer Thumbnail support
         * @return the current instance
         */
        public CapabilitiesBuilder setFileTransferThumbnail(boolean support) {
            mFileTransferThumbnail = support;
            return this;
        }

        /**
         * Is file transfer thumbnail supported
         * 
         * @return Boolean
         */
        public boolean isFileTransferThumbnailSupported() {
            return mFileTransferThumbnail;
        }

        /**
         * Sets File Transfer Thumbnail support
         * 
         * @param support the File Transfer Thumbnail support
         * @return the current instance
         */
        public CapabilitiesBuilder setFileTransferStoreForward(boolean support) {
            mFileTransferStoreForward = support;
            return this;
        }

        /**
         * Is file transfer S&F supported
         * 
         * @return Boolean
         */
        public boolean isFileTransferStoreForwardSupported() {
            return mFileTransferStoreForward;
        }

        /**
         * Sets Group Chat Store & Forward support
         * 
         * @param support the Group Chat Store & Forward support
         * @return the current instance
         */
        public CapabilitiesBuilder setGroupChatStoreForward(boolean support) {
            mGroupChatStoreForward = support;
            return this;
        }

        /**
         * Is group chat S&F supported
         * 
         * @return Boolean
         */
        public boolean isGroupChatStoreForwardSupported() {
            return mGroupChatStoreForward;
        }

        /**
         * Sets Sip Automata support
         * 
         * @param support the Sip Automata support
         * @return the current instance
         */
        public CapabilitiesBuilder setSipAutomata(boolean support) {
            mSipAutomata = support;
            return this;
        }

        /**
         * Is Sip Automata
         * 
         * @return Boolean
         */
        public boolean isSipAutomata() {
            return mSipAutomata;
        }

        /**
         * Sets the timestamp of last request
         * 
         * @param time the Timestamp Of Last Request
         * @return the current instance
         */
        public CapabilitiesBuilder setTimestampOfLastRequest(long time) {
            mTimestampOfLastRequest = time;
            return this;
        }

        /**
         * Gets timestamp of last request
         * 
         * @return Boolean
         */
        public long getTimestampOfLastRequest() {
            return mTimestampOfLastRequest;
        }

        /**
         * Sets the timestamp of last response
         * 
         * @param time the File Transfer Thumbnail support
         * @return the current instance
         */
        public CapabilitiesBuilder setTimestampOfLastResponse(long time) {
            mTimestampOfLastResponse = time;
            return this;
        }

        /**
         * Gets timestamp of last response
         * 
         * @return Boolean
         */
        public long getTimestampOfLastResponse() {
            return mTimestampOfLastResponse;
        }

        /**
         * Build the capabilities
         * 
         * @return the built capabilities instance
         */
        public Capabilities build() {
            return new Capabilities(this);
        }

    }

}
