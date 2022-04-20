package tw.musbea.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tw.musbea.account.User;

public class GetUser {

    /* This class will help to get user data by his uid */

    FirebaseFirestore database;

    public GetUser(String uid, Listener listener) {
        database = FirebaseFirestore.getInstance();
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getString("uid").equals(uid)) {
                        User userData = new User();
                        userData.setUid(document.getString("uid"));
                        userData.setUsername(document.getString("username"));
                        userData.setEmail(document.getString("email"));
                        userData.setDescription(document.getString("description"));
                        userData.setBanReason(document.getString("banReason"));
                        userData.setAvatarUrl(document.getString("avatarUrl"));
                        userData.setYoutubeUrl(document.getString("youtubeUrl"));
                        userData.setSpotifyUrl(document.getString("spotifyUrl"));
                        userData.setSoundcloudUrl(document.getString("soundcloudUrl"));
                        userData.setTwitterUrl(document.getString("twitterUrl"));
                        userData.setUserAge(Integer.parseInt(document.get("userAge").toString()));
                        userData.setBanned(Boolean.parseBoolean(document.get("isBanned").toString()));
                        userData.setOnline(Boolean.parseBoolean(document.get("isOnline").toString()));
                        userData.setOnlineVisible(Boolean.parseBoolean(document.get("isOnlineVisible").toString()));
                        userData.setLastOnlineDate(Long.parseLong(document.get("lastOnlineDate").toString()));
                        userData.setUserBirthDay(Long.parseLong(document.get("userBirthDay").toString()));
                        userData.setAccountCreatedDate(Long.parseLong(document.get("userAccountCreatedDate").toString()));
                        listener.OnUserDataReceived(userData);

                        database.collection("users").document(document.getId()).collection("friends").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<User> usrsList = new ArrayList<>();
                                for (QueryDocumentSnapshot documentFriend : task.getResult()) {
                                    User userData = new User();
                                    userData.setUid(documentFriend.getString("uid"));
                                    userData.setUsername(documentFriend.getString("username"));
                                    userData.setEmail(documentFriend.getString("email"));
                                    userData.setDescription(documentFriend.getString("description"));
                                    userData.setBanReason(documentFriend.getString("banReason"));
                                    userData.setAvatarUrl(documentFriend.getString("avatarUrl"));
                                    userData.setYoutubeUrl(documentFriend.getString("youtubeUrl"));
                                    userData.setSpotifyUrl(documentFriend.getString("spotifyUrl"));
                                    userData.setSoundcloudUrl(documentFriend.getString("soundcloudUrl"));
                                    userData.setTwitterUrl(documentFriend.getString("twitterUrl"));
                                    userData.setUserAge(Integer.parseInt(documentFriend.get("userAge").toString()));
                                    userData.setBanned(Boolean.parseBoolean(documentFriend.get("isBanned").toString()));
                                    userData.setOnline(Boolean.parseBoolean(documentFriend.get("isOnline").toString()));
                                    userData.setOnlineVisible(Boolean.parseBoolean(documentFriend.get("isOnlineVisible").toString()));
                                    userData.setLastOnlineDate(Long.parseLong(documentFriend.get("lastOnlineDate").toString()));
                                    userData.setUserBirthDay(Long.parseLong(documentFriend.get("userBirthDay").toString()));
                                    userData.setAccountCreatedDate(Long.parseLong(documentFriend.get("userAccountCreatedDate").toString()));
                                }
                                listener.OnUserFriendsListReceived(usrsList);
                                database.collection("users").document(document.getId()).collection("followers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        List<User> usrsList = new ArrayList<>();
                                        for (QueryDocumentSnapshot documentFollowers : task.getResult()) {
                                            User userData = new User();
                                            userData.setUid(documentFollowers.getString("uid"));
                                            userData.setUsername(documentFollowers.getString("username"));
                                            userData.setEmail(documentFollowers.getString("email"));
                                            userData.setDescription(documentFollowers.getString("description"));
                                            userData.setBanReason(documentFollowers.getString("banReason"));
                                            userData.setAvatarUrl(documentFollowers.getString("avatarUrl"));
                                            userData.setYoutubeUrl(documentFollowers.getString("youtubeUrl"));
                                            userData.setSpotifyUrl(documentFollowers.getString("spotifyUrl"));
                                            userData.setSoundcloudUrl(documentFollowers.getString("soundcloudUrl"));
                                            userData.setTwitterUrl(documentFollowers.getString("twitterUrl"));
                                            userData.setUserAge(Integer.parseInt(documentFollowers.get("userAge").toString()));
                                            userData.setBanned(Boolean.parseBoolean(documentFollowers.get("isBanned").toString()));
                                            userData.setOnline(Boolean.parseBoolean(documentFollowers.get("isOnline").toString()));
                                            userData.setOnlineVisible(Boolean.parseBoolean(documentFollowers.get("isOnlineVisible").toString()));
                                            userData.setLastOnlineDate(Long.parseLong(documentFollowers.get("lastOnlineDate").toString()));
                                            userData.setUserBirthDay(Long.parseLong(documentFollowers.get("userBirthDay").toString()));
                                            userData.setAccountCreatedDate(Long.parseLong(documentFollowers.get("userAccountCreatedDate").toString()));
                                        }
                                        listener.OnUserFollowersListReceived(usrsList);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.OnFailure(e.getMessage());
            }
        });
    }

    public static interface Listener {
        public void OnUserDataReceived(User userData);

        public void OnUserFriendsListReceived(List<User> friendsList);

        public void OnUserFollowersListReceived(List<User> followersList);

        public void OnFailure(String reason);
    }
}
