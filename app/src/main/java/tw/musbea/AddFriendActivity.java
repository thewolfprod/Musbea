package tw.musbea;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import de.dlyt.yanndroid.oneui.layout.ToolbarLayout;
import de.dlyt.yanndroid.oneui.menu.MenuItem;
import de.dlyt.yanndroid.oneui.sesl.recyclerview.LinearLayoutManager;
import de.dlyt.yanndroid.oneui.view.RecyclerView;
import tw.musbea.account.User;
import tw.musbea.adapters.users.FriendAdapter;
import tw.musbea.community.Users;

public class AddFriendActivity extends AppCompatActivity {

    private ToolbarLayout toolbarLayout;

    RecyclerView friendRecyclerView;
    FriendAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initialize();
        setup();
    }

    void initialize() {
        toolbarLayout = findViewById(R.id.toolbarLayoutHome);
        friendRecyclerView = findViewById(R.id.friendsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddFriendActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        friendRecyclerView.setLayoutManager(layoutManager);
        toolbarLayout.showSearchMode();

        Users usersLoad = new Users(new Users.Listener() {
            @Override
            public void OnUsersLoaded(List<User> usersList) {
                adapter = new FriendAdapter(AddFriendActivity.this, usersList);
                friendRecyclerView.setAdapter(adapter);
            }
        });
    }

    void setup() {
        toolbarLayout.inflateToolbarMenu(R.menu.home_menu);

        toolbarLayout.setOnToolbarMenuItemClickListener(new ToolbarLayout.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.app_bar_search) {
                    toolbarLayout.showSearchMode();
                }
                return false;
            }
        });

        toolbarLayout.setSearchModeListener(new ToolbarLayout.SearchModeListener(){
            @Override
            public void onSearchOpened(EditText search_edittext) {
                super.onSearchOpened(search_edittext);
            }

            @Override
            public void onSearchDismissed(EditText search_edittext) {
                super.onSearchDismissed(search_edittext);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
            }

            @Override
            public void onKeyboardSearchClick(CharSequence s) {
                super.onKeyboardSearchClick(s);
            }

            @Override
            public void onVoiceInputClick(Intent intent) {
                super.onVoiceInputClick(intent);
            }
        });
    }
}
