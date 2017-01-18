package com.example.adminto.mytasks;

import java.util.List;

/**
 * Created by adminto on 18.01.2017.
 */

public interface IDatabaseHandler {
    public void addTask(Task _task);
    public List<Task> getCurrDateTasks();
    public void deleteTask(Task _task);
}