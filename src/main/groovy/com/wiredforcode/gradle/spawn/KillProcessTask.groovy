package com.wiredforcode.gradle.spawn

import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class KillProcessTask extends DefaultSpawnTask {

    @Input
    boolean strict = true

    @TaskAction
    void kill() {
        def pidFile = getPidFile()
        if(strict && !pidFile.exists()) {
            throw new GradleException("No server running!")
        } else if(pidFile.exists()) {
            def pid = pidFile.text
            def process = "kill $pid".execute()

            try {
                process.waitFor()
            } finally {
                pidFile.delete()
            }
        } else {
            logger.debug("pid file not found, strict == false. Nothing to do here.")
        }
    }
}
