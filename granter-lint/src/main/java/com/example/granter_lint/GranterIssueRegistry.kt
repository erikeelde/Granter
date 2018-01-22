package com.example.granter_lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

class GranterIssueRegistry(
        override val issues: List<Issue> = listOf(GranterUsageDetector.ISSUE_WRENCH_MISSING_CALLBACK)
) : IssueRegistry()

