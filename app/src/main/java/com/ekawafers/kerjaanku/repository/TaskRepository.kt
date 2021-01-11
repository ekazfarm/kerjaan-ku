package com.ekawafers.kerjaanku.repository

import android.content.Context
import com.ekawafers.kerjaanku.db.DbSubTaskHelper
import com.ekawafers.kerjaanku.db.DbTaskHelper
import com.ekawafers.kerjaanku.model.MainTask
import com.ekawafers.kerjaanku.model.Task
import com.ekawafers.kerjaanku.model.Tasks
import com.google.gson.Gson
import java.io.IOException

object TaskRepository {

    fun getDataTasks(context: Context?): Tasks?{
        val json: String?
        try {
            val inputStream = context?.assets?.open("tasks.json")
            json = inputStream?.bufferedReader().use { it?.readText() }
        }catch (e: IOException){
            e.printStackTrace()
            return null
        }

        return Gson().fromJson(json, Tasks::class.java)
    }

    fun getDataTaskFromDatabase(dbTaskHelper: DbTaskHelper, dbSubTaskHelper: DbSubTaskHelper)
            : List<Task>?{
        val tasks = mutableListOf<Task>()

        val mainTasks = dbTaskHelper.getAllTask()
        tasks.clear()

        if (mainTasks != null){
            for (mainTask: MainTask in mainTasks){
                val task = Task()
                task.mainTask = mainTask

                val subTasks = dbSubTaskHelper.getAllSubTask(mainTask.id)
                if (subTasks != null && subTasks.isNotEmpty()){
                    task.subTasks = subTasks
                }

                tasks.add(task)
            }
        }else{
            return null
        }

        return tasks
    }

    fun getDataTaskCompleteFromDatabase(dbTaskHelper: DbTaskHelper, dbSubTaskHelper: DbSubTaskHelper)
            : List<Task>?{
        val tasks = mutableListOf<Task>()

        val mainTasks = dbTaskHelper.getAllTaskComplete()
        tasks.clear()

        if (mainTasks != null){
            for (mainTask: MainTask in mainTasks){
                val task = Task()
                task.mainTask = mainTask

                val subTasks = dbSubTaskHelper.getAllSubTask(mainTask.id)
                if (subTasks != null && subTasks.isNotEmpty()){
                    task.subTasks = subTasks
                }

                tasks.add(task)
            }
        }else{
            return null
        }

        return tasks
    }
}