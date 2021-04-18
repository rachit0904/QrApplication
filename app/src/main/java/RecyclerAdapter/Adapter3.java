package RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.qrapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Data.UsersData;
import de.hdodenhof.circleimageview.CircleImageView;


public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder>  {

    private Context context;
    private List<UsersData> data;

    public Adapter3(Context context, List<UsersData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersData usersData=data.get(position);
        holder.name.setText(usersData.getName());
        holder.no.setText(usersData.getNumber());
        String url=usersData.getImageUrl();
        if(url!="na"){
            Picasso.get().load(url).into(holder.userImage);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,no;
        public ImageButton delete;
        public CheckBox select;
        public CircleImageView userImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name3);
            no=itemView.findViewById(R.id.number3);
            delete=itemView.findViewById(R.id.deleteUser);
            select=itemView.findViewById(R.id.selectContact);
            userImage=itemView.findViewById(R.id.userImg2);
        }
    }
}

