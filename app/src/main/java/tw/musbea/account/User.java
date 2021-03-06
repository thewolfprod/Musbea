package tw.musbea.account;

import java.util.HashMap;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;
    private String description;
    private String banReason;
    private String avatarUrl;
    private String youtubeUrl;
    private String spotifyUrl;
    private String soundcloudUrl;
    private String twitterUrl;
    private int userAge;
    private boolean isBanned;
    private boolean isOnline;
    private boolean isOnlineVisible;
    private long lastOnlineDate;
    private long userBirthDay;
    private long accountCreatedDate;
    private List<User> friendsList;

    public HashMap<String, Object> UserToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("avatarUrl", avatarUrl);
        map.put("username", username);
        map.put("email", email);
        map.put("description", description);
        map.put("banReason", banReason);
        map.put("youtubeUrl", youtubeUrl);
        map.put("spotifyUrl", spotifyUrl);
        map.put("soundcloudUrl", soundcloudUrl);
        map.put("twitterUrl", twitterUrl);
        map.put("userAge", userAge);
        map.put("isBanned", isBanned);
        map.put("isOnline", isOnline);
        map.put("isOnlineVisible", isOnlineVisible);
        map.put("lastOnlineDate", lastOnlineDate);
        map.put("userBirthDay", userBirthDay);
        map.put("userAccountCreatedDate", accountCreatedDate);
        return map;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    public void setSoundcloudUrl(String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setLastOnlineDate(long lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnlineVisible() {
        return isOnlineVisible;
    }

    public void setOnlineVisible(boolean onlineVisible) {
        isOnlineVisible = onlineVisible;
    }

    public long getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(long userBirthDay) {
        this.userBirthDay = userBirthDay;
    }

    public long getAccountCreatedDate() {
        return accountCreatedDate;
    }

    public void setAccountCreatedDate(long accountCreatedDate) {
        this.accountCreatedDate = accountCreatedDate;
    }
}
