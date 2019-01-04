package com.github.hd.tornadofxsuite.controller

import kastree.ast.Node
import kastree.ast.psi.Converter
import kastree.ast.psi.Parser
import script.ClassBreakDown
import tornadofx.*

class Scanner: Controller() {

    companion object {
        val parentLevel = 0
        val childLevel = 0
        var detectedUIControls = HashMap<String, ArrayList<String>>()
        var detectedInputs = ArrayList<String>()
        var classes = ArrayList<ClassBreakDown>()
    }

    fun parseAST(textFile: String) {
        val converter = Converter()
        val file = Parser(converter).parseFile(textFile)

        file.decls.forEach {node ->
            when (node) {
                is Node.Decl.Constructor -> TODO() // NdConstructor(node)
                is Node.Decl.Func ->TODO() // NdFunc(node)
                is Node.Decl.Init -> TODO() // NdInit(node)
                is Node.Decl.Property -> TODO() // NdProperty(node)
                is Node.Decl.Structured -> TODO() // NdStructured(node, file)
                is Node.Decl.TypeAlias -> TODO() // NdTypeAlias(node)
            }
        }
    }

    private fun constructor(node: Node.Decl.Constructor) {}

    private fun func(node: Node.Decl.Func) {}

    private fun init(node: Node.Decl.Init) {}

    private fun property(node: Node.Decl.Property) {}

    private fun typeAlias(node: Node.Decl.TypeAlias) {}

    // Node that has infinite Tree structure representation
    // traverse the tree in (pre-order sort?) to print out the AST composition
    data class TreeNode(val parent: TreeNode,
                        val element: ElementSignature,
                        val children: List<TreeNode>,
                        val treeLevel: Int = 0)

    data class ElementSignature(val modifier: String,
                                val form: String,
                                val name: String)
}