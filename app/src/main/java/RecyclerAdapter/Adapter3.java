package RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrapplication.R;

import java.util.List;

import Data.UsersData;


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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,no;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name3);
            no=itemView.findViewById(R.id.number3);
        }
    }
}

