package com.example.granter_lint

import com.android.tools.lint.client.api.IssueRegistry

class GranterIssueRegistry : IssueRegistry() {
    override fun getIssues() = listOf(GranterUsageDetector.ISSUE_WRENCH_MISSING_CALLBACK,
            AttributeDetector.ISSUE_ATTRIBUTE_FOUND)

}

