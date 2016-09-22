package com.OneWindow.sol.MobiAccount.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;
import com.OneWindow.sol.MobiAccount.Models.Item_Subtype;
import com.OneWindow.sol.MobiAccount.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by waqarbscs on 9/16/2016.
 */
public class RecyclerStatusAdapter extends RecyclerView.Adapter<RecyclerStatusAdapter.View_Holder> {

    List<Item_Subtype> list = Collections.emptyList();
    Context context;

    public RecyclerStatusAdapter(List<Item_Subtype> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View1 Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final View_Holder holder, int position) {
        holder.title.setText(list.get(position).getSubType());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Want to Delete Account Type");
                alertDialog.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                remove(list.get(holder.getLayoutPosition()));
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
                showDialog1(list.get(holder.getLayoutPosition()));
            }
        });
    }
    public void updateType(int type,String subType,String newValue){
        DbHelper.getDbHelper(context).update_subItem(type,subType,newValue);
    }
    public void deleteType(String subType){
        DbHelper.getDbHelper(context).delete_subType(subType);
    }
    public void showDialog1(final Item_Subtype type){
        final int position=list.indexOf(type);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Add AccountType");


        final EditText input = new EditText(context);
        input.setText(list.get(position).getSubType());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateType(list.get(position).getType(),list.get(position).getSubType(),input.getText().toString());
                        list.get(position).setSubType(input.getText().toString());
                        notifyDataSetChanged();
                            //Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();

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
    public void remove(Item_Subtype data){
        int position = list.indexOf(data);
        deleteType(list.get(position).getSubType());
        list.remove(data);
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
    /*
    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, SmsModel data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(SmsModel data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
*/
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
