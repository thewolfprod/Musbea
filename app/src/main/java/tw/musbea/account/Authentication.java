package tw.musbea.account;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Authentication {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore database;

    private User userData;

    private AuthListener listener;

    private String DEFAULT_ACCOUNT_DESCRIPTION = "I didn't set description for now...";

    public Authentication(Context context, AuthListener listener) {
        this.listener = listener;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseFirestore.getInstance();

        if (user != null) {
            getUserData(false, false, false, true);
        }
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                user = authResult.getUser();
                getUserData(true, false, false, false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.OnUserLoginError(e.getMessage());
            }
        });
    }

    public void register(String email, String password, String username) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                user = authResult.getUser();
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", user.getUid());
                map.put("avatarUrl", "default");
                map.put("username", username);
                map.put("email", email);
                map.put("description", DEFAULT_ACCOUNT_DESCRIPTION);
                map.put("banReason", "empty");
                map.put("userAge", 0);
                map.put("isBanned", false);
                map.put("isOnline", true);
                map.put("isOnlineVisible", true);
                map.put("lastOnlineDate", 0);
                map.put("userBirthDay", 0);
                map.put("userAccountCreatedDate", 0);

                database.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        getUserData(false, true, false, false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*
                         * OnFailure
                         *
                         * After failing to Add Data to Database
                         * check if user is logged in (exist).
                         * If exist - delete.
                         */

                        if (auth.getCurrentUser() != null) {
                            user = auth.getCurrentUser();
                            user.delete();
                        }
                        listener.OnUserRegisterError(e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.OnUserRegisterError(e.getMessage());
            }
        });
    }

    public boolean isUserLoggedIn() {
        return user != null;
    }

    void getUserData(boolean isLogin, boolean isRegister, boolean userRequest, boolean userAlreadyLogged) {

        // NAVIGATE TO SPECIFIC USER BY HIS UID AND GET DATA

        database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getString("uid").equals(user.getUid())) {
                        userData = new User();
                        userData.setUid(document.getString("uid"));
                        userData.setUsername(document.getString("username"));
                        userData.setEmail(document.getString("email"));
                        userData.setDescription(document.getString("description"));
                        userData.setBanReason(document.getString("banReason"));
                        userData.setAvatarUrl(document.getString("avatarUrl"));
                        userData.setUserAge(Integer.parseInt(document.get("userAge").toString()));
                        userData.setBanned(Boolean.parseBoolean(document.get("isBanned").toString()));
                        userData.setOnline(Boolean.parseBoolean(document.get("isOnline").toString()));
                        userData.setOnlineVisible(Boolean.parseBoolean(document.get("isOnlineVisible").toString()));
                        userData.setLastOnlineDate(Long.parseLong(document.get("lastOnlineDate").toString()));
                        userData.setUserBirthDay(Long.parseLong(document.get("userBirthDay").toString()));
                        userData.setAccountCreatedDate(Long.parseLong(document.get("userAccountCreatedDate").toString()));

                        if (isLogin) {
                            listener.OnUserLogin(userData);
                        } else if (isRegister) {
                            listener.OnUserRegister(userData);
                        } else if (userRequest) {
                            listener.OnUserRequestData(userData);
                        } else if (userAlreadyLogged) {
                            listener.OnUserAlreadyLoggedIn(userData);
                        }
                    }
                }
            }

        });
    }

    public static interface AuthListener {
        public void OnUserLogin(User userData);

        public void OnUserAlreadyLoggedIn(User userData);

        public void OnUserLoginError(String reason);

        public void OnUserRegister(User userData);

        public void OnUserRegisterError(String error);

        public void OnUserRequestData(User userData);

        public void OnUserRequestDataError(String error);
    }
}
