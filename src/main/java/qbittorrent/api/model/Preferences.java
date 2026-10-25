package qbittorrent.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Only the preferences used by the exporter are mapped, so that changes to the
 * remaining (many) preferences in newer qBittorrent versions can't break parsing.
 */
public class Preferences {

    @SerializedName("alt_dl_limit")
    private long altDlLimit;

    @SerializedName("alt_up_limit")
    private long altUpLimit;

    @SerializedName("max_active_downloads")
    private long maxActiveDownloads;

    @SerializedName("max_active_uploads")
    private long maxActiveUploads;

    @SerializedName("max_active_torrents")
    private long maxActiveTorrents;

    public long getAltDlLimit() {
        return altDlLimit;
    }

    public long getAltUpLimit() {
        return altUpLimit;
    }

    public long getMaxActiveDownloads() {
        return maxActiveDownloads;
    }

    public long getMaxActiveUploads() {
        return maxActiveUploads;
    }

    public long getMaxActiveTorrents() {
        return maxActiveTorrents;
    }
}
