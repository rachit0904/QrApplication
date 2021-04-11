package RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrapplication.R;

import java.util.List;

import Data.UsersData;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {

    private Context context;
    private List<UsersData> data;

    public Adapter(Context context, List<UsersData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersData usersData=data.get(position);
        holder.name.setText(usersData.getName());
        holder.no.setText(usersData.getNumber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,no,invite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name1);
            no=itemView.findViewById(R.id.number1);
            invite=itemView.findViewById(R.id.invite);
            invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    UsersData usersData=data.get(pos);
                }
            });
        }
    }
}
