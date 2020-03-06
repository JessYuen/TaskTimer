package com.example.tasktimer;

import androidx.fragment.app.Fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum FragmentEditMode {EDIT, ADD}
    private FragmentEditMode mMode;

    private EditText mNameTv;
    private EditText mDescTv;
    private EditText mSortOrderTv;
    private Button mSaveBtn;

    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");

        View view = inflater.inflate(R.layout.fragment_add_edit,container, false);
        mNameTv = view.findViewById(R.id.addedit_name);
        mDescTv = view.findViewById(R.id.addedit_description);
        mSortOrderTv = view.findViewById(R.id.addedit_sortorder);
        mSaveBtn = view.findViewById(R.id.addedit_save);

        Bundle arguments = getActivity().getIntent().getExtras();

        final Task task;

        if (arguments != null) {
            Log.d(TAG, "onCreateView: retrieving task details");

            task = (Task) arguments.getSerializable(Task.class.getSimpleName());

            if (task != null) {
                Log.d(TAG, "onCreateView: Task details found, editing...");
                mNameTv.setText(task.getName());
                mDescTv.setText(task.getDescription());
                mSortOrderTv.setText(Integer.toString(task.getSortorder()));
                mMode = FragmentEditMode.EDIT;
            } else {
                // No task, so we must be adding a new task
                mMode = FragmentEditMode.ADD;
            }


        } else {
            task = null;
            Log.d(TAG, "onCreateView: No arguments, adding new record");
            mMode = FragmentEditMode.ADD;
        }

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update database if at least one field has changed

                int so; // to save repeated conversions to int;
                if (mSortOrderTv.length() > 0) {
                    so = Integer.parseInt(mSortOrderTv.getText().toString());
                } else{
                    so = 0;
                }

                ContentResolver resolver = getActivity().getContentResolver();
                ContentValues contentValues = new ContentValues();

                switch (mMode) {
                    case EDIT:
                        if (!mNameTv.getText().toString().equals(task.getName())) {
                            contentValues.put(TasksContract.Columns.TASKS_NAME, mNameTv.getText(). toString());
                        }
                        if (!mDescTv.getText().toString().equals(task.getDescription())) {
                        contentValues.put(TasksContract.Columns.TASKS_DESCRIPTION, mDescTv.getText().toString());
                        }
                        if (so != task.getSortorder()) {
                            contentValues.put(TasksContract.Columns.TASKS_SORTORDER, so);
                        }
                        if (contentValues.size() != 0) {
                            Log.d(TAG, "onClick: updating task");
                            resolver.update(TasksContract.buildTaskUri(task.get_Id()), contentValues, null, null);
                        }
                        break;
                    case ADD:
                        if (mNameTv.length() > 0) {
                            Log.d(TAG, "onClick: adding new task");
                            contentValues.put(TasksContract.Columns.TASKS_NAME, mNameTv.getText().toString());
                            contentValues.put(TasksContract.Columns.TASKS_DESCRIPTION, mDescTv.getText().toString());
                            contentValues.put(TasksContract.Columns.TASKS_SORTORDER, so);
                            resolver.insert(TasksContract.CONTENT_URI, contentValues);
                        }
                        break;
                }
                Log.d(TAG, "onClick: done editing");
            }
        });

        Log.d(TAG, "onCreateView: exiting...");
        return view;
    }
}
