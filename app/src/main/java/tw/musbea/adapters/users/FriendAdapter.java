package tw.musbea.adapters.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import de.dlyt.yanndroid.oneui.view.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import tw.musbea.account.User;
import tw.musbea.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private List<User> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public FriendAdapter(Context context, List<User> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.user_friend_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder viewHolder, int i) {
        viewHolder.username_txt.setText(mData.get(i).getUsername());
        if (!mData.get(i).getAvatarUrl().equals("default")) {
            Glide.with(context).load(mData.get(i).getAvatarUrl()).into(viewHolder.avatar_img);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialTextView username_txt;
        CircleImageView avatar_img;

        ViewHolder(View itemView) {
            super(itemView);
            username_txt = itemView.findViewById(R.id.username_txt);
            avatar_img = itemView.findViewById(R.id.avatar_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}