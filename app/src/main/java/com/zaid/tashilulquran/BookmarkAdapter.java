package com.zaid.tashilulquran;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>{
    ArrayList<BookmarkModel> data;

    public BookmarkAdapter(ArrayList<BookmarkModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.bookmar_row,parent,false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        if (Common.empty){
            Common.init();
        }
        holder.btn.setText(data.get(position).getTitle());
        holder.btn.setOnClickListener(v -> {
            // Perform your action here
            Intent i = new Intent(v.getContext(), PDFLayout.class);
            i.putExtra("Bname", data.get(holder.getAdapterPosition()).getPara_no());
            i.putExtra("page_no", data.get(holder.getAdapterPosition()).getPage_no());

            v.getContext().startActivity(i);
            Toast.makeText(v.getContext(), "Opning Para", Toast.LENGTH_SHORT).show();
        });
        holder.btn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableEndWidth = holder.btn.getCompoundDrawables()[2].getBounds().width();
                if (event.getRawX() >= (holder.btn.getRight() - drawableEndWidth)) {
                    // perform your action here
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Are you sure you want to delete this bookmark?")
                            .setPositiveButton("OK", (dialog, id) -> {
                                // perform delete action
                                bgthread bgthread=new bgthread(v.getContext());
                                bgthread.setId_bookmark(data.get(holder.getAdapterPosition()).id);
                                bgthread.start();
                                Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                holder.layout.setVisibility(View.GONE);


                            })
                            .setNegativeButton("Cancel", (dialog, id) -> {
                                // do nothing
                            });
                    builder.show();

                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btn;
        LinearLayout layout;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.bookmarkItemLayout);
            btn=itemView.findViewById(R.id.bookmarkItem);
        }
    }

    class bgthread extends Thread{
        private Context context;

        public int getId_bookmark() {
            return id_bookmark;
        }

        public void setId_bookmark(int id_bookmark) {
            this.id_bookmark = id_bookmark;
        }

        private int id_bookmark;

        bgthread(Context context){
            this.context=context;
        }
        @Override
        public void run() {
            super.run();
            MyDatabaseHandler db=new MyDatabaseHandler(context);
            db.deleteBookmark(id_bookmark);
        }
    }
}
