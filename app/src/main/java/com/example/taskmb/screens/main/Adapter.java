package com.example.taskmb.screens.main;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.taskmb.model.App;
import com.example.taskmb.R;
import com.example.taskmb.model.Task;
import com.example.taskmb.screens.details.TaskDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private SortedList<Task> sortedList;


    public Adapter() {// сортировка задач

        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {// сравниватель
                if(!o2.ready && o1.ready){
                    return 1;
                }
                if(o2.ready && !o1.ready){
                    return -1;
                }
                return (int) (o2.taskTime - o1.taskTime);
            }

            @Override
            public void onChanged(int position, int count) {// менятель
                notifyItemRangeChanged(position, count);

            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {// когда содержиое одинаково
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.uniqueId == item2.uniqueId;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Task> notes){
        sortedList.replaceAll(notes);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{ // хранит ссылки

        TextView taskText;
        CheckBox completed;
        View delete;

        Task task;

        boolean silentUpdate;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.note_text);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailsActivity.start((Activity) itemView.getContext(), task);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getTaskDao().delete(task);
                }
            });

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if(!silentUpdate){
                        task.ready = checked;
                        App.getInstance().getTaskDao().update(task); // Save
                    }
                    updateStrokeOut();

                }
            });

        }
        public void bind(Task note){
            this.task = note;

            taskText.setText(note.text);
            updateStrokeOut();
            silentUpdate = true;
            completed.setChecked(note.ready);
            silentUpdate = false;

        }
        private void updateStrokeOut(){// если выполнена задача вычеркивает
            if(task.ready){
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
