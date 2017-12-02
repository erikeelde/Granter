package com.example.granter_lint

import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

class AttributeDetector : ResourceXmlDetector() {
    companion object {
        val ISSUE_ATTRIBUTE_FOUND = Issue.create("FoundAttribute",
                "brief description",
                "explanation",
                Category.CORRECTNESS,
                5,
                Severity.ERROR,
                Implementation(AttributeDetector::class.java, Scope.RESOURCE_FILE_SCOPE))
    }

    override fun getApplicableElements(): MutableCollection<String> {
        return XmlScanner.ALL
    }

    override fun getApplicableAttributes(): MutableCollection<String> {
        return XmlScanner.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val ownerElement = attribute.ownerElement
        print(ownerElement)
        if (attribute.localName.equals("background") && ownerElement?.localName.equals("TextView")) {
            context.report(ISSUE_ATTRIBUTE_FOUND, context.getNameLocation(attribute), "Mr attribute")
        }
    }

}