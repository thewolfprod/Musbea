package tw.musbea.community;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tw.musbea.account.User;

public class Users {

    FirebaseFirestore database;
    List<User> usersList = new ArrayList<>();
    Listener listener;

    public Users(Listener listener) {
        database = FirebaseFirestore.getInstance();
        this.listener = listener;
        getData();
    }

    void getData() {
        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int a = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
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
                    usersList.add(userData);
                    a++;
                    if (a == task.getResult().size()) {
                        listener.OnUsersLoaded(usersList);
                    }
                }
            }
        });
    }

    public static interface Listener {
        public void OnUsersLoaded(List<User> usersList);
    }
}
