package com.blockstart.android.wallet

import android.content.Context
import com.blockstart.android.wallet.util.FileUtil
import java.io.File





class NIS(val context: Context) {

    init {
        System.loadLibrary("native-lib")
        System.loadLibrary("node")

        //The path where we expect the node project to be at runtime.
        val nodeDir = context.filesDir.absolutePath + "/nis_api"
        if (FileUtil.wasAPKUpdated(context)) {
            //Recursively delete any existing nodejs-project.
            val nodeDirReference = File(nodeDir)
            if (nodeDirReference.exists()) {
                FileUtil.deleteFolderRecursively(File(nodeDir))
            }
            //Copy the node project from assets into the application's data path.
            FileUtil.copyAssetFolder(context.assets, "nis_api", nodeDir)

            FileUtil.saveLastUpdateTime(context)
        }


        Thread(Runnable {
            startNodeWithArguments(arrayOf("node", nodeDir + "/bundle.js"))
        }).start()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun startNodeWithArguments(arguments: Array<String>): Int?


}