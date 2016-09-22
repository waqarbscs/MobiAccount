package com.OneWindow.sol.MobiAccount.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;
import com.OneWindow.sol.MobiAccount.ItemDetails;
import com.OneWindow.sol.MobiAccount.Models.Item;
import com.OneWindow.sol.MobiAccount.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by waqarbscs on 9/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.View_Holder>  {
    List<Item> list = Collections.emptyList();
    Context context;

    public RecyclerViewAdapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View1 Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.View_Holder holder, final int position) {
        holder.title.setText(list.get(position).getSubType());
        holder.description.setText(list.get(position).getDate());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Want to Delete Account");
                alertDialog.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(list.get(holder.getLayoutPosition()));
                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ItemDetails.class);
                intent.putExtra("_id",list.get(position).getId());
                intent.putExtra("_type",list.get(position).getType());
                intent.putExtra("_sub",list.get(position).getSubType());
                intent.putExtra("_amount",list.get(position).getAmount());
                intent.putExtra("_date",list.get(position).getDate());
                intent.putExtra("user",list.get(position).getUserId());
                intent.putExtra("_description",list.get(position).getDescription());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    public void deleteItem(int _id){
        DbHelper.getDbHelper(context).delete_Item(_id);
    }
    public void deleteItem(Item item){
        int position=list.indexOf(item);
        deleteItem(list.get(position).getId());
        list.remove(item);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView description;
        ImageButton editButton,deleteButton;

        View_Holder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            editButton = (ImageButton) itemView.findViewById(R.id.editButton);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deletButton);
        }
    }
}
