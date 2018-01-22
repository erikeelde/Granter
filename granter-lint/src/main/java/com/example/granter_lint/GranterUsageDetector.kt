package com.example.granter_lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import java.util.*

class GranterUsageDetector : Detector(), Detector.UastScanner {

    override fun getApplicableMethodNames(): List<String> {
        return Arrays.asList("requestCode")
    }

    override fun visitMethod(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val methodName = node.methodName!!

        val evaluator = context.evaluator

        if (methodName == "requestCode") {
            val message = StringBuilder()

            message.append("No receiver for requestCode(" + node.valueArguments[0] + "). \n")
            message.append("Expecting to find either @AfterPermissionGranted(" + node.valueArguments[0] + ") " +
                    " or @AfterPermissionGranted(" + node.valueArguments[0].evaluate() + ") annotated method. \n")

            message.append("1: " + node.psi!!.parent.parent.parent.parent)

            context.report(ISSUE_WRENCH_MISSING_CALLBACK, node, context.getLocation(node), message.toString())
        }
    }

    companion object {

        val ISSUE_WRENCH_MISSING_CALLBACK = Issue.create("NoGranterReceiver", "No listener for Granter.Builder().show()",
                "Every call to Granter.Builder().build().show() show have a recipient in it's fragment / activity",
                Category.MESSAGES, 5, Severity.WARNING,
                Implementation(GranterUsageDetector::class.java, Scope.JAVA_FILE_SCOPE))
    }
}
