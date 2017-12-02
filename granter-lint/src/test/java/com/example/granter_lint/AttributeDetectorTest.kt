package com.example.granter_lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class AttributeDetectorTest {
    @Test
    fun CheckBasic() {
        lint().files(
                xml("xml/testFile.xml",
                        """<?xml version="1.0" encoding="utf-8"?>
                        <layout xmlns:android="http://schemas.android.com/apk/res/android">
                            <TextView
                                android:layout_width="match_parent"
                                android:layout_height="match_parent"
                                android:background="@android:color/white"
                                android:orientation="vertical" />
                            <Button
                                android:layout_width="match_parent"
                                android:layout_height="match_parent"
                                android:orientation="vertical" />
                        </layout>
                    """).indented())
                .incremental("xml/testFile.xml")
                .issues(AttributeDetector.ISSUE_ATTRIBUTE_FOUND)
                .run()
                .expect("xml/testFile.xml:6: Error: Mr attribute [FoundAttribute]\n" +
                        "                                android:background=\"@android:color/white\"\n" +
                        "                                ~~~~~~~~~~~~~~~~~~\n" +
                        "1 errors, 0 warnings\n")
    }
}